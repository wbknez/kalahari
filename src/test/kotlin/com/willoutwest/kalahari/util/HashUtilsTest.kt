package com.willoutwest.kalahari.util

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [hash].
 */
class HashUtilsTest : ShouldSpec() {

    init {

        "The hash code of a single null object" {
            should("be the initial value times the multiplier only.") {
                assertAll { initial: Int, multiplier: Int ->
                    val result = hash(null, initialValue=initial,
                                      multiplier = multiplier)

                    result shouldBe (initial * multiplier)
                }
            }
        }

        "The hash code of some random elements" {
            should("be the combined algorithmic hash code as defined.") {
                assertAll { a: Int, b: Float, c: Double, d:String ->

                    var result = 17
                    result = (37 * result) + a.hashCode()
                    result = (37 * result) + b.hashCode()
                    result = (37 * result) + c.hashCode()
                    result = (37 * result) + d.hashCode()

                    hash(a, b, c, d) shouldBe result
                }
            }
        }
    }
}
