package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Table4] objects with
 * random components.
 */
class Table4Generator : Gen<Table4> {

    override fun generate(): Table4 =
        Table4(FloatArray(16) { Gen.float().generate() })
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

        "Accessing a table's elements like an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tab4Gen) { tab: Table4 ->
                    val t00 = tab[0]; val t01 = tab[1]
                    val t02 = tab[2]; val t03 = tab[3]
                    val t10 = tab[4]; val t11 = tab[5]
                    val t12 = tab[6]; val t13 = tab[7]
                    val t20 = tab[8]; val t21 = tab[9]
                    val t22 = tab[10]; val t23 = tab[11]
                    val t30 = tab[12]; val t31 = tab[13]
                    val t32 = tab[14]; val t33 = tab[15]

                    val array = floatArrayOf(t00, t01, t02, t03,
                                             t10, t11, t12, t13,
                                             t20, t21, t22, t23,
                                             t30, t31, t32, t33)
                    tab == Table4(array)
                }
            }
        }

        "Setting a table's elements like an array" {
            should("use indices that correspond to a natural order.") {
                forAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a[0] = b.t00; a[1] = b.t01; a[2] = b.t02; a[3] = b.t03
                    a[4] = b.t10; a[5] = b.t11; a[6] = b.t12; a[7] = b.t13
                    a[8] = b.t20; a[9] = b.t21; a[10] = b.t22; a[11] = b.t23
                    a[12] = b.t30; a[13] = b.t31; a[14] = b.t32; a[15] = b.t33

                    a.toArray() contentEquals b.toArray()
                }
            }
        }

        "Creating the clone of a table" {
            should("copy all elements correctly.") {
                forAll(Tab4Gen) { tab: Table4 ->
                    val cloned = tab.clone()
                    cloned.toArray() contentEquals tab.toArray()
                }
            }

            should("not be reference equa.") {
                forAll(Tab4Gen) { tab: Table4 ->
                    val cloned = tab.clone()
                    !(cloned === tab)
                }
            }
        }

        "The comparison of one tuple to another" {
            should("only be true if their elements are equal.") {
                forAll(Tab4Gen) { a: Table4 ->
                    val b = a.clone()
                    a == b == (a.toArray() contentEquals b.toArray())
                }
            }

            should("be false if their elements are not equal") {
                forAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    var result = true
                    for(i in 0..15) {
                        result = result && (a[i] == b[i])
                    }

                    a == b == result
                }
            }
        }

        "The deconstruction of a table" {
            should("provide elements in [1 ... n] format.") {
                forAll(Tab4Gen) { tab: Table4 ->
                    val (t00, t01, t02, t03,
                        t10, t11, t12, t13,
                        t20, t21, t22, t23,
                        t30, t31, t32, t33) = tab
                    val array = floatArrayOf(t00, t01, t02, t03,
                                             t10, t11, t12, t13,
                                             t20, t21, t22, t23,
                                             t30, t31, t32, t33)
                    tab.toArray() contentEquals array
                }
            }
        }

        "The conversion of a table to an array" {
            should("copy all elements in order.") {
                forAll(Tab4Gen) { tab: Table4 ->
                    val arr = tab.toArray()

                    arr[0] == tab.t00 && arr[1] == tab.t01 &&
                    arr[2] == tab.t02 && arr[3] == tab.t03 &&
                    arr[4] == tab.t10 && arr[5] == tab.t11 &&
                    arr[6] == tab.t12 && arr[7] == tab.t13 &&
                    arr[8] == tab.t20 && arr[9] == tab.t21 &&
                    arr[10] == tab.t22 && arr[11] == tab.t23 &&
                    arr[12] == tab.t30 && arr[13] == tab.t31 &&
                    arr[14] == tab.t32 && arr[15] == tab.t33
                }
            }

            should("produce an array sized accordingly.") {
                forAll(Tab4Gen) { tab: Table4 ->
                    tab.toArray().size == 16
                }
            }
        }

        "The conversion of a table to a String" {
            should("Use parentheses instead of brackets.") {
                forAll(Tab4Gen) { tab: Table4 ->
                    val expected = tab.toArray().joinToString(",", "(", ")")
                    tab.toString() == expected
                }
            }
        }

        "Assigning one table's elements to new values" {
            should("assign their elements correctly.") {
                forAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a.set(b.t00, b.t01, b.t02, b.t03,
                          b.t10, b.t11, b.t12, b.t13,
                          b.t20, b.t21, b.t22, b.t23,
                          b.t30, b.t31, b.t32, b.t33)
                    a.toArray() contentEquals b.toArray()
                }
            }

            should("chain correctly.") {
                forAll(Tab4Gen, Tab4Gen, Tab4Gen) { a: Table4, b: Table4, c: Table4 ->

                    a.set(b.t00, b.t01, b.t02, b.t03,
                          b.t10, b.t11, b.t12, b.t13,
                          b.t20, b.t21, b.t22, b.t23,
                          b.t30, b.t31, b.t32, b.t33)
                        .set(c.t00, c.t01, c.t02, c.t03,
                             c.t10, c.t11, c.t12, c.t13,
                             c.t20, c.t21, c.t22, c.t23,
                             c.t30, c.t31, c.t32, c.t33)

                    a.toArray() contentEquals c.toArray()
                }
            }
        }

        "Assigning one table's elements to those of an array" {
            should("assign their elements correctly.") {
                forAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a.set(b.toArray())
                    a.toArray() contentEquals b.toArray()
                }
            }

            should("chain correctly.") {
                forAll(Tab4Gen, Tab4Gen, Tab4Gen) { a: Table4, b: Table4, c: Table4 ->

                    a.set(b.toArray()).set(c.toArray())
                    a.toArray() contentEquals c.toArray()
                }
            }
        }

        "Assigning one table to another" {
            should("assign their elements correctly.") {
                forAll(Tab4Gen, Tab4Gen) { a: Table4, b: Table4 ->
                    a.set(b)
                    a.toArray() contentEquals b.toArray()
                }
            }

            should("chain correctly.") {
                forAll(Tab4Gen, Tab4Gen, Tab4Gen) { a: Table4, b: Table4, c: Table4 ->

                    a.set(b).set(c)
                    a.toArray() contentEquals c.toArray()
                }
            }
        }
    }
}
