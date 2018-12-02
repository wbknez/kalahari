package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.MathUtils
import com.solsticesquared.kalahari.math.Point2

/**
 * Represents an implementation of [SampleGenerator] that uses regular
 * sampling to generate sample points.
 *
 * Regular sampling provides sample points that are located in the exact
 * center of each cell of a grid inside each pixel.
 */
class RegularSampleGenerator : SampleGenerator {

    override fun generate(numSamples: Int, numSets: Int): SampleBasis2 {
        val length  = numSamples * numSets
        val samples = List(length) { Point2() }

        val sqRoot  = MathUtils.sqrt(numSamples.toFloat()).toInt()
        val invRoot = 1f / sqRoot

        for(i in 0..(numSets - 1)) {
            for(p in 0..(sqRoot - 1)) {
                for(q in 0..(sqRoot - 1)) {
                    val index = i * numSamples + (p * sqRoot + q)

                    samples[index].x = (q + 0.5f) * invRoot
                    samples[index].y = (p + 0.5f) * invRoot
                }
            }
        }

        return SampleBasis2(numSamples, numSets, samples)
    }
}
