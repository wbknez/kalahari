package com.willoutwest.kalahari.texture.patterns

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.math.noise.Speaker
import com.willoutwest.kalahari.texture.Texture

/**
 * Represents an implementation of [Texture] that applies a noise function to
 * itself.
 *
 * @param maxOutput
 *        The maximum allowed noise scaling.
 * @param speaker
 *        The noise function to apply.
 * @property color
 *        The color to apply noise to.
 * @property difference
 *           The difference between the allowed maximum and minimum
 *           noise scaling.
 * @property minOutput
 *           The minimum allowed noise scaling.
 */
class NoisyTexture(speaker: Speaker, val color: Texture,
                   val minOutput: Float, maxOutput: Float) :
    AbstractProceduralTexture(speaker), Cloneable, Texture {

    private val difference = maxOutput - this.minOutput

    init {
        require(this.minOutput in 0f..1f) {
            "Minimum output must be between [0, 1]."
        }

        require(maxOutput >= this.minOutput) {
            "Maximum output must be at least as great as the minimum."
        }

        require(maxOutput <= 1f) {
            "Maximum output must be less than one."
        }
    }

    override fun getColor(record: Intersection, store: Color3): Color3 {
        val cache         = ComputeUtils.localCache
        val localPosition = cache.points.borrow()

        localPosition.set(record.localPosition)
            .transformSelf(this.invTransform)

        val output = this.speaker.output(localPosition)
        val value  = this.minOutput + this.difference * output

        cache.points.reuse(localPosition)
        
        return this.color.getColor(record, store)
            .timesSelf(value)
    }
}