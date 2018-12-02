package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.MathUtils
import com.solsticesquared.kalahari.math.Point2
import com.solsticesquared.kalahari.math.random.RandomUtils

/**
 * Represents an implementation of [SampleGenerator] that uses jittered
 * sampling to generate sample points.
 *
 * Jittered sampling provides sample points that are randomly located in each
 * cell of a grid inside each pixel but whose distribution per cell is more
 * uniform than in random sampling. That is, each cell has a single sample
 * point but that sample point is distributed randomly within that cell. This
 * allows one to reap the benefits of random sampling while preventing the
 * large clumps and gaps that may result from a purely random distribution of
 * sample points.
 */
class JitteredSampleGenerator : SampleGenerator {

    override fun generate(numSamples: Int, numSets: Int): SampleBasis2 {
        val length  = numSamples * numSets
        val random  = RandomUtils.localRandom
        val samples = List(length) { Point2() }

        val sqRoot  = MathUtils.sqrt(numSamples.toFloat()).toInt()
        val invRoot = 1f / sqRoot

        for(i in 0..(numSets - 1)) {
            for(j in 0..(sqRoot - 1)) {
                for(k in 0..(sqRoot - 1)) {
                    val index = i * numSamples + (j * sqRoot + k)

                    samples[index].x = (k + random.nextFloat()) * invRoot
                    samples[index].y = (j + random.nextFloat()) * invRoot
                }
            }
        }

        return SampleBasis2(numSamples, numSets, samples)
    }
}
