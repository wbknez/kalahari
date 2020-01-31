package com.willoutwest.kalahari.render

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import java.lang.IllegalArgumentException

/**
 * Test suite for [ThreadingModel].
 */
class ThreadingModelTest : ShouldSpec() {

    init {

        "Creating a threading model with a zero or negative thread count" {
            should("throw an exception") {
                shouldThrow<IllegalArgumentException> {
                    ThreadingModel(0)
                }

                assertAll(Gen.negativeIntegers()) { numThreads: Int ->
                    shouldThrow<IllegalArgumentException> {
                        ThreadingModel(numThreads)
                    }
                }
            }
        }
    }
}