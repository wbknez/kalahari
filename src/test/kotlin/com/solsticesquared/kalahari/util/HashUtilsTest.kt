package com.solsticesquared.kalahari.util

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [hash].
 */
class HashUtilsTest : ShouldSpec() {

    init {

        "The hash code of null objects" {
            should("be the initial value times the multiplier.") {
                hash(null) shouldBe (17 * 37)
            }
        }

        "The hash code of some random elements" {
            should("be the combined algorithmic hash code as defined.") {
                forAll(Gen.int(), Gen.float(), Gen.double()) {
                    a: Int, b: Float, c: Double ->

                    var result = 17
                    result = (37 * result) + a.hashCode()
                    result = (37 * result) + b.hashCode()
                    result = (37 * result) + c.hashCode()

                    hash(a, b, c) == result
                }
            }
        }
    }
}
