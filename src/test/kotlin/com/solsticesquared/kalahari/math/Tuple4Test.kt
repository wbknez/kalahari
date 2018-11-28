package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Tuple4] objects with
 * random components.
 */
class Tuple4Generator : Gen<Tuple4> {

    override fun generate(): Tuple4 =
        Tuple4(Gen.float().generate(), Gen.float().generate(),
               Gen.float().generate(), Gen.float().generate())
}

/**
 * Test suite for [Tuple4].
 */
class Tuple4Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Tuple4] instances.
         */
        val Tup4Gen = Tuple4Generator()
    }

    init {

        "Accessing a tuple's components as an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tup4Gen) { tup: Tuple4 ->
                    val x = tup[0]
                    val y = tup[1]
                    val z = tup[2]
                    val w = tup[3]

                    x == tup.x && y == tup.y && z == tup.z && w == tup.w
                }
            }
        }

        "Setting a tuple's components like an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tup4Gen, Tup4Gen) { a: Tuple4, b: Tuple4 ->
                    a[0] = b.x
                    a[1] = b.y
                    a[2] = b.z
                    a[3] = b.w

                    a[0] == b.x && a[1] == b.y && a[2] == b.z &&
                    a[3] == b.w
                }
            }
        }

        "Creating a clone of a tuple" {
            should("copy all components correctly.") {
                forAll(Tup4Gen) { tup: Tuple4 ->
                    val cloned = tup.clone()
                    tup == cloned
                }
            }

            should("not be reference equal.") {
                forAll(Tup4Gen) { tup: Tuple4 ->
                    val cloned = tup.clone()
                    !(cloned === tup)
                }
            }
        }

        "The comparison of one tuple to another" {
            should("only be true if their components are equal.") {
                forAll(Tup4Gen) { a: Tuple4 ->
                    val b = a.clone()
                    a == b
                }
            }

            should("be false if their components are not equal.") {
                forAll(Tup4Gen, Tup4Gen) { a: Tuple4, b: Tuple4 ->
                    val expected = a.x == b.x && a.y == b.y && a.z == b.z &&
                                   a.w == b.w
                    (a == b) == expected
                }
            }
        }

        "The deconstruction of a tuple" {
            should("provide components in (x, y, z) form.") {
                forAll(Tup4Gen) { tup: Tuple4 ->
                    val (x, y, z, w) = tup
                    x == tup.x && y == tup.y && z == tup.z && w == tup.w
                }
            }
        }

        "The conversion of a tuple to a String" {
            should("use parentheses instead of arrows.") {
                forAll(Tup4Gen) { tup: Tuple4 ->
                    val expected = "(${tup.x}, ${tup.y}, ${tup.z}, ${tup.w})"
                    tup.toString() == expected
                }
            }
        }

        "Assigning one tuple's components to new values" {
            should("assign their components correctly.") {
                forAll(Tup4Gen, Tup4Gen) { a: Tuple4, b: Tuple4 ->
                    a.set(b.x, b.y, b.z, b.w)
                    a.x == b.x && a.y == b.y && a.z == b.z && a.w == b.w
                }
            }

            should("chain correctly.") {
                forAll(Tup4Gen, Tup4Gen, Tup4Gen) {
                    a: Tuple4, b: Tuple4, c: Tuple4 ->

                    a.set(b.x, b.y, b.z, b.w).set(c.x, c.y, c.z, c.w)
                    a.x == c.x && a.y == c.y && a.z == c.z && a.w == c.w
                }
            }
        }

        "Assigning one tuple to another" {
            should("assign their components correctly.") {
                forAll(Tup4Gen, Tup4Gen) { a: Tuple4, b: Tuple4 ->
                    a.set(b)
                    a == b
                }
            }

            should("chain correctly.") {
                forAll(Tup4Gen, Tup4Gen, Tup4Gen) {
                    a: Tuple4, b: Tuple4, c: Tuple4 ->

                    a.set(b).set(c)
                    a == c
                }
            }
        }
    }
}
