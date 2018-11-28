package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Tuple2] objects with
 * random components.
 */
class Tuple2Generator : Gen<Tuple2> {

    override fun generate(): Tuple2 =
        Tuple2(Gen.float().generate(), Gen.float().generate())
}

/**
 * Test suite for [Tuple2].
 */
class Tuple2Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Tuple2] instances.
         */
        val Tup2Gen = Tuple2Generator()
    }

    init {

        "Accessing a tuple's components as an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tup2Gen) { tup: Tuple2 ->
                    val x = tup[0]
                    val y = tup[1]

                    x == tup.x && y == tup.y
                }
            }
        }

        "Setting a tuple's components like an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tup2Gen, Tup2Gen) { a: Tuple2, b: Tuple2 ->
                    a[0] = b.x
                    a[1] = b.y

                    a[0] == b.x && a[1] == b.y
                }
            }
        }

        "Creating a clone of a tuple" {
            should("copy all components correctly.") {
                forAll(Tup2Gen) { tup: Tuple2 ->
                    val cloned = tup.clone()
                    tup == cloned
                }
            }

            should("not be reference equal.") {
                forAll(Tup2Gen) { tup: Tuple2 ->
                    val cloned = tup.clone()
                    !(cloned === tup)
                }
            }
        }

        "The comparison of one tuple to another" {
            should("only be true if their components are equal.") {
                forAll(Tup2Gen) { a: Tuple2 ->
                    val b = a.clone()
                    a == b
                }
            }

            should("be false if their components are not equal.") {
                forAll(Tup2Gen, Tup2Gen) { a: Tuple2, b: Tuple2 ->
                    val expected = a.x == b.x && a.y == b.y
                    (a == b) == expected
                }
            }
        }

        "The deconstruction of a tuple" {
            should("provide components in (x, y, z) form.") {
                forAll(Tup2Gen) { tup: Tuple2 ->
                    val (x, y) = tup
                    x == tup.x && y == tup.y
                }
            }
        }

        "The conversion of a tuple to a String" {
            should("use parentheses instead of arrows.") {
                forAll(Tup2Gen) { tup: Tuple2 ->
                    val expected =
                        "(" + tup.x + ", " + tup.y + ")"
                    tup.toString() == expected
                }
            }
        }

        "Assigning one tuple's components to new values" {
            should("assign their components correctly.") {
                forAll(Tup2Gen, Tup2Gen) { a: Tuple2, b: Tuple2 ->
                    a.set(b.x, b.y)
                    a.x == b.x && a.y == b.y
                }
            }

            should("chain correctly.") {
                forAll(Tup2Gen, Tup2Gen, Tup2Gen) {
                    a: Tuple2, b: Tuple2, c: Tuple2 ->

                    a.set(b.x, b.y).set(c.x, c.y)
                    a.x == c.x && a.y == c.y
                }
            }
        }

        "Assigning one tuple to another" {
            should("assign their components correctly.") {
                forAll(Tup2Gen, Tup2Gen) { a: Tuple2, b: Tuple2 ->
                    a.set(b)
                    a == b
                }
            }

            should("chain correctly.") {
                forAll(Tup2Gen, Tup2Gen, Tup2Gen) {
                    a: Tuple2, b: Tuple2, c: Tuple2 ->

                    a.set(b).set(c)
                    a == c
                }
            }
        }
    }
}
