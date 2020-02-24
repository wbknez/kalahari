package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import kotlin.math.abs

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

private fun equalsTo(normal: Normal3, tol: Float) = object : Matcher<Normal3> {

    override fun test(value: Normal3): MatcherResult =
        MatcherResult(value.toArray().zip(normal.toArray()).all {
            abs(it.first - it.second) <= tol
        },
                      "Normal $value should be $normal",
                      "Normal $value should not be $normal")
}

fun Normal3.shouldBe(x: Float, y: Float, z: Float) =
    this shouldBe equalsTo(Normal3(x, y, z))

fun Normal3.shouldBe(x: Float, y: Float, z: Float, tol: Float) =
    this shouldBe equalsTo(Normal3(x, y, z), tol)

fun Normal3.shouldBe(other: Normal3) =
    this shouldBe equalsTo(other)

fun Normal3.shouldBe(other: Normal3, tol: Float) =
    this shouldBe equalsTo(other, tol)

/**
 * Test suite for [Normal3].
 */
class Normal3Test : ShouldSpec() {

    init {

        "Computing the distance between a normal and three components" {
            should("be the root of the iterative product of all components") {
                assertAll(Gen.normal3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.distanceTo(x, y, z).shouldBe(MathUtils.sqrt(
                        MathUtils.pow(normal.x - x, 2f) +
                        MathUtils.pow(normal.y - y, 2f) +
                        MathUtils.pow(normal.z - z, 2f)
                    ))
                }
            }
        }

        "Computing the distance between two normals" {
            should("be the root of the dot product of (a - b)") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    a.distanceTo(b).shouldBe(
                        MathUtils.sqrt((a - b).dot(a - b)))
                }
            }
        }

        "Computing the squared distance between a normal and three components" {
            should("be the iterative product of all components") {
                assertAll(Gen.normal3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.distanceSquaredTo(x, y, z).shouldBe(
                        MathUtils.pow(normal.x - x, 2f) +
                        MathUtils.pow(normal.y - y, 2f) +
                        MathUtils.pow(normal.z - z, 2f)
                    )
                }
            }
        }

        "Computing the squared distance between two normals" {
            should("be the dot product of (a - b)") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    a.distanceSquaredTo(b).shouldBe((a - b).dot(a - b))
                }
            }
        }

        "Dividing a normal by a scalar" {
            should("divide each component by that scalar") {
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
            should("divide each of its components by that scalar") {
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
            should("sum the product of each component") {
                assertAll(Gen.normal3(), Gen.float(), Gen.float(),
                          Gen.float()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.dot(x, y, z).shouldBe(
                        normal.x * x + normal.y * y + normal.z * z)
                }
            }
        }

        "Dotting a normal with another" {
            should("sum the product of their components") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    a.dot(b).shouldBe(a.x * b.x + a.y * b.y + a.z * b.z)
                }
            }
        }

        "Subtracting a normal from components" {
            should("subtract all components from each other") {
                assertAll(Gen.normal3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.minus(x, y, z)
                        .shouldBe(normal.x - x, normal.y - y, normal.z - z)
                }
            }
        }

        "Subtracting a normal from another" {
            should("subtract each normal's components") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    (a - b).shouldBe(a.x - b.x, a.y - b.y, a.z - b.z)
                }
            }
        }

        "Subtracting a normal from components in place" {
            should("subtract its components from the other") {
                assertAll(Gen.normal3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.clone().minusSelf(x, y, z)
                        .shouldBe(normal.x - x, normal.y - y, normal.z - z)
                }
            }
        }

        "Subtracting a normal from another in place" {
            should("subtract each of its components") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    a.clone().minusSelf(b).shouldBe(a.x - b.x,
                                                    a.y - b.y,
                                                    a.z - b.z)
                }
            }
        }

        "Negating a normal" {
            should("negate each component") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    (-normal).shouldBe(-normal.x, -normal.y, -normal.z)
                }
            }
        }

        "Negating a normal in place" {
            should("negate each of its components") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.clone().negateSelf().shouldBe(-normal.x, -normal.y, -normal.z)
                }
            }
        }

        "Normalizing a normal" {
            should("divide each component by the magnitude") {
                assertAll(Gen.normal3()) { normal: Normal3 ->

                    when(normal.magnitude != 0f) {
                        false -> normal.normalize().shouldBe(normal.x, normal.y, normal.z)
                        true  -> normal.normalize().shouldBe(normal / normal.magnitude)
                    }
                }
            }
        }

        "Normalizing a normal in place" {
            should("divide each component by the magnitude") {
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

        "Adding a normal from components" {
            should("add all components to each other") {
                assertAll(Gen.normal3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.plus(x, y, z)
                        .shouldBe(normal.x + x, normal.y + y, normal.z + z)
                }
            }
        }

        "Adding a normal to another" {
            should("add each normal's components") {
                assertAll(Gen.normal3(), Gen.normal3()) {
                    a: Normal3, b: Normal3 ->

                    (a + b).shouldBe(a.x + b.x, a.y + b.y, a.z + b.z)
                }
            }
        }

        "Adding a normal from components in place" {
            should("add its components to the other") {
                assertAll(Gen.normal3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    normal: Normal3, x: Float, y: Float, z: Float ->

                    normal.clone().plusSelf(x, y, z)
                        .shouldBe(normal.x + x, normal.y + y, normal.z + z)
                }
            }
        }

        "Adding a normal to another in place" {
            should("add each of its components") {
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

        "Transforming a normal" {
            should("be the product of each component with a column") {
                assertAll(Gen.normal3(), Gen.matrix4()) {
                    normal: Normal3, mat: Matrix4 ->

                    normal.transform(mat).shouldBe(
                        normal.x * mat.t00 + normal.y * mat.t10 + normal.z *
                        mat.t20,
                        normal.x * mat.t01 + normal.y * mat.t11 + normal.z *
                        mat.t21,
                        normal.x * mat.t02 + normal.y * mat.t12 + normal.z *
                        mat.t22
                    )
                }
            }

            should("produce itself when using the identity matrix") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.transform(Matrix4.Identity).shouldBe(normal)
                }
            }

            should("produce zero when using the zero matrix") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.transform(Matrix4.Zero).shouldBe(Vector3.Zero)
                }
            }
        }

        "Transforming a normal in place" {
            should("be the product of its component with a row") {
                assertAll(Gen.normal3(), Gen.matrix4()) {
                    normal: Normal3, mat: Matrix4 ->

                    normal.clone().transformSelf(mat).shouldBe(
                        normal.x * mat.t00 + normal.y * mat.t10 + normal.z *
                        mat.t20,
                        normal.x * mat.t01 + normal.y * mat.t11 + normal.z *
                        mat.t21,
                        normal.x * mat.t02 + normal.y * mat.t12 + normal.z *
                        mat.t22
                    )
                }
            }

            should("produce itself when using the identity matrix") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.clone().transformSelf(Matrix4.Identity).shouldBe(normal)
                }
            }

            should("produce zero when using the zero matrix") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.clone().transformSelf(Matrix4.Zero)
                        .shouldBe(Vector3.Zero)
                }
            }
        }

        "Taking the magnitude of a normal" {
            should("be the root of the product of its components") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.magnitude.shouldBe(MathUtils.sqrt(normal.dot(normal)))
                }
            }
        }

        "Taking the squared magnitude of a normal" {
            should("be the product of its components") {
                assertAll(Gen.normal3()) { normal: Normal3 ->
                    normal.magnitudeSquared.shouldBe(normal.dot(normal))
                }
            }
        }
    }
}