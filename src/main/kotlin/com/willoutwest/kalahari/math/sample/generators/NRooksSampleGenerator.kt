package com.willoutwest.kalahari.math.sample.generators

import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.RandomUtils.Companion.nextFloat
import com.willoutwest.kalahari.math.RandomUtils.Companion.nextInt
import com.willoutwest.kalahari.math.sample.SampleGenerator

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
     * @param points
     *        The list of sample points to use.
     */
    fun distribute(numSamples: Int, numSets: Int, points: List<Point2>) {
        for(i in 0 until numSets) {
            for(j in 0 until numSamples) {
                val index = i * numSamples + j

                points[index].x = (j + nextFloat()) / numSamples
                points[index].y = (j + nextFloat()) / numSamples
            }
        }
    }

    override fun generate(numSamples: Int, numSets: Int): List<Point2> {
        val points = List(numSamples * numSets) { Point2() }

        this.distribute(numSamples, numSets, points)
        this.shuffle(numSamples, numSets, points)

        return points
    }

    /**
     * Shuffles the specified sample points along both the x- and y-axes.
     *
     * @param numSamples
     *        The number of samples per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @param points
     *        The list of sample points to use.
     */
    fun shuffle(numSamples: Int, numSets: Int, points: List<Point2>) {
        for(i in 0 until numSets) {
            val sIndex = i * numSamples

            for (j in 0 until numSamples) {
                val x = sIndex + nextInt(numSamples)
                val y = sIndex + nextInt(numSamples)
                val t = sIndex + j

                this.swap(x, y, t, points)
            }
        }
    }

    fun swap(x: Int, y: Int, t: Int, points: List<Point2>) {
        points[x].x = points[t].x.also { points[t].x = points[x].x }
        points[y].y = points[t].y.also { points[t].y = points[y].y }
    }
}