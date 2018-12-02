package com.solsticesquared.kalahari.math.sample

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [Sampler].
 */
class SamplerTest : ShouldSpec() {

    init {

        "Creating a new list of shuffled indices" {
            should("contain from [0, numSamples] per set for all sets.") {
                forAll(Gen.choose(1, 100), Gen.choose(1, 100)) {
                    numSamples: Int, numSets: Int ->

                    val indices = createShuffledIndices(numSamples, numSets)
                    val expected = numSamples * numSets
                    var result   = 0

                    for(i in 0..(numSets - 1)) {
                        val range = IntRange(i * numSamples,
                                             ((i + 1) * numSamples) - 1)
                        result += indices.sliceArray(range).toSet().size
                    }

                    result == expected
                }
            }
        }
    }
}
