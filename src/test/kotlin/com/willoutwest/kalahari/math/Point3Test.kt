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

class Point3Test : ShouldSpec() {

    init {

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

        "Subtracting a point from another" {
            should("subtract each point's components.") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    (a - b).shouldBe(a.x - b.x, a.y - b.y, a.z - b.z)
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

        "Adding a point to another" {
            should("add each point's components.") {
                assertAll(Gen.point3(), Gen.point3()) {
                    a: Point3, b: Point3 ->

                    (a + b).shouldBe(a.x + b.x, a.y + b.y, a.z + b.z)
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
    }
}