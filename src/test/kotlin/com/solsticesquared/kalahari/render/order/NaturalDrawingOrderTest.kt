package com.solsticesquared.kalahari.render.order

import com.solsticesquared.kalahari.math.Bounds
import com.solsticesquared.kalahari.math.Coords
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec
import java.util.concurrent.ThreadLocalRandom

/**
 * Represents an implementation of [Gen] that generates random [Integer] values
 * between a specific range.
 *
 * @property lowerBound
 *           The minimum value to generate, inclusive.
 * @property upperBound
 *           The maximum value to generate, exclusive.
 */
class BoundedIntGenerator(var lowerBound: Int, var upperBound: Int) : Gen<Int> {

    override fun generate(): Int =
        ThreadLocalRandom.current().nextInt(this.lowerBound, this.upperBound)
}

/**
 * Test suite for [NaturalDrawingOrder].
 */
class NaturalDrawingOrderTest : ShouldSpec() {

    init {

        "Creating a drawing order with natural ordering" {
            should("produce a column-major (x-axis) natural ordering.") {
                val wGen = BoundedIntGenerator(1, 200)
                val hGen = BoundedIntGenerator(1, 200)

                forAll(wGen, hGen) { width: Int, height: Int ->
                    val expected = mutableListOf<Coords>()

                    for(y in 0..(height - 1)) {
                        for(x in 0..(width - 1)) {
                            expected.add(Coords(x, y))
                        }
                    }

                    val results = NaturalDrawingOrder().create(
                        Bounds(0, 0, width, height)
                    )

                    results == expected
                }
            }
        }
    }
}
