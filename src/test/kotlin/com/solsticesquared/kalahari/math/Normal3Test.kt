package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Normal3] objects with
 * random components.
 */
class Normal3Generator : Gen<Normal3> {

    override fun generate(): Normal3 =
        Normal3(Gen.float().generate(), Gen.float().generate(),
                Gen.float().generate())
}

/**
 * Test suite for [Normal3].
 */
class Normal3Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Normal3] instances.
         */
        val Nor3Gen = Normal3Generator()
    }

    init {

        "Adding one normal to another" {
            should("add each normal's components.") {
                forAll(Nor3Gen, Nor3Gen) { a: Normal3, b: Normal3 ->
                    val expected = Normal3(
                        a.x + b.x, a.y + b.y, a.z + b.z
                    )
                    a + b == expected
                }
            }
        }

        "A normal adding itself to another" {
            should("add each of its components to the other.") {
                forAll(Nor3Gen, Nor3Gen) { a: Normal3, b: Normal3 ->
                    val expected = Normal3(
                        a.x + b.x, a.y + b.y, a.z + b.z
                    )
                    a.plusSelf(b) == expected
                }
            }
        }

        "A normal divided by a scalar" {
            should("divide all components by that scalar.") {
                forAll(Nor3Gen, Gen.float()) { normal: Normal3, f: Float ->
                    val ff = when(f == 0f) { false -> f; true -> 1f }
                    val inv = 1f / ff
                    val expected = Normal3(
                        normal.x * inv, normal.y * inv, normal.z * inv
                    )

                    normal / ff == expected
                }
            }
        }

        "A normal dividing itself by a scalar" {
            should("divide each of its components by that scalar.") {
                forAll(Nor3Gen, Gen.float()) { normal: Normal3, f: Float ->
                    val ff = when(f == 0f) { false -> f; true -> 1f }
                    val inv = 1f / ff
                    val expected = Normal3(
                        normal.x * inv, normal.y * inv, normal.z * inv
                    )

                    normal.divSelf(ff) == expected
                }
            }
        }

        "Negating a normal" {
            should("negate each of its components.") {
                forAll(Nor3Gen) { normal: Normal3 ->
                    val expected = Normal3(-normal.x, -normal.y, -normal.z,
                                           false)
                    -normal == expected
                }
            }
        }

        "A normal negating itself" {
            should("negate each of its components.") {
                forAll(Nor3Gen) { normal: Normal3 ->
                    val expected = Normal3(-normal.x, -normal.y, -normal.z,
                                           false)
                    normal.negateSelf() == expected
                }
            }
        }

        "A normal multiplied by a scalar" {
            should("divide all components by that scalar.") {
                forAll(Nor3Gen, Gen.float()) { normal: Normal3, f: Float ->
                    val expected = Normal3(
                        normal.x * f, normal.y * f, normal.z * f
                    )

                    normal * f == expected
                }
            }
        }

        "A normal multiplying itself by a scalar" {
            should("multiply each of its components by that scalar.") {
                forAll(Nor3Gen, Gen.float()) { normal: Normal3, f: Float ->
                    val expected = Normal3(
                        normal.x * f, normal.y * f, normal.z * f
                    )

                    normal.timesSelf(f) == expected
                }
            }
        }
    }
}
