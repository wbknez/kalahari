package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.gen.nonzeroFloats
import com.willoutwest.kalahari.gen.smallFloats
import io.kotlintest.Matcher
import io.kotlintest.MatcherResult
import io.kotlintest.data.forall
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row
import kotlin.math.abs

class Matrix4Generator : Gen<Matrix4> {

    override fun constants(): Iterable<Matrix4> = emptyList()

    override fun random(): Sequence<Matrix4> = generateSequence {
        Matrix4(FloatArray(16) { Gen.smallFloats().random().first() })
    }
}

fun Gen.Companion.matrix4(): Gen<Matrix4> = Matrix4Generator()

private fun equalsTo(mat: Matrix4) = object : Matcher<Matrix4> {

    override fun test(value: Matrix4): MatcherResult =
        MatcherResult(value == mat,
                      "Matrix $value should be $mat",
                      "Matrix $value should not be $mat")
}

private fun equalsTo(mat: Matrix4, tol: Float) = object : Matcher<Matrix4> {

    override fun test(value: Matrix4): MatcherResult =
        MatcherResult(value.toArray().zip(mat.toArray()).all {
                          abs(it.first - it.second) <= tol
                      },
                      "Matrix $value should be $mat",
                      "Matrix $value should not be $mat")
}

fun Matrix4.shouldBe(other: Matrix4) =
    this shouldBe equalsTo(other)

fun Matrix4.shouldBe(other: Matrix4, tol: Float) =
    this shouldBe equalsTo(other, tol)

fun product(a: FloatArray, b: FloatArray): Float =
    a.zip(b).map{ it.first * it.second }.sum()

/**
 * Test suite for [Matrix4].
 */
class Matrix4Test : ShouldSpec() {

    init {

        "Dividing a matrix by a scalar" {
            should("divide each element by that scalar") {
                assertAll(Gen.matrix4(), Gen.nonzeroFloats()) {
                    mat: Matrix4, scalar: Float ->

                    val inv = 1f / scalar

                    (mat / scalar).shouldBe(Matrix4(FloatArray(16) {
                        mat[it] * inv
                    }))
                }
            }
        }

        "Dividing a matrix by a scalar in place" {
            should("divide its elements by that scalar") {
                assertAll(Gen.matrix4(), Gen.nonzeroFloats()) {
                    mat: Matrix4, scalar: Float ->

                    val inv = 1f / scalar

                    mat.clone().divSelf(scalar).shouldBe(Matrix4(
                        FloatArray(16) {
                            mat[it] * inv
                        }))
                }
            }
        }

        "Taking the inverse of a matrix" {
            should("produce the correct result for the following examples") {
                forall(
                    row(Matrix4(1f, 1f, 1f, -1f,
                                1f, 1f, -1f, 1f,
                                1f, -1f, 1f, 1f,
                                -1f, 1f, 1f, 1f),
                        Matrix4(0.25f, 0.25f, 0.25f, -0.25f,
                                0.25f, 0.25f, -0.25f, 0.25f,
                                0.25f, -0.25f, 0.25f, 0.25f,
                                -0.25f, 0.25f, 0.25f, 0.25f)),
                    row(Matrix4(1f, 1f, 1f, 0f,
                                0f, 3f, 1f, 2f,
                                2f, 3f, 1f, 0f,
                                1f, 0f, 2f, 1f),
                        Matrix4(-3f, -0.5f, 1.5f, 1f,
                                1f, 0.25f, -0.25f, -0.5f,
                                3f, 0.25f, -1.25f, -0.5f,
                                -3f, 0f, 1f, 1f))
                ) { mat: Matrix4, result: Matrix4 ->
                    mat.invert().shouldBe(result)
                }
            }

            should("mirror itself when it is the identity") {
                Matrix4.Identity.invert().shouldBe(Matrix4.Identity)
            }

            should("throw an exception when not invertible") {
                assertAll(Gen.matrix4()) { mat: Matrix4 ->
                    mat[12] = 0f
                    mat[13] = 0f
                    mat[14] = 0f
                    mat[15] = 0f

                    shouldThrow<NotInvertibleException> {
                        mat.invert()
                    }
                }
            }
        }

        "Taking the inverse of a matrix in place" {
            should("produce the correct result for the following examples") {
                forall(
                    row(Matrix4(1f, 1f, 1f, -1f,
                                1f, 1f, -1f, 1f,
                                1f, -1f, 1f, 1f,
                                -1f, 1f, 1f, 1f),
                        Matrix4(0.25f, 0.25f, 0.25f, -0.25f,
                                0.25f, 0.25f, -0.25f, 0.25f,
                                0.25f, -0.25f, 0.25f, 0.25f,
                                -0.25f, 0.25f, 0.25f, 0.25f)),
                    row(Matrix4(1f, 1f, 1f, 0f,
                                0f, 3f, 1f, 2f,
                                2f, 3f, 1f, 0f,
                                1f, 0f, 2f, 1f),
                        Matrix4(-3f, -0.5f, 1.5f, 1f,
                                1f, 0.25f, -0.25f, -0.5f,
                                3f, 0.25f, -1.25f, -0.5f,
                                -3f, 0f, 1f, 1f))
                ) { mat: Matrix4, result: Matrix4 ->
                    mat.clone().invertSelf().shouldBe(result)
                }
            }

            should("mirror itself when it is the identity") {
                Matrix4.Identity.clone().invertSelf()
                    .shouldBe(Matrix4.Identity)
            }

            should("throw an exception when not invertible") {
                assertAll(Gen.matrix4()) { mat: Matrix4 ->
                    mat[12] = 0f
                    mat[13] = 0f
                    mat[14] = 0f
                    mat[15] = 0f

                    shouldThrow<NotInvertibleException> {
                        mat.clone().invertSelf()
                    }
                }
            }
        }

        "Subtracting a matrix to another" {
            should("subtract each matrix's elements") {
                assertAll(Gen.matrix4(), Gen.matrix4()) {
                    a: Matrix4, b: Matrix4 ->

                    (a - b).shouldBe(Matrix4(FloatArray(16) {
                        a[it] - b[it]
                    }))
                }
            }
        }

        "Subtracting a matrix to another in place" {
            should("subtract each of its elements") {
                assertAll(Gen.matrix4(), Gen.matrix4()) {
                    a: Matrix4, b: Matrix4 ->

                    a.clone().minusSelf(b).shouldBe(Matrix4(FloatArray(16) {
                        a[it] - b[it]
                    }))
                }
            }
        }

        "Adding a matrix to another" {
            should("add each matrix's elements") {
                assertAll(Gen.matrix4(), Gen.matrix4()) {
                    a: Matrix4, b: Matrix4 ->

                    (a + b).shouldBe(Matrix4(FloatArray(16) {
                        a[it] + b[it]
                    }))
                }
            }
        }

        "Adding a matrix to another in place" {
            should("add each of its elements") {
                assertAll(Gen.matrix4(), Gen.matrix4()) {
                    a: Matrix4, b: Matrix4 ->

                    a.clone().plusSelf(b).shouldBe(Matrix4(FloatArray(16) {
                        a[it] + b[it]
                    }))
                }
            }
        }

        "Multiplying a matrix by another" {
            should("multiply the columns of one by the rows of the other") {
                assertAll(Gen.matrix4(), Gen.matrix4()) {
                    a: Matrix4, b: Matrix4 ->

                    (a * b).shouldBe(Matrix4(FloatArray(16) {
                        val row = it / 4
                        val col = it % 4

                        product(a.row(row), b.column(col))
                    }))
                }
            }
        }

        "Multiplying a matrix by another in place" {
            should("multiply each of its columns by the other's rows") {
                assertAll(Gen.matrix4(), Gen.matrix4()) {
                    a: Matrix4, b: Matrix4 ->

                    a.clone().timesSelf(b).shouldBe(Matrix4(FloatArray(16) {
                        val row = it / 4
                        val col = it % 4

                        product(a.row(row), b.column(col))
                    }))
                }
            }
        }

        "Multiplying a matrix by a scalar" {
            should("multiply each element by that scalar") {
                assertAll(Gen.matrix4(), Gen.numericFloats()) {
                    mat: Matrix4, scalar: Float ->

                    (mat * scalar).shouldBe(Matrix4(FloatArray(16) {
                        mat[it] * scalar
                    }))
                }
            }
        }

        "Multiplying a matrix by a scalar in place" {
            should("multiply its elements by that scalar") {
                assertAll(Gen.matrix4(), Gen.numericFloats()) {
                    mat: Matrix4, scalar: Float ->

                    mat.clone().timesSelf(scalar).shouldBe(Matrix4(
                        FloatArray(16) {
                            mat[it] * scalar
                        }))
                }
            }
        }

        "Transposing a matrix" {
            should("swap the rows with the columns") {
                assertAll(Gen.matrix4()) { mat: Matrix4 ->
                    mat.transpose().shouldBe(Matrix4(
                        mat.t00, mat.t10, mat.t20, mat.t30,
                        mat.t01, mat.t11, mat.t21, mat.t31,
                        mat.t02, mat.t12, mat.t22, mat.t32,
                        mat.t03, mat.t13, mat.t23, mat.t33))
                }
            }
        }

        "Transposing a matrix in place" {
            should("swap its rows with its columns") {
                assertAll(Gen.matrix4()) { mat: Matrix4 ->
                    mat.clone().transposeSelf().shouldBe(Matrix4(
                        mat.t00, mat.t10, mat.t20, mat.t30,
                        mat.t01, mat.t11, mat.t21, mat.t31,
                        mat.t02, mat.t12, mat.t22, mat.t32,
                        mat.t03, mat.t13, mat.t23, mat.t33))
                }
            }
        }
    }
}