package com.willoutwest.kalahari.scene.shadow.detectors

import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.Actor
import com.willoutwest.kalahari.scene.light.Light
import com.willoutwest.kalahari.scene.shadow.ShadowDetector

/**
 * Represents an implementation of [ShadowDetector] that finds the first
 * possible shadow-ray intersection but performs no caching.
 */
class BasicDetector : ShadowDetector {

    override fun isInShadow(light: Light,
                            worldPosition: Point3,
                            omegaI: Vector3,
                            tS: Float,
                            root: Actor,
                            eps: EpsilonTable): Boolean {
        val cache = ComputeUtils.localCache

        val obj   = cache.objects.borrow()
        val sRay  = cache.rays.borrow()
        val tMin  = cache.tmins.borrow()

        sRay.set(omegaI, worldPosition)

        val hit = root.isCastingShadows() &&
                  root.shadows(sRay, tMin, obj, eps, tS)

        cache.objects.reuse(obj)
        cache.rays.reuse(sRay)
        cache.tmins.reuse(tMin)

        return hit
    }
}