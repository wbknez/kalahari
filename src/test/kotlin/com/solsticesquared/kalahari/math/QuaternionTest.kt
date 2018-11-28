package com.solsticesquared.kalahari.math

import io.kotlintest.matchers.lt
import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Quaternion] objects with
 * random components.
 */
class QuaternionGenerator : Gen<Quaternion> {

    override fun generate(): Quaternion =
        Quaternion(Gen.float().generate(), Gen.float().generate(),
                   Gen.float().generate(), Gen.float().generate())
}

/**
 * Test suite for [Quaternion].
 */
class QuaternionTest : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Quaternion] instances.
         */
        val QuatGen = QuaternionGenerator()

        /**
         * The utility to create randomized [Vector3] instances.
         */
        val Vec3Gen = Vector3Generator()
    }

    init {

        "One quaternion added to another" {
            should("add each of their components.") {
                forAll(QuatGen, QuatGen) { a: Quaternion, b: Quaternion ->
                    val expected = Quaternion(
                        a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w
                                             )
                    (a + b) == expected
                }
            }
        }

        "A quaternion adding itself to another" {
            should("add each of their components.") {
                forAll(QuatGen, QuatGen) { a: Quaternion, b: Quaternion ->
                    val expected = Quaternion(
                        a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w
                                             )
                    a.plusSelf(b) == expected
                }
            }
        }

        "The conjugate of a quaternion" {
            should("be the negation of its (x, y, z) components.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val expected = Quaternion(-quat.x, -quat.y, -quat.z, quat.w)
                    quat.conjugate() == expected
                }
            }
        }

        "A quaternion conjugating itself" {
            should("be the negation of its (x, y, z) components.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val expected = Quaternion(-quat.x, -quat.y, -quat.z, quat.w)
                    quat.conjugateSelf() == expected
                }
            }
        }

        "One quaternion divided by a scalar" {
            should("divide each of its components.") {
                forAll(QuatGen, Gen.float()) { quat: Quaternion, f: Float ->
                    val g = 1f / f
                    val expected = Quaternion(
                        quat.x * g, quat.y * g, quat.z * g, quat.w * g
                                             )
                    (quat / f) == expected
                }
            }
        }

        "A quaternion multiplying itself by a scalar" {
            should("multiply each of its components.") {
                forAll(QuatGen, Gen.float()) { quat: Quaternion, f: Float ->
                    val g = 1f / f
                    val expected = Quaternion(
                        quat.x * g, quat.y * g, quat.z * g, quat.w * g
                                             )
                    quat.divSelf(f) == expected
                }
            }
        }

        "The inverse of a quaternion" {
            should("divide each component by the magnitude squared.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val invMagSq = MathUtils.safeInverse(quat.magnitudeSquared)
                    val expected = Quaternion(
                        -quat.x * invMagSq, -quat.y * invMagSq,
                        -quat.z * invMagSq, quat.w * invMagSq
                                             )

                    quat.invert() == expected
                }
            }
        }

        "A quaternion inverting itself" {
            should("divide each of its component by the magnitude squared.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val invMagSq = MathUtils.safeInverse(quat.magnitudeSquared)
                    val expected = Quaternion(
                        -quat.x * invMagSq, -quat.y * invMagSq,
                        -quat.z * invMagSq, quat.w * invMagSq
                                             )

                    quat.invertSelf() == expected
                }
            }
        }

        "The normalization of a quaternion" {
            should("divide each component by the magnitude.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val expected = Quaternion(quat)
                    val mag = quat.magnitude

                    if(mag != 0f) {
                        expected.timesSelf(1f / mag)
                    }

                    quat.normalize() == expected
                }
            }
        }

        "A quaternion normalizing itself" {
            should("divide each of its components by the magnitude.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val expected = Quaternion(quat)
                    val mag = quat.magnitude

                    if(mag != 0f) {
                        expected.timesSelf(1f / mag)
                    }

                    quat.normalizeSelf() == expected
                }
            }
        }

        "One quaternion multiplied by a scalar" {
            should("multiply each of its components.") {
                forAll(QuatGen, Gen.float()) { quat: Quaternion, f: Float ->
                    val expected = Quaternion(
                        quat.x * f, quat.y * f, quat.z * f, quat.w * f
                                             )
                    (quat * f) == expected
                }
            }
        }

        "One quaternion multiplied by another" {
            should("perform component-wise multiplication.") {
                forAll(QuatGen, QuatGen) { a: Quaternion, b: Quaternion ->
                    val expected = Quaternion(
                        a.w * b.x + b.w * a.x + a.y * b.z - a.z * b.y,
                        a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x,
                        a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w,
                        a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z
                                             )
                    (a * b) == expected
                }
            }
        }

        "A quaternion multiplying itself by a scalar" {
            should("multiply each of its components.") {
                forAll(QuatGen, Gen.float()) { quat: Quaternion, f: Float ->
                    val expected = Quaternion(
                        quat.x * f, quat.y * f, quat.z * f, quat.w * f
                                             )
                    quat.timesSelf(f) == expected
                }
            }
        }

        "A quaternion multiplying itself by another" {
            should("perform component-wise multiplication.") {
                forAll(QuatGen, QuatGen) { a: Quaternion, b: Quaternion ->
                    val expected = Quaternion(
                        a.w * b.x + b.w * a.x + a.y * b.z - a.z * b.y,
                        a.w * b.y - a.x * b.z + a.y * b.w + a.z * b.x,
                        a.w * b.z + a.x * b.y - a.y * b.x + a.z * b.w,
                        a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z
                                             )
                    a.timesSelf(b) == expected
                }
            }
        }

        "Converting an angle around an axis to a quaternion" {
            should("multiply each component by the sine of the half angle.") {
                forAll(Vec3Gen, Gen.float()) { vec: Vector3, f: Float ->
                    val angle = f * MathUtils.TwoPi
                    val halfAngle = angle / 2f
                    val sin = MathUtils.sin(halfAngle)
                    val expected = Quaternion(
                        vec.x * sin,
                        vec.y * sin,
                        vec.z * sin,
                        MathUtils.cos(halfAngle)
                                             )
                    val result = Quaternion()

                    result.setFromAxis(angle, vec) == expected
                }
            }

            should("match the following example.") {
                val expected = Quaternion(0.7071f, 0f, 0f, 0.7071f)
                val result = Quaternion()

                result.setFromAxis(MathUtils.toRadians(90f), Vector3.X)

                MathUtils.abs(result.x - expected.x) shouldBe lt(0.0001f)
                MathUtils.abs(result.y - expected.y) shouldBe lt(0.0001f)
                MathUtils.abs(result.z - expected.z) shouldBe lt(0.0001f)
            }
        }

        "Convert Euler angles to a quaternion" {
            should("convert pitch, yaw, and roll correctly.") {
                forAll(Gen.float(), Gen.float(), Gen.float()) { a: Float, b: Float, c: Float ->

                    val pitch = a * MathUtils.TwoPi
                    val roll  = b * MathUtils.TwoPi
                    val yaw   = c * MathUtils.TwoPi

                    val cosP = MathUtils.cos(pitch * 0.5f)
                    val sinP = MathUtils.sin(pitch * 0.5f)
                    val cosR = MathUtils.cos(roll * 0.5f)
                    val sinR = MathUtils.sin(roll * 0.5f)
                    val cosY = MathUtils.cos(yaw * 0.5f)
                    val sinY = MathUtils.sin(yaw * 0.5f)

                    val expected = Quaternion(
                        cosY * sinR * cosP - sinY * cosR * sinP,
                        cosY * cosR * sinP + sinY * sinR * cosP,
                        sinY * cosR * cosP - cosY * sinR * sinP,
                        cosY * cosR * cosP + sinY * sinR * sinP
                                             )
                    val result = Quaternion()

                    result.setFromEuler(pitch, roll, yaw) == expected
                }
            }

            should("match the following example.") {
                val angle = MathUtils.toRadians(90f)
                val quat = Quaternion()
                val expected = Quaternion(0.7071f, 0f, 0f, 0.7071f)
                val result = quat.setFromEuler(0f, angle, 0f)

                MathUtils.abs(result.x - expected.x) shouldBe lt(0.0001f)
                MathUtils.abs(result.y - expected.y) shouldBe lt(0.0001f)
                MathUtils.abs(result.z - expected.z) shouldBe lt(0.0001f)
            }
        }

        "One quaternion subtracted from another" {
            should("subtract each of their components.") {
                forAll(QuatGen, QuatGen) { a: Quaternion, b: Quaternion ->
                    val expected = Quaternion(
                        a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w
                                             )
                    (a - b) == expected
                }
            }
        }

        "A quaternion subtracting itself from another" {
            should("subtract each of their components.") {
                forAll(QuatGen, QuatGen) { a: Quaternion, b: Quaternion ->
                    val expected = Quaternion(
                        a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w
                                             )
                    a.minusSelf(b) == expected
                }
            }
        }

        "The magnitude of a quaternion" {
            should("be the root of the dot product with itself.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val expected = MathUtils.sqrt(
                        quat.x * quat.x + quat.y * quat.y +
                        quat.z * quat.z + quat.w * quat.w
                                                 )
                    quat.magnitude == expected
                }
            }
        }

        "The squared magnitude of a quaternion" {
            should("be the dot product with itself.") {
                forAll(QuatGen) { quat: Quaternion ->
                    val expected = quat.x * quat.x + quat.y * quat.y +
                                   quat.z * quat.z + quat.w * quat.w
                    quat.magnitudeSquared == expected
                }
            }
        }
    }
}
