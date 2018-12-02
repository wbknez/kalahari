package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.Point2

/**
 * Represents an implementation of [SampleGenerator] that uses Hammersley
 * sampling to generate sample points.
 *
 * Hammersley sampling provides sample points that are based on the computer
 * representation of numbers in various prime number bases and are
 * distributed along a <i>n</i>x<i>n</i> grid in the unit square. This allows
 * Hammersley sampling to be well-distributed in 2D at the expense of being
 * regularly spaced in 1D (which can lead to aliasing problems).
 * Unfortunately, there is only one Hammersley sequence per number <i>n</i>
 * which may also lead to aliasing issues.
 */
class HammersleySampleGenerator : SampleGenerator {

    /**
     * Computes the radical inverse of the specified number.
     *
     * @param i
     *        The number to compute the radical inverse of.
     * @return The radical inverse of a number.
     */
    fun computeRadicalInverse(i: Int): Float {
        var x = 0.0
        var f = 0.5
        var j = i

        while(j > 0) {
            x += f * (j % 2)
            j /= 2
            f *= 0.5
        }

        return x.toFloat()
    }

    override fun generate(numSamples: Int, numSets: Int): SampleBasis2 {
        val length  = numSamples * numSets
        val samples = List(length) { Point2() }

        for(i in 0..(length - 1)) {
            val y = i % numSamples

            samples[i].x = y.toFloat() / numSamples
            samples[i].y = this.computeRadicalInverse(y)
        }

        return SampleBasis2(numSamples, numSets, samples)
    }
}
