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
import kotlin.math.cos
import kotlin.math.sin

class QuaternionGenerator : Gen<Quaternion> {

    override fun constants(): Iterable<Quaternion> = emptyList()

    override fun random(): Sequence<Quaternion> = generateSequence {
        Quaternion(Gen.smallFloats().random().first(),
                   Gen.smallFloats().random().first(),
                   Gen.smallFloats().random().first(),
                   Gen.smallFloats().random().first())
    }
}

fun Gen.Companion.quaternion(): Gen<Quaternion> = QuaternionGenerator()

private fun equalsTo(quat: Quaternion) = object : Matcher<Quaternion> {

    override fun test(value: Quaternion): MatcherResult =
        MatcherResult(value == quat,
                  "Quaternion $value should be $quat",
                      "Quaternion $value should not be $quat")
}

private fun equalsTo(quat: Quaternion, tol: Float) =
    object : Matcher<Quaternion> {

    override fun test(value: Quaternion): MatcherResult =
        MatcherResult(abs(value.x - quat.x) <= tol &&
                      abs(value.y - quat.y) <= tol &&
                      abs(value.z - quat.z) <= tol &&
                      abs(value.w - quat.w) <= tol,
                      "Quaternion $value should be $quat",
                      "Quaternion $value should not be $quat")
}

fun Quaternion.shouldBe(x: Float, y: Float, z: Float, w: Float) =
    this shouldBe equalsTo(Quaternion(x, y, z, w))

fun Quaternion.shouldBe(x: Float, y: Float, z: Float, w: Float, tol: Float) =
    this shouldBe equalsTo(Quaternion(x, y, z, w), tol)

fun Quaternion.shouldBe(other: Quaternion) =
    this shouldBe equalsTo(other)

fun Quaternion.shouldBe(other: Quaternion, tol: Float) =
    this shouldBe equalsTo(other, tol)

/**
 * Test suite for [Quaternion].
 */
class QuaternionTest : ShouldSpec() {

    init {

        "Conjugating a quaternion" {
            should("negate the x, y, and z components only") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->
                    quat.conjugate()
                        .shouldBe(-quat.x, -quat.y, -quat.z, quat.w)
                }
            }
        }

        "Conjugating a quaternion in place" {
            should("negate its x, y, and z components only") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->
                    quat.clone().conjugateSelf()
                        .shouldBe(-quat.x, -quat.y, -quat.z, quat.w)
                }
            }
        }

        "Dividing a quaternion by a scalar" {
            should("divide each component by that scalar.") {
                assertAll(Gen.quaternion(), Gen.nonzeroFloats()) {
                    quat: Quaternion, scalar: Float ->

                    val inv = 1f / scalar

                    (quat / scalar).shouldBe(quat.x * inv,
                                             quat.y * inv,
                                             quat.z * inv,
                                             quat.w * inv)
                }
            }
        }

        "Dividing a quaternion by a scalar in place" {
            should("divide each of its components by that scalar.") {
                assertAll(Gen.quaternion(), Gen.nonzeroFloats()) {
                    quat: Quaternion, scalar: Float ->

                    val inv = 1f / scalar

                    quat.clone().divSelf(scalar).shouldBe(quat.x * inv,
                                                          quat.y * inv,
                                                          quat.z * inv,
                                                          quat.w * inv)
                }
            }
        }

        "Inverting a quaternion" {
            should("invert each component.") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->
                    val invMag = MathUtils.safeInverse(quat.magnitudeSquared)

                    quat.invert().shouldBe(quat.x * -invMag,
                                           quat.y * -invMag,
                                           quat.z * -invMag,
                                           quat.w * invMag)
                }
            }
        }

        "Inverting a quaternion in place" {
            should("invert each of its components.") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->
                    val invMag = MathUtils.safeInverse(quat.magnitudeSquared)

                    quat.clone().invertSelf().shouldBe(quat.x * -invMag,
                                                       quat.y * -invMag,
                                                       quat.z * -invMag,
                                                       quat.w * invMag)
                }
            }
        }

        "Subtracting a quaternion from another" {
            should("subtract each quaternion's components.") {
                assertAll(Gen.quaternion(), Gen.quaternion()) {
                    a: Quaternion, b: Quaternion ->

                    (a - b).shouldBe(a.x - b.x, a.y - b.y, a.z - b.z,
                                     a.w - b.w)
                }
            }
        }

        "Subtracting a quaternion from another in place" {
            should("subtract each of its components.") {
                assertAll(Gen.quaternion(), Gen.quaternion()) {
                    a: Quaternion, b: Quaternion ->

                    a.clone().minusSelf(b).shouldBe(a.x - b.x, a.y - b.y,
                                                    a.z - b.z, a.w - b.w)
                }
            }
        }

        "Normalizing a quaternion" {
            should("divide each component by the magnitude.") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->

                    when(quat.magnitude != 0f) {
                        false -> quat.normalize()
                                    .shouldBe(quat.x, quat.y, quat.z, quat.w)
                        true  -> quat.normalize()
                                    .shouldBe(quat / quat.magnitude)
                    }
                }
            }
        }

        "Normalizing a quaternion in place" {
            should("divide each component by the magnitude.") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->

                    when(quat.magnitude != 0f) {
                        false -> quat.clone().normalizeSelf()
                                    .shouldBe(quat.x, quat.y, quat.z, quat.w)
                        true  -> quat.clone().normalizeSelf()
                                    .shouldBe(quat / quat.magnitude)
                    }
                }
            }
        }

        "Adding a quaternion to another" {
            should("add each quaternion's components.") {
                assertAll(Gen.quaternion(), Gen.quaternion()) {
                    a: Quaternion, b: Quaternion ->

                    (a + b).shouldBe(a.x + b.x, a.y + b.y, a.z + b.z,
                                     a.w + b.w)
                }
            }
        }

        "Adding a quaternion to another in place" {
            should("add each of its components.") {
                assertAll(Gen.quaternion(), Gen.quaternion()) {
                    a: Quaternion, b: Quaternion ->

                    a.clone().plusSelf(b).shouldBe(a.x + b.x,
                                                   a.y + b.y,
                                                   a.z + b.z,
                                                   a.w + b.w)
                }
            }
        }

        "Multiplying a quaternion by a scalar" {
            should("multiply each component by that scalar") {
                assertAll(Gen.quaternion(), Gen.numericFloats()) {
                    quat: Quaternion, scalar: Float ->

                    (quat * scalar).shouldBe(quat.x * scalar,
                                             quat.y * scalar,
                                             quat.z * scalar,
                                             quat.w * scalar)
                }
            }
        }

        "Multiplying a quaternion by a scalar in place" {
            should("multiply each of its components by that scalar") {
                assertAll(Gen.quaternion(), Gen.numericFloats()) {
                    quat: Quaternion, scalar: Float ->

                    quat.clone().timesSelf(scalar).shouldBe(quat.x * scalar,
                                                            quat.y * scalar,
                                                            quat.z * scalar,
                                                            quat.w * scalar)
                }
            }
        }

        "Assigning a quaternion's components using axis angles" {
            should("convert from half angle along an axis") {
                assertAll(Gen.vector3(), Gen.smallFloats()) {
                    vec: Vector3, scalar: Float ->

                    val angle    = scalar * MathUtils.TwoPi
                    val theta    = angle / 2f
                    val sinTheta = sin(theta)

                    Quaternion(angle, vec).shouldBe(vec.x * sinTheta,
                                                    vec.y * sinTheta,
                                                    vec.z * sinTheta,
                                                    cos(theta))
                }
            }

            should("produce the correct result to an example") {
                Quaternion(MathUtils.toRadians(90f), Vector3.X)
                    .shouldBe(0.7071f, 0f, 0f, 0.7071f, 0.00001f)
            }
        }

        "Assigning a quaternion's components using Euler angles" {
            should("convert from pitch, roll, and yaw") {
                assertAll(Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) { a: Float, b: Float, c: Float ->
                    val pitch = a * MathUtils.TwoPi
                    val roll  = b * MathUtils.TwoPi
                    val yaw   = c * MathUtils.TwoPi

                    val cosP = MathUtils.cos(pitch * 0.5f)
                    val sinP = MathUtils.sin(pitch * 0.5f)
                    val cosR = MathUtils.cos(roll * 0.5f)
                    val sinR = MathUtils.sin(roll * 0.5f)
                    val cosY = MathUtils.cos(yaw * 0.5f)
                    val sinY = MathUtils.sin(yaw * 0.5f)

                    Quaternion(pitch, roll, yaw).shouldBe(
                        cosY * sinR * cosP - sinY * cosR * sinP,
                        cosY * cosR * sinP + sinY * sinR * cosP,
                        sinY * cosR * cosP - cosY * sinR * sinP,
                        cosY * cosR * cosP + sinY * sinR * sinP
                    )
                }
            }

            should("produce the correct result to an example") {
                Quaternion(0f, MathUtils.toRadians(90f), 0f)
                    .shouldBe(0.7071f, 0f, 0f, 0.7071f, 0.00001f)
            }
        }

        "Taking the magnitude of a quaternion" {
            should("be the root of the product of its components.") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->
                    quat.magnitude.shouldBe(
                        MathUtils.sqrt(quat.x * quat.x + quat.y * quat.y +
                                       quat.z * quat.z + quat.w * quat.w))
                }
            }
        }

        "Taking the squared magnitude of a quaternion" {
            should("be the product of its components.") {
                assertAll(Gen.quaternion()) { quat: Quaternion ->
                    quat.magnitudeSquared.shouldBe(
                        quat.x * quat.x + quat.y * quat.y + quat.z * quat.z +
                        quat.w * quat.w)
                }
            }
        }
    }
}