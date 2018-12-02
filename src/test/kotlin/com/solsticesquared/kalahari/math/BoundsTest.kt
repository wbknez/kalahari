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
        val x = Gen.choose(1, 1000).generate()
        val y = Gen.choose(1, 1000).generate()
        val height = Gen.choose(y + 1, (y * 2) + 1).generate()
        val width  = Gen.choose(x + 1, (x * 2) + 1).generate()

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
