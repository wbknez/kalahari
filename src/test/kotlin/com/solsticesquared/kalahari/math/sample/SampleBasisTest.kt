package com.solsticesquared.kalahari.math.sample

import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.ShouldSpec
import java.lang.IllegalArgumentException

/**
 * Test suite for [SampleBasis].
 */
class SampleBasisTest : ShouldSpec() {

    init {

        "Constructing a sample basis of any type" {
            should("The number of samples in the collection must be the same " +
                   "size as the number of samples times the number of sets.") {
                shouldThrow<IllegalArgumentException>{
                    SampleBasis<Int>(4, 4, List(15, { it }))
                }
                shouldThrow<IllegalArgumentException>{
                    SampleBasis<Int>(4, 5, List(16, { it }))
                }
                shouldThrow<IllegalArgumentException>{
                    SampleBasis<Int>(5, 4, List(16, { it }))
                }
            }
        }
    }
}
