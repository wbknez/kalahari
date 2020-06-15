package com.willoutwest.kalahari.texture.patterns

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.math.noise.Speaker
import com.willoutwest.kalahari.texture.Texture

/**
 * Represents an implementation of [Texture] that applies a wrapped noise
 * function to itself.
 *
 * @param color
 *        The color to apply noise to.
 * @param speaker
 *        The noise function to apply.
 * @property expansionNumber
 *           The amount to expand the amplitude.
 */
class WrapTexture(speaker: Speaker, color: Texture,
                  val expansionNumber: Float) :
    AbstractProceduralTexture(speaker, color), Cloneable, Texture {

    init {
        require(this.expansionNumber > 1f) {
            "Expansion number must be greater than one."
        }
    }

    override fun getColor(record: Intersection, store: Color3): Color3 {
        val cache         = ComputeUtils.localCache
        val localPosition = cache.points.borrow()

        localPosition.set(record.localPosition)
            .transformSelf(this.invTransform)

        val output = this.expansionNumber * this.speaker.output(localPosition)
        val value  = output - MathUtils.floor(output)

        cache.points.reuse(localPosition)

        return this.color.getColor(record, store)
            .timesSelf(value)
    }
}