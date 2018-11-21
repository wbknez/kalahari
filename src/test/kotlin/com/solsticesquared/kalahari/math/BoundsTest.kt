package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Bounds] objects with
 * random dimensions.
 */
class BoundsGenerator : Gen<Bounds> {

    override fun generate(): Bounds {
        val x = Gen.int().generate()
        val y = Gen.int().generate()
        val height = Gen.int().generate() + y + 1
        val width  = Gen.int().generate() + x + 1

        return Bounds(x, y, width, height)
    }
}

/**
 * Test suite for [Bounds].
 */
class BoundsTest : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Bounds] instances.
         */
        val BoundsGen = BoundsGenerator()
    }

    init {

        "the area of a finite, bounded region" {
            should("be equal to the width times the height.") {
                forAll(BoundsGen) { bounds: Bounds ->
                    bounds.area == bounds.width * bounds.height
                }
            }
        }
    }
}
