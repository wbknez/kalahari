package com.willoutwest.kalahari.math.sample.generators

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.RandomUtils.Companion.nextFloat
import com.willoutwest.kalahari.math.sample.SampleGenerator

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

    override fun generate(numSamples: Int, numSets: Int): List<Point2> {
        val length = numSamples * numSets
        val points = List(length) { Point2() }
        val sqRoot = MathUtils.sqrt(numSamples.toFloat()).toInt()

        for(i in 0 until numSets) {
            for(j in 0 until sqRoot) {
                for(k in 0 until sqRoot) {
                    val index = i * numSamples + (j * sqRoot + k)

                    points[index].x = (k + nextFloat()) / sqRoot
                    points[index].y = (j + nextFloat()) / sqRoot
                }
            }
        }

        return points
    }
}