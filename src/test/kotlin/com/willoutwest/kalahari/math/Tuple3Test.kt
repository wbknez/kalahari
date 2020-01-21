package com.willoutwest.kalahari.math

import io.kotlintest.matchers.types.shouldNotBeSameInstanceAs
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Tuple3] objects with
 * random components.
 */
class Tuple3Generator : Gen<Tuple3> {

    override fun constants(): Iterable<Tuple3> = emptyList()

    override fun random(): Sequence<Tuple3> = generateSequence {
        Tuple3(Gen.float().random().first(),
               Gen.float().random().first(),
               Gen.float().random().first())
    }
}

fun Gen.Companion.tuple3(): Gen<Tuple3> = Tuple3Generator()

/**
 * Test suite for [Tuple3].
 */
class Tuple3Test : ShouldSpec() {

     init {

        "Accessing a tuple's components using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Gen.tuple3()) { tup: Tuple3 ->
                    tup[0] shouldBe tup.x
                    tup[1] shouldBe tup.y
                    tup[2] shouldBe tup.z
                }
            }
        }

        "Setting a tuple's components using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Gen.tuple3(), Gen.tuple3()) { a: Tuple3, b: Tuple3 ->
                    a[0] = b.x
                    a[1] = b.y
                    a[2] = b.z

                    a[0] shouldBe b.x
                    a[1] shouldBe b.y
                    a[2] shouldBe b.z
                }
            }
        }

        "Creating a clone of a tuple" {
            should("copy all components correctly.") {
                assertAll(Gen.tuple3()) { tup: Tuple3 ->
                    tup.clone() shouldBe tup
                }
            }

            should("not be reference equal.") {
                assertAll(Gen.tuple3()) { tup: Tuple3 ->
                    tup.clone().shouldNotBeSameInstanceAs(tup)
                }
            }
        }

        "Comparing one tuple to another" {
            should("only be true if their components are equal.") {
                assertAll(Gen.tuple3()) { a: Tuple3 ->
                    Tuple3(a) shouldBe a
                }
            }

            should("be false if their components are not equal.") {
                assertAll(Gen.tuple3()) { a: Tuple3 ->
                    Tuple3(a.x * a.x, a.y * a.y, a.z * a.z) shouldNotBe a
                }
            }
        }

        "Destructuring a tuple" {
            should("provide components in (x, y, z) form.") {
                assertAll(Gen.tuple3()) { tup: Tuple3 ->
                    val (x, y, z) = tup

                    x shouldBe tup.x
                    y shouldBe tup.y
                    z shouldBe tup.z
                }
            }
        }

        "Converting a tuple to a String" {
            should("use parentheses instead of arrows.") {
                assertAll(Gen.tuple3()) { tup: Tuple3 ->
                    tup.toString() shouldBe "(${tup.x}, ${tup.y}, ${tup.z})"
                }
            }
        }

        "Assigning one tuple's components to new values" {
            should("assign their components correctly.") {
                assertAll(Gen.tuple3(), Gen.tuple3()) { a: Tuple3, b: Tuple3 ->
                    a.set(b.x, b.y, b.z) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Gen.tuple3(), Gen.tuple3(), Gen.tuple3()) {
                    a: Tuple3, b: Tuple3, c: Tuple3 ->

                    a.set(b.x, b.y, b.z).set(c.x, c.y, c.z) shouldBe c
                }
            }
        }

        "Assigning one tuple to another" {
            should("assign their components correctly.") {
                assertAll(Gen.tuple3(), Gen.tuple3()) { a: Tuple3, b: Tuple3 ->
                    a.set(b) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Gen.tuple3(), Gen.tuple3(), Gen.tuple3()) {
                    a: Tuple3, b: Tuple3, c: Tuple3 ->

                    a.set(b).set(c) shouldBe c
                }
            }
        }
    }
}
