package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Point2] objects with
 * random components.
 */
class Point2Generator : Gen<Point2> {

    override fun generate(): Point2 =
        Point2(Gen.float().generate(), Gen.float().generate())
}

/**
 * Test suite for [Point2].
 */
class Point2Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Point2] instances.
         */
        val Poi2Gen = Point2Generator()
    }

    init {

        "The distance between two points" {
            should("be the magnitude of the difference.") {
                forAll(Poi2Gen, Poi2Gen) { a: Point2, b: Point2 ->
                    val dX = a.x - b.x
                    val dY = a.y - b.y
                    val expected = MathUtils.sqrt(dX * dX + dY * dY)
                    a.distanceTo(b) == expected
                }
            }
        }

        "The squared distance between two points" {
            should("be the magnitude of the difference squared.") {
                forAll(Poi2Gen, Poi2Gen) { a: Point2, b: Point2 ->
                    val dX = a.x - b.x
                    val dY = a.y - b.y
                    val expected = dX * dX + dY * dY
                    a.distanceSquaredTo(b) == expected
                }
            }
        }

        "One point subtracted from another" {
            should("subtract each of their components.") {
                forAll(Poi2Gen, Poi2Gen) { a: Point2, b: Point2 ->
                    val expected = Point2(
                        a.x - b.x, a.y - b.y
                                         )
                    (a - b) == expected
                }
            }
        }

        "A point subtracting itself from another" {
            should("subtract each of its components.") {
                forAll(Poi2Gen, Poi2Gen) { a: Point2, b: Point2 ->
                    val expected = Point2(
                        a.x - b.x, a.y - b.y
                                         )
                    a.minusSelf(b) == expected
                }
            }
        }

        "The negation of a point" {
            should("negate each component in turn.") {
                forAll(Poi2Gen) { point: Point2 ->
                    val expected = Point2(-point.x, -point.y)
                    -point == expected
                }
            }
        }

        "A point negating itself" {
            should("negate each of its components as well.") {
                forAll(Poi2Gen) { point: Point2 ->
                    val expected = Point2(-point.x, -point.y)
                    point.negateSelf() == expected
                }
            }
        }

        "One point added to another" {
            should("add each of their components.") {
                forAll(Poi2Gen, Poi2Gen) { a: Point2, b: Point2 ->
                    val expected = Point2(
                        a.x + b.x, a.y + b.y
                                         )
                    (a + b) == expected
                }
            }
        }

        "A point adding itself to another" {
            should("add each of its components.") {
                forAll(Poi2Gen, Poi2Gen) { a: Point2, b: Point2 ->
                    val expected = Point2(
                        a.x + b.x, a.y + b.y
                                         )
                    a.plusSelf(b) == expected
                }
            }
        }

        "One point multiplied by a scalar" {
            should("multiply each component in turn.") {
                forAll(Poi2Gen, Gen.float()) { point: Point2, f: Float ->
                    val expected = Point2(
                        point.x * f, point.y * f
                                         )
                    (point * f) == expected
                }
            }
        }

        "A point multiplying itself by a scalar" {
            should("multiply each of its components as well.") {
                forAll(Poi2Gen, Gen.float()) { point: Point2, f: Float ->
                    val expected = Point2(
                        point.x * f, point.y * f
                                         )
                    point.timesSelf(f) == expected
                }
            }
        }

        "One point divided by a scalar" {
            should("divide each component in turn.") {
                forAll(Poi2Gen, Gen.float()) { point: Point2, f: Float ->
                    val g = 1f / f
                    val expected = Point2(
                        point.x * g, point.y * g
                                         )
                    (point / f) == expected
                }
            }
        }

        "A point multiplying itself by a scalar" {
            should("multiply each of its components as well.") {
                forAll(Poi2Gen, Gen.float()) { point: Point2, f: Float ->
                    val g = 1f / f
                    val expected = Point2(
                        point.x * g, point.y * g
                                         )
                    point.divSelf(f) == expected
                }
            }
        }
    }
}
