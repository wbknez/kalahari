package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.Point2
import com.solsticesquared.kalahari.math.random.RandomUtils

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

    override fun generate(numSamples: Int, numSets: Int): SampleBasis2 {
        val length  = numSamples * numSets
        val random  = RandomUtils.localRandom
        val samples = List(length) { Point2() }

        for(i in 0..(length - 1)) {
            samples[i].x = random.nextFloat()
            samples[i].y = random.nextFloat()
        }

        return SampleBasis2(numSamples, numSets, samples)
    }
}
