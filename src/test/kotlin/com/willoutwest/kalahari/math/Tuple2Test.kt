package com.willoutwest.kalahari.math

import io.kotlintest.matchers.types.shouldNotBeSameInstanceAs
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Tuple2] objects with
 * random components.
 */
class Tuple2Generator : Gen<Tuple2> {

    override fun constants(): Iterable<Tuple2> = emptyList()

    override fun random(): Sequence<Tuple2> = generateSequence {
        Tuple2(Gen.float().random().first(),
               Gen.float().random().first())
    }
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

        "Accessing a tuple's components using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Tup2Gen) { tup: Tuple2 ->
                    tup[0] shouldBe tup.x
                    tup[1] shouldBe tup.y
                }
            }
        }

        "Setting a tuple's components using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Tup2Gen, Tup2Gen) { a: Tuple2, b: Tuple2 ->
                    a[0] = b.x
                    a[1] = b.y

                    a[0] shouldBe b.x
                    a[1] shouldBe b.y
                }
            }
        }

        "Creating a clone of a tuple" {
            should("copy all components correctly.") {
                assertAll(Tup2Gen) { tup: Tuple2 ->
                    tup.clone() shouldBe tup
                }
            }

            should("not be reference equal.") {
                assertAll(Tup2Gen) { tup: Tuple2 ->
                    tup.clone().shouldNotBeSameInstanceAs(tup)
                }
            }
        }

        "Comparing one tuple to another" {
            should("only be true if their components are equal.") {
                assertAll(Tup2Gen) { a: Tuple2 ->
                    Tuple2(a) shouldBe a
                }
            }

            should("be false if their components are not equal.") {
                assertAll(Tup2Gen) { a: Tuple2 ->
                    Tuple2(a.x * a.x, a.y * a.y) shouldNotBe a
                }
            }
        }

        "Destructuring a tuple" {
            should("provide components in (x, y, z) form.") {
                assertAll(Tup2Gen) { tup: Tuple2 ->
                    val (x, y) = tup

                    x shouldBe tup.x
                    y shouldBe tup.y
                }
            }
        }

        "Converting a tuple to a String" {
            should("use parentheses instead of arrows.") {
                assertAll(Tup2Gen) { tup: Tuple2 ->
                    tup.toString() shouldBe "(${tup.x}, ${tup.y})"
                }
            }
        }

        "Assigning one tuple's components to new values" {
            should("assign their components correctly.") {
                assertAll(Tup2Gen, Tup2Gen) { a: Tuple2, b: Tuple2 ->
                    a.set(b.x, b.y) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Tup2Gen, Tup2Gen, Tup2Gen) {
                    a: Tuple2, b: Tuple2, c: Tuple2 ->

                    a.set(b.x, b.y).set(c.x, c.y) shouldBe c
                }
            }
        }

        "Assigning one tuple to another" {
            should("assign their components correctly.") {
                assertAll(Tup2Gen, Tup2Gen) { a: Tuple2, b: Tuple2 ->
                    a.set(b) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Tup2Gen, Tup2Gen, Tup2Gen) {
                    a: Tuple2, b: Tuple2, c: Tuple2 ->

                    a.set(b).set(c) shouldBe c
                }
            }
        }
    }
}