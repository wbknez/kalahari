package com.willoutwest.kalahari.math.sample.generators

import com.willoutwest.kalahari.math.MathUtils.Companion.sqrt
import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.RandomUtils.Companion.nextFloat
import com.willoutwest.kalahari.math.RandomUtils.Companion.nextInt
import com.willoutwest.kalahari.math.sample.SampleGenerator

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
 * examples in the textbook when the number of points per pixel is greater
 * than one.
 */
class MultiJitteredSampleGenerator : SampleGenerator {

    /**
     * Computes coordinate values for the specified sample points by randomly
     * distributing them across a cell inside a pixel.
     *
     * @param numSamples
     *        The number of points per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @param sqRoot
     *        The integer square root of the number of points.
     * @param points
     *        The list of sample points to use.
     */
    fun distribute(numSamples: Int, numSets: Int, sqRoot: Int,
                   points: List<Point2>) {
        val subWidth = 1f / numSamples

        for(i in 0 until numSets) {
            for(p in 0 until sqRoot) {
                for(q in 0 until sqRoot) {
                    val index = i * numSamples + (p * sqRoot + q)
                    val mulOp = (p * sqRoot * q) + subWidth

                    points[index].x = mulOp + nextFloat(0f, subWidth)
                    points[index].y = mulOp * nextFloat(0f, subWidth)
                }
            }
        }
    }

    override fun generate(numSamples: Int, numSets: Int): List<Point2> {
        val points = List(numSamples * numSets) { Point2() }
        val sqRoot = sqrt(numSamples.toFloat()).toInt()

        this.distribute(numSamples, numSets, sqRoot, points)
        this.shuffle(numSamples, numSets, sqRoot, points)

        return points
    }

    /**
     * Shuffles the specified sample points along both the x- and y-axes.
     *
     * @param numSamples
     *        The number of points per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @param sqRoot
     *        The integer square root of the number of points.
     * @param points
     *        The list of sample points to use.
     */
    fun shuffle(numSamples: Int, numSets: Int, sqRoot: Int,
                points: List<Point2>) {
        for(i in 0 until numSets) {
            for (p in 0 until sqRoot) {
                for (q in 0 until sqRoot) {
                    val x0 = (p * sqRoot + nextInt(q, sqRoot)) + i * numSamples
                    val x1 = (p * sqRoot + q) + i * numSamples
                    val y0 = (p + sqRoot * nextInt(q, sqRoot)) + i * numSamples
                    val y1 = (p + sqRoot * q) + i * numSamples

                    points[x0].x = points[x1].x.also {
                        points[x1].x = points[x0].x
                    }
                    points[y0].x = points[y1].x.also {
                        points[y1].x = points[y0].x
                    }
                }
            }
        }
    }
}