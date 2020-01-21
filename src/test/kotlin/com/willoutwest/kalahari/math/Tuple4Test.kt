package com.willoutwest.kalahari.math

import io.kotlintest.matchers.types.shouldNotBeSameInstanceAs
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Tuple4] objects with
 * random components.
 */
class Tuple4Generator : Gen<Tuple4> {

    override fun constants(): Iterable<Tuple4> = emptyList()

    override fun random(): Sequence<Tuple4> = generateSequence {
        Tuple4(Gen.float().random().first(),
               Gen.float().random().first(),
               Gen.float().random().first(),
               Gen.float().random().first())
    }
}

fun Gen.Companion.tuple4(): Gen<Tuple4> = Tuple4Generator()

/**
 * Test suite for [Tuple4].
 */
class Tuple4Test : ShouldSpec() {

    init {

        "Accessing a tuple's components using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Gen.tuple4()) { tup: Tuple4 ->
                    tup[0] shouldBe tup.x
                    tup[1] shouldBe tup.y
                    tup[2] shouldBe tup.z
                    tup[3] shouldBe tup.w
                }
            }
        }

        "Setting a tuple's components using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Gen.tuple4(), Gen.tuple4()) { a: Tuple4, b: Tuple4 ->
                    a[0] = b.x
                    a[1] = b.y
                    a[2] = b.z
                    a[3] = b.w

                    a[0] shouldBe b.x
                    a[1] shouldBe b.y
                    a[2] shouldBe b.z
                    a[3] shouldBe b.w
                }
            }
        }

        "Creating a clone of a tuple" {
            should("copy all components correctly.") {
                assertAll(Gen.tuple4()) { tup: Tuple4 ->
                    tup.clone() shouldBe tup
                }
            }

            should("not be reference equal.") {
                assertAll(Gen.tuple4()) { tup: Tuple4 ->
                    tup.clone().shouldNotBeSameInstanceAs(tup)
                }
            }
        }

        "Comparing one tuple to another" {
            should("only be true if their components are equal.") {
                assertAll(Gen.tuple4()) { a: Tuple4 ->
                    Tuple4(a) shouldBe a
                }
            }

            should("be false if their components are not equal.") {
                assertAll(Gen.tuple4()) { a: Tuple4 ->
                    val b = Tuple4(a.x * a.x, a.y * a.y, a.z * a.z, a.w * a.w)

                    b shouldNotBe a
                }
            }
        }

        "Destructuring a tuple" {
            should("provide components in (x, y, z) form.") {
                assertAll(Gen.tuple4()) { tup: Tuple4 ->
                    val (x, y, z, w) = tup

                    x shouldBe tup.x
                    y shouldBe tup.y
                    z shouldBe tup.z
                    w shouldBe tup.w
                }
            }
        }

        "Converting a tuple to a String" {
            should("use parentheses instead of arrows.") {
                assertAll(Gen.tuple4()) { tup: Tuple4 ->
                    tup.toString() shouldBe "(${tup.x}, ${tup.y}, ${tup.z}, " +
                                            "${tup.w})"
                }
            }
        }

        "Assigning one tuple's components to new values" {
            should("assign their components correctly.") {
                assertAll(Gen.tuple4(), Gen.tuple4()) { a: Tuple4, b: Tuple4 ->
                    a.set(b.x, b.y, b.z, b.w) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Gen.tuple4(), Gen.tuple4(), Gen.tuple4()) {
                    a: Tuple4, b: Tuple4, c: Tuple4 ->

                    val d = a.set(b.x, b.y, b.z, b.w).set(c.x, c.y, c.z, c.w)

                    d shouldBe c
                }
            }
        }

        "Assigning one tuple to another" {
            should("assign their components correctly.") {
                assertAll(Gen.tuple4(), Gen.tuple4()) { a: Tuple4, b: Tuple4 ->
                    a.set(b) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Gen.tuple4(), Gen.tuple4(), Gen.tuple4()) {
                    a: Tuple4, b: Tuple4, c: Tuple4 ->

                    a.set(b).set(c) shouldBe c
                }
            }
        }
    }
}