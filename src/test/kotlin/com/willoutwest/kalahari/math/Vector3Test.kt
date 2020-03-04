package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.data.forall
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row
import kotlin.math.abs

class Vector3Generator : Gen<Vector3> {

    override fun constants(): Iterable<Vector3> = emptyList()

    override fun random(): Sequence<Vector3> = generateSequence {
        Vector3(Gen.smallFloats().random().first(),
               Gen.smallFloats().random().first(),
               Gen.smallFloats().random().first())
    }
}

fun Gen.Companion.vector3(): Gen<Vector3> = Vector3Generator()

private fun equalsTo(vector: Vector3) = object : Matcher<Vector3> {

    override fun test(value: Vector3): MatcherResult =
        MatcherResult(value == vector,
                      "Vector $value should be $vector",
                      "Vector $value should not be $vector")
}

private fun equalsTo(vector: Vector3, tol: Float) = object : Matcher<Vector3> {

    override fun test(value: Vector3): MatcherResult =
        MatcherResult(value.toArray().zip(vector.toArray()).all {
                          abs(it.first - it.second) <= tol
                      },
                      "Vector $value should be $vector",
                      "Vector $value should not be $vector")
}

fun Vector3.shouldBe(x: Float, y: Float, z: Float) =
    this shouldBe equalsTo(Vector3(x, y, z))

fun Vector3.shouldBe(x: Float, y: Float, z: Float, tol: Float) =
    this shouldBe equalsTo(Vector3(x, y, z), tol)

fun Vector3.shouldBe(other: Vector3) =
    this shouldBe equalsTo(other)

fun Vector3.shouldBe(other: Vector3, tol: Float) =
    this shouldBe equalsTo(other, tol)

/**
 * Test suite for [vector3].
 */
class Vector3Test : ShouldSpec() {

    init {

        "Crossing a vector with three components" {
            should("cross all components") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vec: Vector3, x: Float, y: Float, z: Float ->

                    vec.cross(x, y, z).shouldBe(vec.y * z - vec.z * y,
                                                vec.z * x - vec.x * z,
                                                vec.x * y - vec.y * x)
                }
            }

            should("be orthogonal when they are orthogonal") {
                forall(
                    row(Vector3.X, Vector3.Y,  Vector3.Z),
                    row(Vector3.X, Vector3.Z, -Vector3.Y),
                    row(Vector3.Y, Vector3.X, -Vector3.Z),
                    row(Vector3.Y, Vector3.Z,  Vector3.X),
                    row(Vector3.Z, Vector3.X,  Vector3.Y),
                    row(Vector3.Z, Vector3.Y, -Vector3.X)
                ) { a: Vector3, b: Vector3, c: Vector3 ->
                    a.cross(b.x, b.y, b.z).shouldBe(c)
                }
            }

            should("be zero when they are parallel") {
                forall(
                    row(Vector3.X, Vector3.X),
                    row(Vector3.Y, Vector3.Y),
                    row(Vector3.Z, Vector3.Z)
                ) { a: Vector3, b: Vector3 ->

                    a.cross(b.x, b.y, b.z).shouldBe(Vector3.Zero)
                }
            }
        }

        "Crossing a vector with another" {
            should("cross each vector's components") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    a.cross(b).shouldBe(a.y * b.z - a.z * b.y,
                                        a.z * b.x - a.x * b.z,
                                        a.x * b.y - a.y * b.x)
                }
            }

            should("be orthogonal when they are orthogonal") {
                forall(
                    row(Vector3.X, Vector3.Y,  Vector3.Z),
                    row(Vector3.X, Vector3.Z, -Vector3.Y),
                    row(Vector3.Y, Vector3.X, -Vector3.Z),
                    row(Vector3.Y, Vector3.Z,  Vector3.X),
                    row(Vector3.Z, Vector3.X,  Vector3.Y),
                    row(Vector3.Z, Vector3.Y, -Vector3.X)
                ) { a: Vector3, b: Vector3, c: Vector3 ->

                    a.cross(b).shouldBe(c)
                }
            }

            should("be zero when they are parallel") {
                forall(
                    row(Vector3.X, Vector3.X),
                    row(Vector3.Y, Vector3.Y),
                    row(Vector3.Z, Vector3.Z)
                ) { a: Vector3, b: Vector3 ->

                    a.cross(b).shouldBe(Vector3.Zero)
                }
            }
        }

        "Crossing a vector with three components in place" {
            should("cross all components") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vec: Vector3, x: Float, y: Float, z: Float ->

                    vec.clone().crossSelf(x, y, z).shouldBe(
                        vec.y * z - vec.z * y,
                        vec.z * x - vec.x * z,
                        vec.x * y - vec.y * x
                    )
                }
            }

            should("be orthogonal when they are orthogonal") {
                forall(
                    row(Vector3.X, Vector3.Y,  Vector3.Z),
                    row(Vector3.X, Vector3.Z, -Vector3.Y),
                    row(Vector3.Y, Vector3.X, -Vector3.Z),
                    row(Vector3.Y, Vector3.Z,  Vector3.X),
                    row(Vector3.Z, Vector3.X,  Vector3.Y),
                    row(Vector3.Z, Vector3.Y, -Vector3.X)
                ) { a: Vector3, b: Vector3, c: Vector3 ->
                    a.clone().crossSelf(b.x, b.y, b.z).shouldBe(c)
                }
            }

            should("be zero when they are parallel") {
                forall(
                    row(Vector3.X, Vector3.X),
                    row(Vector3.Y, Vector3.Y),
                    row(Vector3.Z, Vector3.Z)
                ) { a: Vector3, b: Vector3 ->

                    a.clone().crossSelf(b.x, b.y, b.z).shouldBe(Vector3.Zero)
                }
            }
        }

        "Crossing a vector with another in place" {
            should("cross each vector's components") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    a.clone().crossSelf(b).shouldBe(a.y * b.z - a.z * b.y,
                                                    a.z * b.x - a.x * b.z,
                                                    a.x * b.y - a.y * b.x)
                }
            }

            should("be orthogonal when they are orthogonal") {
                forall(
                    row(Vector3.X, Vector3.Y,  Vector3.Z),
                    row(Vector3.X, Vector3.Z, -Vector3.Y),
                    row(Vector3.Y, Vector3.X, -Vector3.Z),
                    row(Vector3.Y, Vector3.Z,  Vector3.X),
                    row(Vector3.Z, Vector3.X,  Vector3.Y),
                    row(Vector3.Z, Vector3.Y, -Vector3.X)
                ) { a: Vector3, b: Vector3, c: Vector3 ->

                    a.clone().crossSelf(b).shouldBe(c)
                }
            }

            should("be zero when they are parallel") {
                forall(
                    row(Vector3.X, Vector3.X),
                    row(Vector3.Y, Vector3.Y),
                    row(Vector3.Z, Vector3.Z)
                ) { a: Vector3, b: Vector3 ->

                    a.clone().crossSelf(b).shouldBe(Vector3.Zero)
                }
            }
        }

        "Computing the distance between a vector and three components" {
            should("be the root of the iterative product of all components") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, x: Float, y: Float, z: Float ->

                    vector.distanceTo(x, y, z).shouldBe(MathUtils.sqrt(
                        MathUtils.pow(vector.x - x, 2f) +
                        MathUtils.pow(vector.y - y, 2f) +
                        MathUtils.pow(vector.z - z, 2f)
                    ))
                }
            }
        }

        "Computing the distance between two vectors" {
            should("be the root of the dot product of (a - b)") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    a.distanceTo(b).shouldBe(
                        MathUtils.sqrt((a - b).dot(a - b)))
                }
            }
        }

        "Computing the squared distance between a vector and three components" {
            should("be the iterative product of all components") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, x: Float, y: Float, z: Float ->

                    vector.distanceSquaredTo(x, y, z).shouldBe(
                        MathUtils.pow(vector.x - x, 2f) +
                        MathUtils.pow(vector.y - y, 2f) +
                        MathUtils.pow(vector.z - z, 2f)
                    )
                }
            }
        }

        "Computing the squared distance between two vectors" {
            should("be the dot product of (a - b)") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    a.distanceSquaredTo(b).shouldBe((a - b).dot(a - b))
                }
            }
        }

        "Dividing a vector by a scalar" {
            should("divide each component by that scalar") {
                assertAll(Gen.vector3(), Gen.nonzeroFloats()) {
                    vec: Vector3, scalar: Float ->

                    val inv = 1f / scalar

                    (vec / scalar).shouldBe(vec.x * inv,
                                            vec.y * inv,
                                            vec.z * inv)
                }
            }
        }

        "Dividing a vector by a scalar in place" {
            should("divide each of its components by that scalar") {
                assertAll(Gen.vector3(), Gen.nonzeroFloats()) {
                    vec: Vector3, scalar: Float ->

                    val inv = 1f / scalar

                    vec.clone().divSelf(scalar).shouldBe(vec.x * inv,
                                                         vec.y * inv,
                                                         vec.z * inv)
                }
            }
        }

        "Doting a vector with three components" {
            should("sum the product of each component") {
                assertAll(Gen.vector3(), Gen.float(), Gen.float(),
                          Gen.float()) {
                    vec: Vector3, x: Float, y: Float, z: Float ->

                    vec.dot(x, y, z).shouldBe(
                        vec.x * x + vec.y * y + vec.z * z)
                }
            }
        }

        "Dotting a vector with another" {
            should("sum the product of their components") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    a.dot(b).shouldBe(a.x * b.x + a.y * b.y + a.z * b.z)
                }
            }
        }

        "Inverting a vector" {
            should("invert each component") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.invert().shouldBe(MathUtils.safeInverse(vec.x),
                                          MathUtils.safeInverse(vec.y),
                                          MathUtils.safeInverse(vec.z))
                }
            }
        }

        "Inverting a vector in place" {
            should("invert each of its components") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.clone().invert().shouldBe(MathUtils.safeInverse(vec.x),
                                                  MathUtils.safeInverse(vec.y),
                                                  MathUtils.safeInverse(vec.z))
                }
            }
        }

        "Subtracting a vector from components" {
            should("subtract all components from each other") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, x: Float, y: Float, z: Float ->

                    vector.minus(x, y, z)
                        .shouldBe(vector.x - x, vector.y - y, vector.z - z)
                }
            }
        }

        "Subtracting a vector from another" {
            should("subtract each vector's components") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    (a - b).shouldBe(a.x - b.x, a.y - b.y, a.z - b.z)
                }
            }
        }

        "Subtracting a vector from components in place" {
            should("subtract its components from the other") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, x: Float, y: Float, z: Float ->

                    vector.clone().minusSelf(x, y, z)
                        .shouldBe(vector.x - x, vector.y - y, vector.z - z)
                }
            }
        }

        "Subtracting a vector from another in place" {
            should("subtract each of its components") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    a.clone().minusSelf(b).shouldBe(a.x - b.x,
                                                    a.y - b.y,
                                                    a.z - b.z)
                }
            }
        }

        "Negating a vector" {
            should("negate each component") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    (-vec).shouldBe(-vec.x, -vec.y, -vec.z)
                }
            }
        }

        "Negating a vector in place" {
            should("negate each of its components") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.clone().negateSelf().shouldBe(-vec.x, -vec.y, -vec.z)
                }
            }
        }

        "Normalizing a vector" {
            should("divide each component by the magnitude") {
                assertAll(Gen.vector3()) { vec: Vector3 ->

                    when(vec.magnitude != 0f) {
                        false -> vec.normalize().shouldBe(vec.x, vec.y, vec.z)
                        true  -> vec.normalize().shouldBe(vec / vec.magnitude)
                    }
                }
            }
        }

        "Normalizing a vector in place" {
            should("divide each component by the magnitude") {
                assertAll(Gen.vector3()) { vec: Vector3 ->

                    when(vec.magnitude != 0f) {
                        false -> vec.clone().normalizeSelf()
                                    .shouldBe(vec.x, vec.y, vec.z)
                        true  -> vec.clone().normalizeSelf()
                                    .shouldBe(vec / vec.magnitude)
                    }
                }
            }
        }

        "Adding a vector from components" {
            should("add all components to each other") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, x: Float, y: Float, z: Float ->

                    vector.plus(x, y, z)
                        .shouldBe(vector.x + x, vector.y + y, vector.z + z)
                }
            }
        }

        "Adding a vector to another" {
            should("add each vector's components") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    (a + b).shouldBe(a.x + b.x, a.y + b.y, a.z + b.z)
                }
            }
        }

        "Adding a vector from components in place" {
            should("add its components to the other") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, x: Float, y: Float, z: Float ->

                    vector.clone().plusSelf(x, y, z)
                        .shouldBe(vector.x + x, vector.y + y, vector.z + z)
                }
            }
        }

        "Adding a vector to another in place" {
            should("add each of its components") {
                assertAll(Gen.vector3(), Gen.vector3()) {
                    a: Vector3, b: Vector3 ->

                    a.clone().plusSelf(b).shouldBe(a.x + b.x,
                                                   a.y + b.y,
                                                   a.z + b.z)
                }
            }
        }

        "Adding a vector from scaled components" {
            should("add all components to each other") {
                assertAll(Gen.vector3(), Gen.smallFloats(),
                          Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, scalar: Float, x: Float, y: Float,
                    z: Float ->

                    vector.plusTimes(scalar, x, y, z).shouldBe(
                        vector.x + scalar * x,
                        vector.y + scalar * y,
                        vector.z + scalar * z
                    )
                }
            }
        }

        "Adding a vector to another that has been scaled" {
            should("add each vector's components") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.vector3()) {
                    a: Vector3, scalar, b: Vector3 ->

                    a.plusTimes(scalar, b).shouldBe(
                        a.x + scalar * b.x,
                        a.y + scalar * b.y,
                        a.z + scalar * b.z
                    )
                }
            }
        }

        "Adding a vector from scaled components in place" {
            should("add its components to the other") {
                assertAll(Gen.vector3(), Gen.smallFloats(),
                          Gen.smallFloats(), Gen.smallFloats(),
                          Gen.smallFloats()) {
                    vector: Vector3, scalar: Float, x: Float, y: Float,
                    z: Float ->

                    vector.clone().plusTimesSelf(scalar, x, y, z).shouldBe(
                        vector.x + scalar * x,
                        vector.y + scalar * y,
                        vector.z + scalar * z
                    )
                }
            }
        }

        "Adding a vector to another that has been scaled in place" {
            should("add each of its components") {
                assertAll(Gen.vector3(), Gen.smallFloats(), Gen.vector3()) {
                    a: Vector3, scalar, b: Vector3 ->

                    a.clone().plusTimesSelf(scalar, b).shouldBe(
                        a.x + scalar * b.x,
                        a.y + scalar * b.y,
                        a.z + scalar * b.z
                    )
                }
            }
        }

        "Multiplying a vector by a scalar" {
            should("multiply each component by that scalar") {
                assertAll(Gen.vector3(), Gen.numericFloats()) {
                    vec: Vector3, scalar: Float ->

                    (vec * scalar).shouldBe(vec.x * scalar,
                                            vec.y * scalar,
                                            vec.z * scalar)
                }
            }
        }

        "Multiplying a vector by a scalar in place" {
            should("multiply each of its components by that scalar") {
                assertAll(Gen.vector3(), Gen.numericFloats()) {
                    vec: Vector3, scalar: Float ->

                    vec.clone().timesSelf(scalar).shouldBe(vec.x * scalar,
                                                           vec.y * scalar,
                                                           vec.z * scalar)
                }
            }
        }

        "Converting a vector to a scaling matrix" {
            should("place all components along the diagonal") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.toScalingMatrix().shouldBe(Matrix4(
                        vec.x, 0f, 0f, 0f,
                        0f, vec.y, 0f, 0f,
                        0f, 0f, vec.z, 0f,
                        0f, 0f, 0f, 1f
                    ))
                }
            }
        }

        "Converting a vector to a translation matrix" {
            should("place all components along the last column") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.toTranslationMatrix().shouldBe(Matrix4(
                        1f, 0f, 0f, vec.x,
                        0f, 1f, 0f, vec.y,
                        0f, 0f, 1f, vec.z,
                        0f, 0f, 0f, 1f
                    ))
                }
            }
        }

        "Transforming a vector" {
            should("be the product of each component with a row") {
                assertAll(Gen.vector3(), Gen.matrix4()) {
                    vec: Vector3, mat: Matrix4 ->

                    vec.transform(mat).shouldBe(
                        vec.x * mat.t00 + vec.y * mat.t01 + vec.z * mat.t02,
                        vec.x * mat.t10 + vec.y * mat.t11 + vec.z * mat.t12,
                        vec.x * mat.t20 + vec.y * mat.t21 + vec.z * mat.t22
                    )
                }
            }

            should("produce itself when using the identity matrix") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.transform(Matrix4.Identity).shouldBe(vec)
                }
            }

            should("produce zero when using the zero matrix") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.transform(Matrix4.Zero).shouldBe(Vector3.Zero)
                }
            }
        }

        "Transforming a vector in place" {
            should("be the product of its component with a row") {
                assertAll(Gen.vector3(), Gen.matrix4()) {
                    vec: Vector3, mat: Matrix4 ->

                    vec.clone().transformSelf(mat).shouldBe(
                        vec.x * mat.t00 + vec.y * mat.t01 + vec.z * mat.t02,
                        vec.x * mat.t10 + vec.y * mat.t11 + vec.z * mat.t12,
                        vec.x * mat.t20 + vec.y * mat.t21 + vec.z * mat.t22
                    )
                }
            }

            should("produce itself when using the identity matrix") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.clone().transformSelf(Matrix4.Identity).shouldBe(vec)
                }
            }

            should("produce zero when using the zero matrix") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.clone().transformSelf(Matrix4.Zero)
                        .shouldBe(Vector3.Zero)
                }
            }
        }

        "Taking the magnitude of a vector" {
            should("be the root of the product of its components") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.magnitude.shouldBe(MathUtils.sqrt(vec.dot(vec)))
                }
            }
        }

        "Taking the squared magnitude of a vector" {
            should("be the product of its components") {
                assertAll(Gen.vector3()) { vec: Vector3 ->
                    vec.magnitudeSquared.shouldBe(vec.dot(vec))
                }
            }
        }
    }
}