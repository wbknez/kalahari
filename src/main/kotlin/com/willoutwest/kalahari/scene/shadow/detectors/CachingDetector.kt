package com.willoutwest.kalahari.scene.shadow.detectors

import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Matrix4
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.Actor
import com.willoutwest.kalahari.scene.Scene
import com.willoutwest.kalahari.scene.shadow.ShadowCaster
import com.willoutwest.kalahari.scene.shadow.ShadowDetector

/**
 * Represents an implementation of [ShadowDetector] that finds the first
 * possible shadow-ray intersection and caches the results for repeated use.
 *
 * This shadow detection strategy is dependent on the idea that similar rays
 * (in space) probabilistically intersect similar objects.  In simple scenes
 * this strategy is generally not useful, but in scenes with many actors
 * this may produce useful performance gains.
 *
 * @property cached
 *           The last object to cast a shadow.
 * @property invTransform
 *           The world transform for shadow-ray intersection testing.
 */
class CachingDetector : ShadowDetector {

    private var cached: ShadowCaster? = null

    private val invTransform: Matrix4 = Matrix4()

    override fun isVisible(worldPosition: Point3, omegaI: Vector3, tS: Float,
                           scene: Scene, eps: EpsilonTable): Boolean {
        val cache = ComputeUtils.localCache

        val obj   = cache.objects.borrow()
        val cRay  = cache.rays.borrow()
        val sRay  = cache.rays.borrow()
        val tMin  = cache.tmins.borrow()

        cRay.set(omegaI, worldPosition)
        sRay.set(omegaI, worldPosition)

        val hit = this.cached?.shadows(cRay, tMin, obj, eps, tS) ?:
                  scene.root.isCastingShadows() &&
                  scene.root.shadows(sRay, tMin, obj, eps, tS)

        if(hit && obj.obj !== this.cached) {
            this.cached = obj.obj as ShadowCaster

            this.updateTransform(obj.obj as Actor)
        }

        cache.objects.reuse(obj)
        cache.rays.reuse(cRay, sRay)
        cache.tmins.reuse(tMin)

        return !hit
    }

    /**
     * Updates this caching detector's inverse world transform.
     *
     * This method applies each inverse transform successively from the
     * specified actor up the scene-graph, parent by parent.
     *
     * @param actor
     *        The actor to use.
     */
    private fun updateTransform(actor: Actor) {
        var parent = actor.parent

        this.invTransform.set(actor.invTransform)

        while(parent != null) {
            this.invTransform.timesSelf(parent.invTransform)

            parent = parent.parent
        }
    }
}