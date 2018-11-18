package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Tuple3] objects with
 * random components.
 */
class Tuple3Generator : Gen<Tuple3> {

    override fun generate(): Tuple3 =
        Tuple3(Gen.float().generate(), Gen.float().generate(),
               Gen.float().generate())
}

/**
 * Test suite for [Tuple3].
 */
class Tuple3Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Tuple3] instances.
         */
        val Tup3Gen = Tuple3Generator()
    }

    init {

        "Accessing a tuple's components as an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tup3Gen) { tup: Tuple3 ->
                    val x = tup[0]
                    val y = tup[1]
                    val z = tup[2]

                    x == tup.x && y == tup.y && z == tup.z
                }
            }
        }

        "Setting a tuple's components like an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tup3Gen, Tup3Gen) { a: Tuple3, b: Tuple3 ->
                    a[0] = b.x
                    a[1] = b.y
                    a[2] = b.z

                    a[0] == b.x && a[1] == b.y && a[2] == b.z
                }
            }
        }

        "Creating a clone of a tuple" {
            should("copy all components correctly.") {
                forAll(Tup3Gen) { tup: Tuple3 ->
                    val cloned = tup.clone()
                    tup == cloned
                }
            }

            should("not be reference equal.") {
                forAll(Tup3Gen) { tup: Tuple3 ->
                    val cloned = tup.clone()
                    !(cloned === tup)
                }
            }
        }

        "The comparison of one tuple to another" {
            should("only be true if their components are equal.") {
                forAll(Tup3Gen) { a: Tuple3 ->
                    val b = a.clone()
                    a == b
                }
            }

            should("be false if their components are not equal.") {
                forAll(Tup3Gen, Tup3Gen) { a: Tuple3, b: Tuple3 ->
                    val expected = a.x == b.x && a.y == b.y && a.z == b.z
                    (a == b) == expected
                }
            }
        }

        "The deconstruction of a tuple" {
            should("provide components in (x, y, z) form.") {
                forAll(Tup3Gen) { tup: Tuple3 ->
                    val (x, y, z) = tup
                    x == tup.x && y == tup.y && z == tup.z
                }
            }
        }

        "The conversion of a tuple to a String" {
            should("use parentheses instead of arrows.") {
                forAll(Tup3Gen) { tup: Tuple3 ->
                    val expected =
                            "(" + tup.x + ", " + tup.y + ", " + tup.z + ")"
                    tup.toString() == expected
                }
            }
        }

        "Assigning one tuple's components to new values" {
            should("assign their components correctly.") {
                forAll(Tup3Gen, Tup3Gen) { a: Tuple3, b: Tuple3 ->
                    a.set(b.x, b.y, b.z)
                    a.x == b.x && a.y == b.y && a.z == b.z
                }
            }

            should("chain correctly.") {
                forAll(Tup3Gen, Tup3Gen, Tup3Gen) {
                    a: Tuple3, b: Tuple3, c: Tuple3 ->

                    a.set(b.x, b.y, b.z).set(c.x, c.y, c.z)
                    a.x == c.x && a.y == c.y && a.z == c.z
                }
            }
        }

        "Assigning one tuple to another" {
            should("assign their components correctly.") {
                forAll(Tup3Gen, Tup3Gen) { a: Tuple3, b: Tuple3 ->
                    a.set(b)
                    a == b
                }
            }

            should("chain correctly.") {
                forAll(Tup3Gen, Tup3Gen, Tup3Gen) {
                    a: Tuple3, b: Tuple3, c: Tuple3 ->

                    a.set(b).set(c)
                    a == c
                }
            }
        }
    }
}
