package com.willoutwest.kalahari.scene.shadow.detectors

import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Matrix4
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.Actor
import com.willoutwest.kalahari.scene.light.Light
import com.willoutwest.kalahari.scene.shadow.ShadowCaster
import com.willoutwest.kalahari.scene.shadow.ShadowDetector

/**
 * Represents an implementation of [ShadowDetector] that finds the first
 * possible shadow-ray intersection and caches the results per-light for
 * repeated use.
 *
 * This shadow detection strategy is dependent on the idea that similar rays
 * (in space) probabilistically intersect similar objects.  In simple scenes
 * this strategy is generally not useful, but in scenes with many actors
 * this may produce worthwhile performance gains.
 *
 * @property cached
 *           The mapping of cached shadowed objects to lights.
 */
class CachingDetector : ShadowDetector {

    /**
     * Represents an object that has previously had one or more successful
     * shadow-ray intersections with a particular light.
     *
     * @property invTransform
     *           The
     * @property sc
     *           The cached object.
     */
    private data class CachedResult(val invTransform: Matrix4 = Matrix4(),
                                    var sc: ShadowCaster? = null)

    private val cached: MutableMap<Light, CachedResult> = mutableMapOf()

    override fun isInShadow(light: Light, worldPosition: Point3,
                            omegaI: Vector3, tS: Float, root: Actor,
                            eps: EpsilonTable): Boolean {
        val cache = ComputeUtils.localCache

        val obj   = cache.objects.borrow()
        val cRay  = cache.rays.borrow()
        val sRay  = cache.rays.borrow()
        val tMin  = cache.tmins.borrow()

        cRay.set(omegaI, worldPosition)
        sRay.set(omegaI, worldPosition)

        val cObj    = this.cached[light]
        val inCache = cObj?.sc?.shadows(cRay, tMin, obj, eps, tS) == true
        val hit     = inCache || (root.isCastingShadows() &&
                                  root.shadows(sRay, tMin, obj, eps, tS))

        if(hit && !inCache) {
            this.updateCache(light, obj.obj as Actor)
        }

        cache.objects.reuse(obj)
        cache.rays.reuse(cRay, sRay)
        cache.tmins.reuse(tMin)

        return hit
    }

    /**
     * Updates the cached result in this caching detector with the specific
     * actor for the specific light.
     *
     * @param light
     *        The light casting shadows.
     * @param actor
     *        The actor to cache.
     */
    private fun updateCache(light: Light, actor: Actor) {
        if(light !in this.cached) {
            this.cached[light] = CachedResult(Matrix4.Identity.clone())
        }

        this.cached[light]!!.sc = actor

        this.updateTransform(light, actor)
    }

    /**
     * Updates this caching detector's inverse world transform.
     *
     * This method applies each inverse transform successively from the
     * specified actor up the scene-graph, parent by parent.
     *
     * @param light
     *        The light casting shadows.
     * @param actor
     *        The actor to use.
     */
    private fun updateTransform(light: Light, actor: Actor) {
        var parent = actor.parent

        this.cached[light]!!.invTransform.set(Matrix4.Identity)

        while(parent != null) {
            this.cached[light]!!.invTransform.timesSelf(parent.invTransform)

            parent = parent.parent
        }
    }
}