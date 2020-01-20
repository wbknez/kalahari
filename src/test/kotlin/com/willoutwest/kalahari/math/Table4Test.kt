package com.willoutwest.kalahari.math

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.types.shouldNotBeSameInstanceAs
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Table4] objects with
 * random components.
 */
class Table4Generator : Gen<Table4> {

    override fun constants(): Iterable<Table4> = emptyList()

    override fun random(): Sequence<Table4> = generateSequence {
        Table4(FloatArray(16) {
            Gen.float().random().first()
        })
    }
}

/**
 * Test suite for [Table4].
 */
class Table4Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Table4] instances.
         */
        val Tab4Gen = Table4Generator()
    }

    init {

        "Accessing a table's elements using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Tab4Gen) { tab: Table4 ->
                    tab[0] shouldBe tab.t00; tab[1] shouldBe tab.t01
                    tab[2] shouldBe tab.t02; tab[3] shouldBe tab.t03
                    tab[4] shouldBe tab.t10; tab[5] shouldBe tab.t11
                    tab[6] shouldBe tab.t12; tab[7] shouldBe tab.t13
                    tab[8] shouldBe tab.t20; tab[9] shouldBe tab.t21
                    tab[10] shouldBe tab.t22; tab[11] shouldBe tab.t23
                    tab[12] shouldBe tab.t30; tab[13] shouldBe tab.t31
                    tab[14] shouldBe tab.t32; tab[15] shouldBe tab.t33
                }
            }
        }

        "Setting a table's elements using indices" {
            should("use indices that correspond to a natural order.") {
                assertAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a[0] = b.t00; a[1] = b.t01; a[2] = b.t02; a[3] = b.t03
                    a[4] = b.t10; a[5] = b.t11; a[6] = b.t12; a[7] = b.t13
                    a[8] = b.t20; a[9] = b.t21; a[10] = b.t22; a[11] = b.t23
                    a[12] = b.t30; a[13] = b.t31; a[14] = b.t32; a[15] = b.t33

                    a.toArray() shouldBe b.toArray()
                }
            }
        }

        "Creating a clone of a table" {
            should("copy all elements correctly.") {
                assertAll(Tab4Gen) { tab: Table4 ->
                    tab.clone() shouldBe tab
                }
            }

            should("not be reference equal.") {
                assertAll(Tab4Gen) { tab: Table4 ->
                    tab.clone().shouldNotBeSameInstanceAs(tab)
                }
            }
        }

        "Comparing one table to another" {
            should("only be true if their elements are equal.") {
                assertAll(Tab4Gen) { a: Table4 ->
                    Table4(a) shouldBe a
                }
            }

            should("be false if their elements are not equal") {
                assertAll(Tab4Gen) { a: Table4 ->
                    val array = a.toArray().map { it * it }.toFloatArray()

                    Table4(array) shouldNotBe a
                }
            }
        }

        "Destructuring a table" {
            should("provide elements in [1 ... n] format.") {
                assertAll(Tab4Gen) { tab: Table4 ->
                    val (t00, t01, t02, t03,
                        t10, t11, t12, t13,
                        t20, t21, t22, t23,
                        t30, t31, t32, t33) = tab
                    val array = floatArrayOf(t00, t01, t02, t03,
                                             t10, t11, t12, t13,
                                             t20, t21, t22, t23,
                                             t30, t31, t32, t33)

                    Table4(array) shouldBe tab
                }
            }
        }

        "Converting a table to an array" {
            should("copy all elements in order.") {
                assertAll(Tab4Gen) { tab: Table4 ->
                    Table4(tab.toArray()) shouldBe tab
                }
            }

            should("produce an array sized accordingly.") {
                assertAll(Tab4Gen) { tab: Table4 ->
                    tab.toArray().toList().shouldHaveSize(16)
                }
            }
        }

        "Converting a table to a String" {
            should("use parentheses instead of brackets.") {
                assertAll(Tab4Gen) { tab: Table4 ->
                    val expr = tab.toArray().joinToString(",", "(", ")")

                    expr shouldBe tab.toString()
                }
            }
        }

        "Setting a table's elements to new values individually" {
            should("assign their elements correctly.") {
                assertAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a.set(b.t00, b.t01, b.t02, b.t03,
                          b.t10, b.t11, b.t12, b.t13,
                          b.t20, b.t21, b.t22, b.t23,
                          b.t30, b.t31, b.t32, b.t33)

                    a shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Tab4Gen, Tab4Gen,
                          Tab4Gen) { a: Table4, b: Table4, c: Table4 ->

                    a.set(b.t00, b.t01, b.t02, b.t03,
                          b.t10, b.t11, b.t12, b.t13,
                          b.t20, b.t21, b.t22, b.t23,
                          b.t30, b.t31, b.t32, b.t33)
                        .set(c.t00, c.t01, c.t02, c.t03,
                             c.t10, c.t11, c.t12, c.t13,
                             c.t20, c.t21, c.t22, c.t23,
                             c.t30, c.t31, c.t32, c.t33)

                    a shouldBe c
                }
            }
        }

        "Setting a table's elements to new values with an array" {
            should("assign their elements correctly.") {
                assertAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a.set(b.toArray()) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Tab4Gen, Tab4Gen,
                          Tab4Gen) { a: Table4, b: Table4, c: Table4 ->

                    a.set(b.toArray()).set(c.toArray()) shouldBe c
                }
            }
        }

        "Setting a table's elements to new values with another table" {
            should("assign their elements correctly.") {
                assertAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a.set(b) shouldBe b
                }
            }

            should("chain correctly.") {
                assertAll(Tab4Gen, Tab4Gen,
                          Tab4Gen) { a: Table4, b: Table4, c: Table4 ->

                    a.set(b).set(c) shouldBe c
                }
            }
        }
    }
}
