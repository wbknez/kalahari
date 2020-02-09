package com.willoutwest.kalahari.math.sample.generators

import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.RandomUtils.Companion.nextFloat
import com.willoutwest.kalahari.math.sample.SampleGenerator

/**
 * Represents an implementation of {SampleGenerator] that uses random
 * sampling to generate sample points.
 *
 * Random sampling provides sample points that are randomly located in each
 * cell of a grid inside each pixel.  This effectively replaces aliasing
 * artifacts with pure noise.  Depending on the image this may result in a
 * vast improvement in rendering quality for minimal computational cost.
 */
class RandomSampleGenerator : SampleGenerator {

    override fun generate(numSamples: Int, numSets: Int): List<Point2> =
        List(numSamples * numSets) {
            Point2(nextFloat(), nextFloat())
        }
}