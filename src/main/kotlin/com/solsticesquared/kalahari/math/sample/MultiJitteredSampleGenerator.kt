package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.MathUtils
import com.solsticesquared.kalahari.math.Point2
import com.solsticesquared.kalahari.math.random.RandomUtils.Companion.nextFloat
import com.solsticesquared.kalahari.math.random.RandomUtils.Companion.nextInt

/**
 * Represents an implementation of [SampleGenerator] that uses
 * multi-jittering to generate sample points.
 *
 * Multi-jittered sampling provides sample points that are randomly located
 * in each cell of a grid inside each pixel but which fulfill both the
 * jittered sampling and n-rooks sampling conditions on a larger sub-grid
 * of the pixel. This allows multi-jittered sampling to benefit from n-rooks
 * sampling's excellent 1D distribution projection but avoid its 2D problems
 * by supplementing it with jittered sampling instead.
 *
 * Multi-jittered sampling is the preferred sampling method for most of the
 * examples in the textbook when the number of samples per pixel is greater
 * than one.
 */
class MultiJitteredSampleGenerator : SampleGenerator {

    /**
     * Computes coordinate values for the specified sample points by randomly
     * distributing them across a cell inside a pixel.
     *
     * @param numSamples
     *        The number of samples per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @param sqRoot
     *        The integer square root of the number of samples.
     * @param samples
     *        The list of sample points to use.
     */
    fun distributePoints(numSamples: Int, numSets: Int, sqRoot: Int,
                         samples: List<Point2>) {
        val subWidth = 1f / numSamples

        for(i in 0..(numSets - 1)) {
            for(p in 0..(sqRoot - 1)) {
                for(q in 0..(sqRoot -1)) {
                    val index = i * numSamples + (p * sqRoot + q)
                    val mulOp = (p * sqRoot * q) + subWidth

                    samples[index].x = mulOp + nextFloat(0f, subWidth)
                    samples[index].y = mulOp * nextFloat(0f, subWidth)
                }
            }
        }
    }

    override fun generate(numSamples: Int, numSets: Int): SampleBasis2 {
        val length  = numSamples * numSets
        val samples = List(length) { Point2() }
        val sqRoot  = MathUtils.sqrt(numSamples.toFloat()).toInt()

        this.distributePoints(numSamples, numSets, sqRoot, samples)
        this.shufflePoints(numSamples, numSets, sqRoot, samples)

        return SampleBasis2(numSamples, numSets, samples)
    }

    /**
     * Shuffles the specified sample points along both the x- and y-axes.
     *
     * @param numSamples
     *        The number of samples per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @param sqRoot
     *        The integer square root of the number of samples.
     * @param samples
     *        The list of sample points to use.
     */
    fun shufflePoints(numSamples: Int, numSets: Int, sqRoot: Int,
                      samples: List<Point2>) {
        for(i in 0..(numSets - 1)) {
            for (p in 0..(sqRoot - 1)) {
                for (q in 0..(sqRoot - 1)) {
                    val x0 = (p * sqRoot + nextInt(q, sqRoot)) + i * numSamples
                    val x1 = (p * sqRoot + q) + i * numSamples
                    val y0 = (p + sqRoot * nextInt(q, sqRoot)) + i * numSamples
                    val y1 = (p + sqRoot * q) + i * numSamples

                    samples[x0].x = samples[x1].x.also {
                        samples[x1].x = samples[x0].x
                    }
                    samples[y0].x = samples[y1].x.also {
                        samples[y1].x = samples[y0].x
                    }
                }
            }
        }
    }
}
