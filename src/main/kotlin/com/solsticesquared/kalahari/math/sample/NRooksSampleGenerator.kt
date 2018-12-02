package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.Point2
import com.solsticesquared.kalahari.math.random.RandomUtils

/**
 * Represents an implementation of [SampleGenerator] that uses n-rooks
 * sampling to generate sample points.
 *
 * N-rooks sampling provides sample points that are randomly located in each
 * cell of a grid inside each pixel, but with the condition that there can
 * only be one sample point per row and column.  This produces the n-rooks
 * pattern, as in chess, where <i>n</i> rooks are placed on the board in such
 * a position as they cannot capture each other.  This allows n-rooks
 * sampling to yield excellent 1D distributions at the expense of 2D; the 2D
 * distribution is considered to be no better than random sampling.
 */
class NRooksSampleGenerator : SampleGenerator {

    /**
     * Computes coordinate values for the specified sample points by randomly
     * distributing them across a cell inside a pixel but with the constraint
     * that only one point per row and column.
     *
     * @param numSamples
     *        The number of samples per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @param samples
     *        The list of sample points to use.
     */
    fun distributePoints(numSamples: Int, numSets: Int,
                         samples: List<Point2>) {
        val invSamples = 1f / numSamples
        val random     = RandomUtils.localRandom

        for(i in 0..(numSets - 1)) {
            for(j in 0..(numSamples - 1)) {
                val index = i * numSamples + j

                samples[index].x = (j + random.nextFloat()) * invSamples
                samples[index].y = (j + random.nextFloat()) * invSamples
            }
        }
    }

    override fun generate(numSamples: Int, numSets: Int): SampleBasis2 {
        val length  = numSamples * numSets
        val samples = List(length) { Point2() }

        this.distributePoints(numSamples, numSets, samples)
        this.shufflePoints(numSamples, numSets, samples)

        return SampleBasis2(numSamples, numSets, samples)
    }

    /**
     * Shuffles the specified sample points along both the x- and y-axes.
     *
     * @param numSamples
     *        The number of samples per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @param samples
     *        The list of sample points to use.
     */
    fun shufflePoints(numSamples: Int, numSets: Int,
                      samples: List<Point2>) {
        val random = RandomUtils.localRandom

        for(i in 0..(numSets - 1)) {
            val sIndex = i * numSamples

            for (j in 0..(numSamples - 1)) {
                val x0 = sIndex + random.nextInt(numSamples)
                val y0 = sIndex + random.nextInt(numSamples)
                val t1 = sIndex + j

                samples[x0].x = samples[t1].x.also {
                    samples[t1].x = samples[x0].x
                }
                samples[y0].y = samples[t1].y.also {
                    samples[t1].y = samples[y0].y
                }
            }
        }
    }
}
