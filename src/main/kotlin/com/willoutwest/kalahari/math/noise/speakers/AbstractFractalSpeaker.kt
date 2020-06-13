package com.willoutwest.kalahari.math.noise.speakers

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.noise.Speaker

/**
 * Represents an implementation of [Speaker] that provides the
 * infrastructure necessary to compute outputs using properties of statistical
 * self-similarity.
 *
 * @property gain
 *           The ratio of successive amplitudes.
 * @property maxBounds
 *           The maximum allowed output value.
 * @property minBounds
 *           The minimum allowed output value.
 * @property octaves
 *           The number of successive frequencies that differ by a constant
 *           ratio.
 * @property source
 *           The underlying noise source.
 */
abstract class AbstractFractalSpeaker(protected val source: Speaker,
                                      val gain: Float,
                                      val octaves: Int) : Speaker {

    val maxBounds: Float

    val minBounds: Float

    init {
        require(this.octaves >= 0) {
            "Number of octaves cannot be less than zero."
        }

        this.maxBounds = when(this.gain == 1f) {
            false -> (1f - MathUtils.pow(this.gain, this.octaves.toFloat())) /
                     (1f - this.gain)
            true  -> this.octaves.toFloat()
        }
        this.minBounds = -this.maxBounds
    }
}