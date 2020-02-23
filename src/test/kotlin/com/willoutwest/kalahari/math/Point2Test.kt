package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

class Point2Generator : Gen<Point2> {

    override fun constants(): Iterable<Point2> = emptyList()

    override fun random(): Sequence<Point2> = generateSequence {
        Point2(Gen.smallFloats().random().first(),
               Gen.smallFloats().random().first())
    }
}

fun Gen.Companion.point2(): Gen<Point2> = Point2Generator()

private fun equalsTo(Point: Point2) = object : Matcher<Point2> {

    override fun test(value: Point2): MatcherResult =
        MatcherResult(value == Point,
                      "Point $value should be $Point",
                      "Point $value should not be $Point")
}

fun Point2.shouldBe(other: Point2) =
    this shouldBe equalsTo(other)

fun Point2.shouldBe(x: Float, y: Float) =
    this shouldBe equalsTo(Point2(x, y))

/**
 * Test suite for [Point2].
 */
class Point2Test : ShouldSpec() {

    init {

        "Computing the distance between two points" {
            should("be the root of the dot product of (a - b).") {
                assertAll(Gen.point2(), Gen.point2()) {
                    a: Point2, b: Point2 ->

                    a.distanceTo(b).shouldBe(MathUtils.sqrt(
                        MathUtils.pow(a.x - b.x, 2f) +
                        MathUtils.pow(a.y - b.y, 2f)))
                }
            }
        }

        "Computing the squared distance between two points" {
            should("be the dot product of (a - b).") {
                assertAll(Gen.point2(), Gen.point2()) {
                    a: Point2, b: Point2 ->

                    a.distanceSquaredTo(b).shouldBe(
                        MathUtils.pow(a.x - b.x, 2f) +
                        MathUtils.pow(a.y - b.y, 2f))
                }
            }
        }

        "Dividing a point by a scalar" {
            should("divide each component by that scalar.") {
                assertAll(Gen.point2(), Gen.nonzeroFloats()) {
                    point: Point2, scalar: Float ->

                    val inv = 1f / scalar

                    (point / scalar).shouldBe(point.x * inv,
                                              point.y * inv)
                }
            }
        }

        "Dividing a point by a scalar in place" {
            should("divide each of its components by that scalar.") {
                assertAll(Gen.point2(), Gen.nonzeroFloats()) {
                    point: Point2, scalar: Float ->

                    val inv = 1f / scalar

                    point.clone().divSelf(scalar).shouldBe(point.x * inv,
                                                           point.y * inv)
                }
            }
        }

        "Subtracting a point from components" {
            should("subtract all components from each other") {
                assertAll(Gen.point2(), Gen.smallFloats(), Gen.smallFloats()) {
                    point: Point2, x: Float, y: Float ->

                    point.minus(x, y).shouldBe(point.x - x, point.y - y)
                }
            }
        }

        "Subtracting a point from another" {
            should("subtract each point's components.") {
                assertAll(Gen.point2(), Gen.point2()) { a: Point2, b: Point2 ->

                    (a - b).shouldBe(a.x - b.x, a.y - b.y)
                }
            }
        }

        "Subtracting a point from components in place" {
            should("subtract its components from the other") {
                assertAll(Gen.point2(), Gen.smallFloats(), Gen.smallFloats()) {
                    point: Point2, x: Float, y: Float ->

                    point.clone().minusSelf(x, y)
                        .shouldBe(point.x - x, point.y - y)
                }
            }
        }

        "Subtracting a point from another in place" {
            should("subtract each of its components.") {
                assertAll(Gen.point2(), Gen.point2()) {
                    a: Point2, b: Point2 ->

                    a.clone().minusSelf(b).shouldBe(a.x - b.x,
                                                    a.y - b.y)
                }
            }
        }

        "Negating a point" {
            should("negate each component.") {
                assertAll(Gen.point2()) { point: Point2 ->
                    (-point).shouldBe(-point.x, -point.y)
                }
            }
        }

        "Negating a point in place" {
            should("negate each of its components.") {
                assertAll(Gen.point2()) { point: Point2 ->
                    point.clone().negateSelf().shouldBe(
                        -point.x, -point.y)
                }
            }
        }

        "Adding a point from components" {
            should("add all components to each other") {
                assertAll(Gen.point2(), Gen.smallFloats(), Gen.smallFloats()) {
                    point: Point2, x: Float, y: Float ->

                    point.plus(x, y).shouldBe(point.x + x, point.y + y)
                }
            }
        }

        "Adding a point to another" {
            should("add each point's components.") {
                assertAll(Gen.point2(), Gen.point2()) {
                    a: Point2, b: Point2 ->

                    (a + b).shouldBe(a.x + b.x, a.y + b.y)
                }
            }
        }

        "Adding a point from components in place" {
            should("add its components to the other") {
                assertAll(Gen.point2(), Gen.smallFloats(), Gen.smallFloats()) {
                    point: Point2, x: Float, y: Float ->

                    point.clone().plusSelf(x, y)
                        .shouldBe(point.x + x, point.y + y)
                }
            }
        }

        "Adding a point to another in place" {
            should("add each of its components.") {
                assertAll(Gen.point2(), Gen.point2()) {
                    a: Point2, b: Point2 ->

                    a.clone().plusSelf(b).shouldBe(a.x + b.x,
                                                   a.y + b.y)
                }
            }
        }

        "Multiplying a point by a scalar" {
            should("multiply each component by that scalar") {
                assertAll(Gen.point2(), Gen.numericFloats()) {
                    vec: Point2, scalar: Float ->

                    (vec * scalar).shouldBe(vec.x * scalar,
                                            vec.y * scalar)
                }
            }
        }

        "Multiplying a point by a scalar in place" {
            should("multiply each of its components by that scalar") {
                assertAll(Gen.point2(), Gen.numericFloats()) {
                    vec: Point2, scalar: Float ->

                    vec.clone().timesSelf(scalar).shouldBe(vec.x * scalar,
                                                           vec.y * scalar)
                }
            }
        }
    }
}