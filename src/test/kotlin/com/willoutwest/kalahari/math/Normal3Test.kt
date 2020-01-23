package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class Normal3Generator : Gen<Normal3> {

    override fun constants(): Iterable<Normal3> = emptyList()

    override fun random(): Sequence<Normal3> = generateSequence {
        Normal3(Gen.smallFloats().random().first(),
                Gen.smallFloats().random().first(),
                Gen.smallFloats().random().first())
    }
}

fun Gen.Companion.normal3(): Gen<Normal3> = Normal3Generator()

private fun equalsTo(normal: Normal3) = object : Matcher<Normal3> {

    override fun test(value: Normal3): MatcherResult =
        MatcherResult(value == normal,
                      "Normal $value should be $normal",
                      "Normal $value should not be $normal")
}

fun Normal3.shouldBe(other: Normal3) =
    this shouldBe equalsTo(other)

fun Normal3.shouldBe(x: Float, y: Float, z: Float) =
    this shouldBe equalsTo(Normal3(x, y, z))

/**
 * Test suite for [Normal3].
 */
class Normal3Test : ShouldSpec() {
    
    init {

        "Dividing a normal by a scalar" {
            should("divide each component by that scalar.") {
                assertAll(Gen.normal3(), Gen.nonzeroFloats()) {
                    normal: Normal3, scalar: Float ->

                    val inv = 1f / scalar

                    (normal / scalar).shouldBe(normal.x * inv,
                                               normal.y * inv,
                                               normal.z * inv)
                }
            }
        }

        "Dividing a normal by a scalar in place" {
            should("divide each of its components by that scalar.") {
                assertAll(Gen.normal3(), Gen.nonzeroFloats()) {
                    normal: Normal3, scalar: Float ->

                    val inv = 1f / scalar

                    normal.clone().divSelf(scalar).shouldBe(normal.x * inv,
                                                            normal.y * inv,
                                                            normal.z * inv)
                }
            }
        }

        "Doting a normal with three components" {
            should("sum the product of each component.") {
                assertAll(Gen.normal3(), Gen.float(), Gen.float(),
                          Gen.float()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.dot(x, y, z).shouldBe(
                        normal.x * x + normal.y * y + normal.z * z)
                }
            }
        }

        "Dotting a normal with another" {
            should("sum the product of their components.") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    a.dot(b).shouldBe(a.x * b.x + a.y * b.y + a.z * b.z)
                }
            }
        }

        "Subtracting a normal from another" {
            should("subtract each normal's components.") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    (a - b).shouldBe(a.x - b.x, a.y - b.y, a.z - b.z)
                }
            }
        }

        "Subtracting a normal from another in place" {
            should("subtract each of its components.") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    a.clone().minusSelf(b).shouldBe(a.x - b.x,
                                                    a.y - b.y,
                                                    a.z - b.z)
                }
            }
        }

        "Negating a normal" {
            should("negate each component.") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    (-normal).shouldBe(-normal.x, -normal.y, -normal.z)
                }
            }
        }

        "Negating a normal in place" {
            should("negate each of its components.") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.clone().negateSelf()
                        .shouldBe(-normal.x, -normal.y, -normal.z)
                }
            }
        }

        "Normalizing a normal" {
            should("divide each component by the magnitude.") {
                assertAll(Gen.normal3()) { normal: Normal3 ->

                    when(normal.magnitude != 0f) {
                        false -> normal.normalize()
                            .shouldBe(normal.x, normal.y, normal.z)
                        true  -> normal.normalize()
                            .shouldBe(normal / normal.magnitude)
                    }
                }
            }
        }

        "Normalizing a normal in place" {
            should("divide each component by the magnitude.") {
                assertAll(Gen.normal3()) { normal: Normal3 ->

                    when(normal.magnitude != 0f) {
                        false -> normal.clone().normalizeSelf()
                            .shouldBe(normal.x, normal.y, normal.z)
                        true  -> normal.clone().normalizeSelf()
                            .shouldBe(normal / normal.magnitude)
                    }
                }
            }
        }

        "Adding a normal to another" {
            should("add each normal's components.") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    (a + b).shouldBe(a.x + b.x, a.y + b.y, a.z + b.z)
                }
            }
        }

        "Adding a normal to another in place" {
            should("add each of its components.") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    a.clone().plusSelf(b).shouldBe(a.x + b.x,
                                                   a.y + b.y,
                                                   a.z + b.z)
                }
            }
        }

        "Multiplying a normal by a scalar" {
            should("multiply each component by that scalar") {
                assertAll(Gen.normal3(), Gen.numericFloats()) {
                    normal: Normal3, scalar: Float ->

                    (normal * scalar).shouldBe(normal.x * scalar,
                                            normal.y * scalar,
                                            normal.z * scalar)
                }
            }
        }

        "Multiplying a normal by a scalar in place" {
            should("multiply each of its components by that scalar") {
                assertAll(Gen.normal3(), Gen.numericFloats()) {
                    normal: Normal3, scalar: Float ->

                    normal.clone().timesSelf(scalar).shouldBe(normal.x * scalar,
                                                           normal.y * scalar,
                                                           normal.z * scalar)
                }
            }
        }
    }
}