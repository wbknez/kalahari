package com.solsticesquared.kalahari.render.order

import com.solsticesquared.kalahari.math.Bounds
import com.solsticesquared.kalahari.math.Coords
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [NaturalDrawingOrder].
 */
class NaturalDrawingOrderTest : ShouldSpec() {

    init {

        "Creating a drawing order with natural ordering" {
            should("produce a column-major (x-axis) natural ordering.") {
                forAll(Gen.choose(1, 200), Gen.choose(1, 200)) {
                    width: Int, height: Int ->

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
