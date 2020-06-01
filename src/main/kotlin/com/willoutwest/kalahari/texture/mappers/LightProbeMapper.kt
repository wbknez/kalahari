package com.willoutwest.kalahari.texture.mappers

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.TexCoord2
import com.willoutwest.kalahari.texture.ImageMapper
import kotlin.math.acos

/**
 * Represents an implementation of [ImageMapper] that maps an intersection
 * point on a geometric surface onto a reflective sphere.
 *
 * @property mode
 *           Whether or not to mirror-reverse a mapping.
 */
class LightProbeMapper(val mode: MirrorMode) : ImageMapper {

    /**
     * Represents the type of image reversal (mirroring or non-mirroring)
     * that occurs during mapping.
     */
    enum class MirrorMode {

        /**
         * Produces a mirror-reversed an image.
         */
        Light,

        /**
         * Does not produce a mirror reversed an image.
         *
         * This assumes that an image is actually a tiled composite using
         * one or more additional images to create a panoramic view.
         */
        Panoramic
    }

    override fun map(hit: Point3, width: Int, height: Int, store: TexCoord2)
        : TexCoord2 {
        val dist    = MathUtils.sqrt(hit.x * hit.x + hit.y * hit.y)
        val cosBeta = hit.x / dist
        val sinBeta = hit.y / dist
        val r       = when(mode) {
            MirrorMode.Light     -> MathUtils.InvPi * acos(hit.z)
            MirrorMode.Panoramic -> MathUtils.InvPi * acos(-hit.z)
        }

        return store.set((1f + r * cosBeta) * 0.5f,
                         (1f + r * sinBeta) * 0.5f)
    }
}