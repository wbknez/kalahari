package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class Point3Generator : Gen<Point3> {

    override fun constants(): Iterable<Point3> = emptyList()

    override fun random(): Sequence<Point3> = generateSequence {
        Point3(Gen.smallFloats().random().first(),
                Gen.smallFloats().random().first(),
                Gen.smallFloats().random().first())
    }
}

fun Gen.Companion.point3(): Gen<Point3> = Point3Generator()

private fun equalsTo(Point: Point3) = object : Matcher<Point3> {

    override fun test(value: Point3): MatcherResult =
        MatcherResult(value == Point,
                      "Point $value should be $Point",
                      "Point $value should not be $Point")
}

fun Point3.shouldBe(other: Point3) =
    this shouldBe equalsTo(other)

fun Point3.shouldBe(x: Float, y: Float, z: Float) =
    this shouldBe equalsTo(Point3(x, y, z))

/**
 * Test suite for [Point3].
 */
class Point3Test : ShouldSpec() {

    init {

        "Computing the distance between a point and three components" {
            should("be the root of the iterative product of all components") {
                assertAll(Gen.point3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    point: Point3, x: Float, y: Float, z: Float ->

                    point.distanceTo(x, y, z).shouldBe(MathUtils.sqrt(
                        MathUtils.pow(point.x - x, 2f) +
                        MathUtils.pow(point.y - y, 2f) +
                        MathUtils.pow(point.z - z, 2f)
                    ))
                }
            }
        }

        "Computing the distance between two points" {
            should("be the root of the dot product of (a - b).") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    a.distanceTo(b).shouldBe(MathUtils.sqrt(
                        MathUtils.pow(a.x - b.x, 2f) +
                        MathUtils.pow(a.y - b.y, 2f) +
                        MathUtils.pow(a.z - b.z, 2f)))
                }
            }
        }

        "Computing the squared distance between a point and three components" {
            should("be the iterative product of all components") {
                assertAll(Gen.point3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    point: Point3, x: Float, y: Float, z: Float ->

                    point.distanceSquaredTo(x, y, z).shouldBe(
                        MathUtils.pow(point.x - x, 2f) +
                        MathUtils.pow(point.y - y, 2f) +
                        MathUtils.pow(point.z - z, 2f)
                    )
                }
            }
        }

        "Computing the squared distance between two points" {
            should("be the dot product of (a - b).") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    a.distanceSquaredTo(b).shouldBe(
                        MathUtils.pow(a.x - b.x, 2f) +
                        MathUtils.pow(a.y - b.y, 2f) +
                        MathUtils.pow(a.z - b.z, 2f))
                }
            }
        }

        "Dividing a point by a scalar" {
            should("divide each component by that scalar.") {
                assertAll(Gen.point3(), Gen.nonzeroFloats()) {
                    point: Point3, scalar: Float ->

                    val inv = 1f / scalar

                    (point / scalar).shouldBe(point.x * inv,
                                              point.y * inv,
                                              point.z * inv)
                }
            }
        }

        "Dividing a point by a scalar in place" {
            should("divide each of its components by that scalar.") {
                assertAll(Gen.point3(), Gen.nonzeroFloats()) {
                    point: Point3, scalar: Float ->

                    val inv = 1f / scalar

                    point.clone().divSelf(scalar).shouldBe(point.x * inv,
                                                           point.y * inv,
                                                           point.z * inv)
                }
            }
        }

        "Subtracting a point from components" {
            should("subtract all components from each other") {
                assertAll(Gen.point3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    point: Point3, x: Float, y: Float, z: Float ->

                    point.minus(x, y, z)
                        .shouldBe(point.x - x, point.y - y, point.z - z)
                }
            }
        }

        "Subtracting a point from another" {
            should("subtract each point's components.") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    (a - b).shouldBe(a.x - b.x, a.y - b.y, a.z - b.z)
                }
            }
        }

        "Subtracting a point from components in place" {
            should("subtract its components from the other") {
                assertAll(Gen.point3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    point: Point3, x: Float, y: Float, z: Float ->

                    point.clone().minusSelf(x, y, z)
                        .shouldBe(point.x - x, point.y - y, point.z - z)
                }
            }
        }

        "Subtracting a point from another in place" {
            should("subtract each of its components.") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    a.clone().minusSelf(b).shouldBe(a.x - b.x,
                                                    a.y - b.y,
                                                    a.z - b.z)
                }
            }
        }

        "Negating a point" {
            should("negate each component.") {
                assertAll(Gen.point3()) { point: Point3 ->
                    (-point).shouldBe(-point.x, -point.y, -point.z)
                }
            }
        }

        "Negating a point in place" {
            should("negate each of its components.") {
                assertAll(Gen.point3()) { point: Point3 ->
                    point.clone().negateSelf().shouldBe(
                        -point.x, -point.y, -point.z)
                }
            }
        }

        "Adding a point from components" {
            should("add all components to each other") {
                assertAll(Gen.point3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    point: Point3, x: Float, y: Float, z: Float ->

                    point.plus(x, y, z)
                        .shouldBe(point.x + x, point.y + y, point.z + z)
                }
            }
        }

        "Adding a point to another" {
            should("add each point's components.") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    (a + b).shouldBe(a.x + b.x, a.y + b.y, a.z + b.z)
                }
            }
        }

        "Adding a point from components in place" {
            should("add its components to the other") {
                assertAll(Gen.point3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    point: Point3, x: Float, y: Float, z: Float ->

                    point.clone().plusSelf(x, y, z)
                        .shouldBe(point.x + x, point.y + y, point.z + z)
                }
            }
        }

        "Adding a point to another in place" {
            should("add each of its components.") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    a.clone().plusSelf(b).shouldBe(a.x + b.x,
                                                   a.y + b.y,
                                                   a.z + b.z)
                }
            }
        }

        "Multiplying a point by a scalar" {
            should("multiply each component by that scalar") {
                assertAll(Gen.point3(), Gen.numericFloats()) {
                    vec: Point3, scalar: Float ->

                    (vec * scalar).shouldBe(vec.x * scalar,
                                            vec.y * scalar,
                                            vec.z * scalar)
                }
            }
        }

        "Multiplying a point by a scalar in place" {
            should("multiply each of its components by that scalar") {
                assertAll(Gen.point3(), Gen.numericFloats()) {
                    vec: Point3, scalar: Float ->

                    vec.clone().timesSelf(scalar).shouldBe(vec.x * scalar,
                                                           vec.y * scalar,
                                                           vec.z * scalar)
                }
            }
        }

        "Transforming a point" {
            should("be the product of each component with a row") {
                assertAll(Gen.point3(), Gen.matrix4()) {
                    point: Point3, mat: Matrix4 ->

                    point.transform(mat).shouldBe(
                        point.x * mat.t00 + point.y * mat.t01 + point.z *
                        mat.t02 + mat.t03,
                        point.x * mat.t10 + point.y * mat.t11 + point.z *
                        mat.t12 + mat.t13,
                        point.x * mat.t20 + point.y * mat.t21 + point.z *
                        mat.t22 + mat.t23
                    )
                }
            }

            should("produce itself when using the identity matrix") {
                assertAll(Gen.point3()) { point: Point3 ->
                    point.transform(Matrix4.Identity).shouldBe(point)
                }
            }

            should("produce zero when using the zero matrix") {
                assertAll(Gen.point3()) { point: Point3 ->
                    point.transform(Matrix4.Zero).shouldBe(Point3.Zero)
                }
            }
        }

        "Transforming a point in place" {
            should("be the product of its component with a row") {
                assertAll(Gen.point3(), Gen.matrix4()) {
                    point: Point3, mat: Matrix4 ->

                    point.clone().transformSelf(mat).shouldBe(
                        point.x * mat.t00 + point.y * mat.t01 + point.z *
                        mat.t02 + mat.t03,
                        point.x * mat.t10 + point.y * mat.t11 + point.z *
                        mat.t12 + mat.t13,
                        point.x * mat.t20 + point.y * mat.t21 + point.z *
                        mat.t22 + mat.t23
                    )
                }
            }

            should("produce itself when using the identity matrix") {
                assertAll(Gen.point3()) { point: Point3 ->
                    point.clone().transformSelf(Matrix4.Identity).shouldBe(point)
                }
            }

            should("produce zero when using the zero matrix") {
                assertAll(Gen.point3()) { point: Point3 ->
                    point.clone().transformSelf(Matrix4.Zero)
                        .shouldBe(Point3.Zero)
                }
            }
        }
    }
}