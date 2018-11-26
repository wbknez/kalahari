package com.solsticesquared.kalahari.math

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Point3] objects with
 * random components.
 */
class Point3Generator : Gen<Point3> {

    override fun generate(): Point3 =
        Point3(Gen.float().generate(), Gen.float().generate(),
               Gen.float().generate())
}

/**
 * Test suite for [Point3].
 */
class Point3Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Point3] instances.
         */
        val Poi3Gen = Point3Generator()
    }

    init {

        "The distance between two points" {
            should("be the magnitude of the difference.") {
                forAll(Poi3Gen, Poi3Gen) { a: Point3, b: Point3 ->
                    val dX = a.x - b.x
                    val dY = a.y - b.y
                    val dZ = a.z - b.z
                    val expected = MathUtils.sqrt(dX * dX + dY * dY + dZ * dZ)
                    a.distanceTo(b) == expected
                }
            }
        }

        "The squared distance between two points" {
            should("be the magnitude of the difference squared.") {
                forAll(Poi3Gen, Poi3Gen) { a: Point3, b: Point3 ->
                    val dX = a.x - b.x
                    val dY = a.y - b.y
                    val dZ = a.z - b.z
                    val expected = dX * dX + dY * dY + dZ * dZ
                    a.distanceSquaredTo(b) == expected
                }
            }
        }

        "One point divided by a scalar" {
            should("divide each component in turn.") {
                forAll(Poi3Gen, Gen.float()) { point: Point3, f: Float ->
                    val g = 1f / f
                    val expected = Point3(
                        point.x * g, point.y * g, point.z * g
                    )
                    (point / f) == expected
                }
            }
        }

        "A point dividing itself by a scalar" {
            should("divide each of its components as well.") {
                forAll(Poi3Gen, Gen.float()) { point: Point3, f: Float ->
                    val g = 1f / f
                    val expected = Point3(
                        point.x * g, point.y * g, point.z * g
                    )
                    point.divSelf(f) == expected
                }
            }
        }

        "One point subtracted from another" {
            should("subtract each of their components.") {
                forAll(Poi3Gen, Poi3Gen) { a: Point3, b: Point3 ->
                    val expected = Point3(
                        a.x - b.x, a.y - b.y, a.z - b.z
                    )
                    (a - b) == expected
                }
            }
        }

        "A point subtracting itself from another" {
            should("subtract each of its components.") {
                forAll(Poi3Gen, Poi3Gen) { a: Point3, b: Point3 ->
                    val expected = Point3(
                        a.x - b.x, a.y - b.y, a.z - b.z
                    )
                    a.minusSelf(b) == expected
                }
            }
        }

        "The negation of a point" {
            should("negate each component in turn.") {
                forAll(Poi3Gen) { point: Point3 ->
                    val expected = Point3(-point.x, -point.y, -point.z)
                    -point == expected
                }
            }
        }

        "A point negating itself" {
            should("negate each of its components as well.") {
                forAll(Poi3Gen) { point: Point3 ->
                    val expected = Point3(-point.x, -point.y, -point.z)
                    point.negateSelf() == expected
                }
            }
        }

        "One point added to another" {
            should("add each of their components.") {
                forAll(Poi3Gen, Poi3Gen) { a: Point3, b: Point3 ->
                    val expected = Point3(
                        a.x + b.x, a.y + b.y, a.z + b.z
                    )
                    (a + b) == expected
                }
            }
        }

        "A point adding itself to another" {
            should("add each of its components.") {
                forAll(Poi3Gen, Poi3Gen) { a: Point3, b: Point3 ->
                    val expected = Point3(
                        a.x + b.x, a.y + b.y, a.z + b.z
                    )
                    a.plusSelf(b) == expected
                }
            }
        }

        "One point multiplied by a scalar" {
            should("multiply each component in turn.") {
                forAll(Poi3Gen, Gen.float()) { point: Point3, f: Float ->
                    val expected = Point3(
                        point.x * f, point.y * f, point.z * f
                    )
                    (point * f) == expected
                }
            }
        }

        "A point multiplying itself by a scalar" {
            should("multiply each of its components as well.") {
                forAll(Poi3Gen, Gen.float()) { point: Point3, f: Float ->
                    val expected = Point3(
                        point.x * f, point.y * f, point.z * f
                    )
                    point.timesSelf(f) == expected
                }
            }
        }
    }
}
