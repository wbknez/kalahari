package com.willoutwest.kalahari.math.sample.generators

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.sample.SampleGenerator

/**
 * Represents an implementation of [SampleGenerator] that uses regular
 * sampling to generate sample points.
 *
 * Regular sampling provides sample points that are located in the exact
 * center of each cell of a grid inside each pixel.
 */
class RegularSampleGenerator : SampleGenerator {

    override fun generate(numSamples: Int, numSets: Int): List<Point2> {
        val length = numSamples * numSets
        val points = List(length) { Point2() }
        val sqRoot = MathUtils.sqrt(numSamples.toFloat()).toInt()

        for (i in 0 until numSets) {
            for (p in 0 until sqRoot) {
                for (q in 0 until sqRoot) {
                    val index = i * numSamples + (p * sqRoot + q)

                    points[index].x = (q + 0.5f) / sqRoot
                    points[index].y = (p + 0.5f) / sqRoot
                }
            }
        }

        return points
    }
}