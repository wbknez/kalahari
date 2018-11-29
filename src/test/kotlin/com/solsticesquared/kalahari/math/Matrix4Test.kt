package com.solsticesquared.kalahari.math

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Represents an implementation of [Gen] that creates [Matrix4] objects with
 * random components.
 */
class Matrix4Generator : Gen<Matrix4> {
    override fun generate(): Matrix4 =
        Matrix4(FloatArray(16) { Gen.float().generate() })
}

/**
 * Test suite for [Matrix4].
 */
class Matrix4Test : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Matrix4] instances.
         */
        val Mat4Gen = Matrix4Generator()
    }

    init {

        "The inverse of a matrix" {
            should("be itself when identity.") {
                val mat    = Matrix4()
                val expected = mat.clone()

                mat.invert() shouldBe expected
            }

            should("match the following example.") {
                val mat = Matrix4(
                    4f, 0f, 0f, 0f,
                    0f, 0f, 2f, 0f,
                    0f, 1f, 2f, 0f,
                    1f, 0f, 0f, 1f
                                 )
                val expected = Matrix4(
                    0.25f, 0f, 0f, 0f,
                    0f, -1f, 1f, 0f,
                    0f, 0.5f, 0f, 0f,
                    -0.25f, 0f, 0f, 1f
                                      )

                mat.invert() shouldBe expected
            }

            should("throw when the inverse cannot be found.") {
                shouldThrow<CannotInvertMatrixException>{
                    Matrix4.Zero.clone().invert()
                }
            }
        }

        "A matrix inverting itself" {
            should("be itself when identity.") {
                val mat    = Matrix4()
                val expected = mat.clone()

                mat.invertSelf() shouldBe expected
            }

            should("match the following example.") {
                val mat = Matrix4(
                    1f, 1f, 1f, 0f,
                    0f, 3f, 1f, 2f,
                    2f, 3f, 1f, 0f,
                    1f, 0f, 2f, 1f
                                 )
                val expected = Matrix4(
                    -3f, -0.5f, 1.5f, 1f,
                    1f, 0.25f, -0.25f, -0.5f,
                    3f, 0.25f, -1.25f, -0.5f,
                    -3f, 0f, 1f, 1f
                                      )

                mat.invertSelf() shouldBe expected
            }

            should("throw when the inverse cannot be found.") {
                shouldThrow<CannotInvertMatrixException>{
                    Matrix4.Zero.clone().invertSelf()
                }
            }
        }

        "One matrix added to another" {
            should("add each of their elements.") {
                forAll(Mat4Gen, Mat4Gen) { a: Matrix4, b: Matrix4 ->
                    val expected = Matrix4()

                    for(i in 0..15) { expected[i] = a[i] + b[i] }
                    (a + b) == expected
                }
            }
        }

        "A matrix adding itself to another" {
            should("add each of its elements.") {
                forAll(Mat4Gen, Mat4Gen) { a: Matrix4, b: Matrix4 ->
                    val expected = Matrix4()

                    for(i in 0..15) { expected[i] = a[i] + b[i] }
                    a.plusSelf(b) == expected
                }
            }
        }

        "One matrix multiplied by a scalar" {
            should("multiply each element by that scalar.") {
                forAll(Mat4Gen, Gen.float()) { mat: Matrix4, f: Float ->
                    val expected = Matrix4()

                    for(i in 0..15) { expected[i] = mat[i] * f }
                    (mat * f) == expected
                }
            }
        }

        "One matrix multiplied by another" {
            should("multiply each row by each column.") {
                forAll(Mat4Gen, Mat4Gen) { a: Matrix4, b: Matrix4 ->
                    val expected = Matrix4(
                        a.t00 * b.t00 + a.t01 * b.t10 +
                        a.t02 * b.t20 + a.t03 * b.t30,
                        a.t00 * b.t01 + a.t01 * b.t11 +
                        a.t02 * b.t21 + a.t03 * b.t31,
                        a.t00 * b.t02 + a.t01 * b.t12 +
                        a.t02 * b.t22 + a.t03 * b.t32,
                        a.t00 * b.t03 + a.t01 * b.t13 +
                        a.t02 * b.t23 + a.t03 * b.t33,
                        a.t10 * b.t00 + a.t11 * b.t10 +
                        a.t12 * b.t20 + a.t13 * b.t30,
                        a.t10 * b.t01 + a.t11 * b.t11 +
                        a.t12 * b.t21 + a.t13 * b.t31,
                        a.t10 * b.t02 + a.t11 * b.t12 +
                        a.t12 * b.t22 + a.t13 * b.t32,
                        a.t10 * b.t03 + a.t11 * b.t13 +
                        a.t12 * b.t23 + a.t13 * b.t33,
                        a.t20 * b.t00 + a.t21 * b.t10 +
                        a.t22 * b.t20 + a.t23 * b.t30,
                        a.t20 * b.t01 + a.t21 * b.t11 +
                        a.t22 * b.t21 + a.t23 * b.t31,
                        a.t20 * b.t02 + a.t21 * b.t12 +
                        a.t22 * b.t22 + a.t23 * b.t32,
                        a.t20 * b.t03 + a.t21 * b.t13 +
                        a.t22 * b.t23 + a.t23 * b.t33,
                        a.t30 * b.t00 + a.t31 * b.t10 +
                        a.t32 * b.t20 + a.t33 * b.t30,
                        a.t30 * b.t01 + a.t31 * b.t11 +
                        a.t32 * b.t21 + a.t33 * b.t31,
                        a.t30 * b.t02 + a.t31 * b.t12 +
                        a.t32 * b.t22 + a.t33 * b.t32,
                        a.t30 * b.t03 + a.t31 * b.t13 +
                        a.t32 * b.t23 + a.t33 * b.t33
                                          )
                    (a * b) == expected
                }
            }

            should("match the following example.") {
                val a = Matrix4(
                    5f, 2f, 6f, 1f,
                    0f, 6f, 2f, 0f,
                    3f, 8f, 1f, 4f,
                    1f, 8f, 5f, 6f
                               )
                val b = Matrix4(
                    7f, 5f, 8f, 0f,
                    1f, 8f, 2f, 6f,
                    9f, 4f, 3f, 8f,
                    5f, 3f, 7f, 9f
                               )
                val expected = Matrix4(
                    96f, 68f, 69f, 69f,
                    24f, 56f, 18f, 52f,
                    58f, 95f, 71f, 92f,
                    90f, 107f, 81f, 142f
                                      )

                a * b shouldBe expected
            }
        }

        "A matrix multiplying itself by a scalar" {
            should("multiply each element by that scalar.") {
                forAll(Mat4Gen, Gen.float()) { mat: Matrix4, f: Float ->
                    val expected = Matrix4()

                    for(i in 0..15) { expected[i] = mat[i] * f }
                    mat.timesSelf(f) == expected
                }
            }
        }

        "A matrix multiplying itself by another" {
            should("multiply each row by each column.") {
                forAll(Mat4Gen, Mat4Gen) { a: Matrix4, b: Matrix4 ->
                    val expected = Matrix4(
                        a.t00 * b.t00 + a.t01 * b.t10 +
                        a.t02 * b.t20 + a.t03 * b.t30,
                        a.t00 * b.t01 + a.t01 * b.t11 +
                        a.t02 * b.t21 + a.t03 * b.t31,
                        a.t00 * b.t02 + a.t01 * b.t12 +
                        a.t02 * b.t22 + a.t03 * b.t32,
                        a.t00 * b.t03 + a.t01 * b.t13 +
                        a.t02 * b.t23 + a.t03 * b.t33,
                        a.t10 * b.t00 + a.t11 * b.t10 +
                        a.t12 * b.t20 + a.t13 * b.t30,
                        a.t10 * b.t01 + a.t11 * b.t11 +
                        a.t12 * b.t21 + a.t13 * b.t31,
                        a.t10 * b.t02 + a.t11 * b.t12 +
                        a.t12 * b.t22 + a.t13 * b.t32,
                        a.t10 * b.t03 + a.t11 * b.t13 +
                        a.t12 * b.t23 + a.t13 * b.t33,
                        a.t20 * b.t00 + a.t21 * b.t10 +
                        a.t22 * b.t20 + a.t23 * b.t30,
                        a.t20 * b.t01 + a.t21 * b.t11 +
                        a.t22 * b.t21 + a.t23 * b.t31,
                        a.t20 * b.t02 + a.t21 * b.t12 +
                        a.t22 * b.t22 + a.t23 * b.t32,
                        a.t20 * b.t03 + a.t21 * b.t13 +
                        a.t22 * b.t23 + a.t23 * b.t33,
                        a.t30 * b.t00 + a.t31 * b.t10 +
                        a.t32 * b.t20 + a.t33 * b.t30,
                        a.t30 * b.t01 + a.t31 * b.t11 +
                        a.t32 * b.t21 + a.t33 * b.t31,
                        a.t30 * b.t02 + a.t31 * b.t12 +
                        a.t32 * b.t22 + a.t33 * b.t32,
                        a.t30 * b.t03 + a.t31 * b.t13 +
                        a.t32 * b.t23 + a.t33 * b.t33
                                          )
                    a.timesSelf(b) == expected
                }
            }

            should("match the following example.") {
                val a = Matrix4(
                    5f, 2f, 6f, 1f,
                    0f, 6f, 2f, 0f,
                    3f, 8f, 1f, 4f,
                    1f, 8f, 5f, 6f
                               )
                val b = Matrix4(
                    7f, 5f, 8f, 0f,
                    1f, 8f, 2f, 6f,
                    9f, 4f, 3f, 8f,
                    5f, 3f, 7f, 9f
                               )
                val expected = Matrix4(
                    96f, 68f, 69f, 69f,
                    24f, 56f, 18f, 52f,
                    58f, 95f, 71f, 92f,
                    90f, 107f, 81f, 142f
                                      )

                a.timesSelf(b) shouldBe expected
            }
        }

        "One matrix subtracted from another" {
            should("subtract each of their elements") {
                forAll(Mat4Gen, Mat4Gen) { a: Matrix4, b: Matrix4 ->
                    val expected = Matrix4()

                    for(i in 0..15) { expected[i] = a[i] - b[i] }
                    (a - b) == expected
                }
            }
        }

        "A matrix subtracting itself from another" {
            should("subtract each of its components.") {
                forAll(Mat4Gen, Mat4Gen) { a: Matrix4, b: Matrix4 ->
                    val expected = Matrix4()

                    for(i in 0..15) { expected[i] = a[i] - b[i] }
                    a.minusSelf(b) == expected
                }
            }
        }

        "The transpose of a matrix" {
            should("swap rows with columns.") {
                forAll(Mat4Gen) { mat: Matrix4 ->
                    val expected = Matrix4(
                        mat.t00, mat.t10, mat.t20, mat.t30,
                        mat.t01, mat.t11, mat.t21, mat.t31,
                        mat.t02, mat.t12, mat.t22, mat.t32,
                        mat.t03, mat.t13, mat.t23, mat.t33
                                          )
                    mat.transpose() == expected
                }
            }
        }

        "A matrix transposing itself" {
            should("swap rows with columns.") {
                forAll(Mat4Gen) { mat: Matrix4 ->
                    val expected = Matrix4(
                        mat.t00, mat.t10, mat.t20, mat.t30,
                        mat.t01, mat.t11, mat.t21, mat.t31,
                        mat.t02, mat.t12, mat.t22, mat.t32,
                        mat.t03, mat.t13, mat.t23, mat.t33
                                          )
                    mat.transposeSelf() == expected
                }
            }
        }
    }
}
