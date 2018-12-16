package com.solsticesquared.kalahari.math

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Vector3] objects with
 * random components.
 */
class Vector3Generator : Gen<Vector3> {

    override fun generate(): Vector3 =
        Vector3(Gen.float().generate(), Gen.float().generate(),
                Gen.float().generate())
}

/**
 * Test suite for [Vector3].
 */
class Vector3Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Vector3] instances.
         */
        val Vec3Gen = Vector3Generator()
    }

    init {

        "Adding one vector to another" {
            should("add each vector's components.") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = Vector3(
                        a.x + b.x, a.y + b.y, a.z + b.z
                                          )
                    a + b == expected
                }
            }
        }

        "A vector adding itself to another" {
            should("add each of its components to the other.") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = Vector3(
                        a.x + b.x, a.y + b.y, a.z + b.z
                                          )
                    a.plusSelf(b) == expected
                }
            }
        }

        "The cross product of two vectors" {
            should("cross each component correctly.") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = Vector3(
                        a.y * b.z - a.z * b.y,
                        a.z * b.x - a.x * b.z,
                        a.x * b.y - a.y * b.x
                                          )
                    a.cross(b) == expected
                }
            }

            should("be orthogonal when the vectors are orthogonal.") {
                Vector3.X.cross(Vector3.Y) shouldBe  Vector3.Z
                Vector3.X.cross(Vector3.Z) shouldBe -Vector3.Y
                Vector3.Y.cross(Vector3.X) shouldBe -Vector3.Z
                Vector3.Y.cross(Vector3.Z) shouldBe  Vector3.X
                Vector3.Z.cross(Vector3.X) shouldBe  Vector3.Y
                Vector3.Z.cross(Vector3.Y) shouldBe -Vector3.X
            }

            should("be zero when the vectors are parallel.") {
                Vector3.X.cross(Vector3.X) shouldBe Vector3.Zero
                Vector3.Y.cross(Vector3.Y) shouldBe Vector3.Zero
                Vector3.Z.cross(Vector3.Z) shouldBe Vector3.Zero
            }
        }

        "A vector crossing itself with another" {
            should("cross each component correctly.") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = Vector3(
                        a.y * b.z - a.z * b.y,
                        a.z * b.x - a.x * b.z,
                        a.x * b.y - a.y * b.x
                                          )
                    a.crossSelf(b) == expected
                }
            }

            should("be orthogonal when the vectors are orthogonal.") {
                Vector3.X.clone().crossSelf(Vector3.Y) shouldBe  Vector3.Z
                Vector3.X.clone().crossSelf(Vector3.Z) shouldBe -Vector3.Y
                Vector3.Y.clone().crossSelf(Vector3.X) shouldBe -Vector3.Z
                Vector3.Y.clone().crossSelf(Vector3.Z) shouldBe  Vector3.X
                Vector3.Z.clone().crossSelf(Vector3.X) shouldBe  Vector3.Y
                Vector3.Z.clone().crossSelf(Vector3.Y) shouldBe -Vector3.X
            }

            should("be zero when the vectors are parallel.") {
                Vector3.X.clone().crossSelf(Vector3.X) shouldBe Vector3.Zero
                Vector3.Y.clone().crossSelf(Vector3.Y) shouldBe Vector3.Zero
                Vector3.Z.clone().crossSelf(Vector3.Z) shouldBe Vector3.Zero
            }
        }

        "The distance between two vectors" {
            should("be the root of the dot product of (a - b).") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = MathUtils.sqrt((a - b).dot(a - b))
                    a.distanceTo(b) == expected
                }
            }
        }

        "The squared distance between two vectors" {
            should("be the dot product of (a - b).") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = (a - b).dot(a - b)
                    a.distanceSquaredTo(b) == expected
                }
            }
        }

        "A vector divided by a scalar" {
            should("divide all components by that scalar.") {
                forAll(Vec3Gen, Gen.float()) { vec: Vector3, f: Float ->
                    val ff = when(f == 0f) { false -> f; true -> 1f }
                    val inv = 1f / ff
                    val expected = Vector3(
                        vec.x * inv, vec.y * inv, vec.z * inv
                                          )

                    vec / ff == expected
                }
            }
        }

        "A vector dividing itself by a scalar" {
            should("divide each of its components by that scalar.") {
                forAll(Vec3Gen, Gen.float()) { vec: Vector3, f: Float ->
                    val ff = when(f == 0f) { false -> f; true -> 1f }
                    val inv = 1f / ff
                    val expected = Vector3(
                        vec.x * inv, vec.y * inv, vec.z * inv
                                          )

                    vec.divSelf(ff) == expected
                }
            }
        }

        "The dot product of a vector and three components" {
            should("be the sum of the multiplication of each component.") {
                forAll(Vec3Gen, Gen.float(), Gen.float(), Gen.float()) {
                    vec: Vector3, x: Float, y: Float, z: Float ->

                    val expected = vec.x * x + vec.y * y + vec.z * z
                    vec.dot(x, y, z) == expected
                }
            }
        }

        "The dot product of two vectors" {
            should("be the sum of the multiplication of their compoentns.") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = a.x * b.x + a.y * b.y + a.z * b.z
                    a.dot(b) == expected
                }
            }
        }

        "The inverse of a vector" {
            should("invert all components.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    if(vec.x == 0f) vec.x = 1f
                    if(vec.y == 0f) vec.y = 1f
                    if(vec.z == 0f) vec.z = 1f

                    val expected = Vector3(
                        1f / vec.x, 1f / vec.y, 1f / vec.z
                                          )
                    vec.invert() == expected
                }
            }
        }

        "A vector inverting itself" {
            should("invert each of its components.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    if(vec.x == 0f) vec.x = 1f
                    if(vec.y == 0f) vec.y = 1f
                    if(vec.z == 0f) vec.z = 1f

                    val expected = Vector3(
                        1f / vec.x, 1f / vec.y, 1f / vec.z
                                          )
                    vec.invertSelf() == expected
                }
            }
        }

        "Subtracting one vector to another" {
            should("subtract each vector's components.") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = Vector3(
                        a.x - b.x, a.y - b.y, a.z - b.z
                                          )
                    a - b == expected
                }
            }
        }

        "A vector subtracting itself to another" {
            should("subtract each of its components to the other.") {
                forAll(Vec3Gen, Vec3Gen) { a: Vector3, b: Vector3 ->
                    val expected = Vector3(
                        a.x - b.x, a.y - b.y, a.z - b.z
                                          )
                    a.minusSelf(b) == expected
                }
            }
        }

        "Negating a vector" {
            should("negate each of its components.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    val expected = Vector3(-vec.x, -vec.y, -vec.z)
                    -vec == expected
                }
            }
        }

        "A vector negating itself" {
            should("negate each of its components.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    val expected = Vector3(-vec.x, -vec.y, -vec.z)
                    vec.negateSelf() == expected
                }
            }
        }

        "Normalizing a vector" {
            should("divide each component by the magnitude.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    val inv = when(vec.magnitude != 0f) {
                        false -> 1f
                        true  -> 1f / vec.magnitude
                    }
                    val expected = Vector3(
                        vec.x * inv, vec.y * inv, vec.z * inv
                                          )

                    vec.normalize() == expected
                }
            }
        }

        "A vector normalizing itself" {
            should("divide each component by the magnitude.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    val inv = when(vec.magnitude != 0f) {
                        false -> 1f
                        true  -> 1f / vec.magnitude
                    }
                    val expected = Vector3(
                        vec.x * inv, vec.y * inv, vec.z * inv
                                          )

                    vec.normalizeSelf() == expected
                }
            }
        }

        "A vector multiplied by a scalar" {
            should("divide all components by that scalar.") {
                forAll(Vec3Gen, Gen.float()) { vec: Vector3, f: Float ->
                    val expected = Vector3(
                        vec.x * f, vec.y * f, vec.z * f
                                          )

                    vec * f == expected
                }
            }
        }

        "A vector multiplying itself by a scalar" {
            should("divide each of its components by that scalar.") {
                forAll(Vec3Gen, Gen.float()) { vec: Vector3, f: Float ->
                    val expected = Vector3(
                        vec.x * f, vec.y * f, vec.z * f
                                          )

                    vec.timesSelf(f) == expected
                }
            }
        }

        "The magntiude of a vector" {
            should("be the root of the dot product of that vector with " +
                   "itself.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    val expected = MathUtils.sqrt(vec.dot(vec))
                    vec.magnitude == expected
                }
            }
        }

        "The squared magnitude of a vector" {
            should("be the dot product of that vector with itself.") {
                forAll(Vec3Gen) { vec: Vector3 ->
                    val expected = vec.dot(vec)
                    vec.magnitudeSquared == expected
                }
            }
        }
    }
}
