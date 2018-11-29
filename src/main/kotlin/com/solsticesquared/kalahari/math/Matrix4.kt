package com.solsticesquared.kalahari.math

/**
 * Represents an implementation of [RuntimeException] that is thrown when an
 * attempt is made to take the inverse of a matrix that is not invertible.
 */
class CannotInvertMatrixException(msg: String) : RuntimeException(msg)

/**
 * Represents a matrix in four dimensional Cartesian space.
 */
open class Matrix4(t00: Float = 1f, t01: Float = 0f,
                   t02: Float = 0f, t03: Float = 0f,
                   t10: Float = 0f, t11: Float = 1f,
                   t12: Float = 0f, t13: Float = 0f,
                   t20: Float = 0f, t21: Float = 0f,
                   t22: Float = 1f, t23: Float = 0f,
                   t30: Float = 0f, t31: Float = 0f,
                   t32: Float = 0f, t33: Float = 1f)
    : Cloneable, Table4(t00, t01, t02, t03,
                        t10, t11, t12, t13,
                        t20, t21, t22, t23,
                        t30, t31, t32, t33) {

    companion object {

        /**
         * The identity matrix.
         */
        val Identity = Matrix4(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
                              )

        /**
         * The zero matrix.
         */
        val Zero = Matrix4(
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f
                          )
    }

    /**
     * Constructor.
     *
     * @param elements
     *        The array to copy from.
     */
    constructor(elements: FloatArray)
        : this(elements[0], elements[1], elements[2], elements[3],
               elements[4], elements[5], elements[6], elements[7],
               elements[8], elements[9], elements[10], elements[11],
               elements[12], elements[13], elements[14], elements[15])

    /**
     * Constructor.
     *
     * @param mat
     *        The matrix to copy from.]
     */
    constructor(mat: Table4?)
        : this(mat!!.t00, mat.t01, mat.t02, mat.t03,
               mat.t10, mat.t11, mat.t12, mat.t13,
               mat.t20, mat.t21, mat.t22, mat.t23,
               mat.t30, mat.t31, mat.t32, mat.t33)

    override fun clone(): Matrix4 = Matrix4(this)

    operator fun div(scalar: Float): Matrix4 {
        val inv = 1f / scalar
        
        return Matrix4(
            this.t00 * inv, this.t01 * inv,
            this.t02 * inv, this.t03 * inv,
            this.t10 * inv, this.t11 * inv,
            this.t12 * inv, this.t13 * inv,
            this.t20 * inv, this.t21 * inv,
            this.t22 * inv, this.t23 * inv,
            this.t30 * inv, this.t31 * inv,
            this.t32 * inv, this.t33 * inv
        )
    }
    
    /**
     * Divides this matrix by the specified scalar and also modifies this
     * matrix as a result.
     *
     * @param scalar
     *        The value to use.
     * @return A reference to this matrix for easy chaining.
     */
    fun divSelf(scalar: Float): Matrix4 {
        val inv = 1f / scalar

        this.t00 *= inv; this.t01 *= inv
        this.t02 *= inv; this.t03 *= inv
        this.t10 *= inv; this.t11 *= inv
        this.t12 *= inv; this.t13 *= inv
        this.t20 *= inv; this.t21 *= inv
        this.t22 *= inv; this.t23 *= inv
        this.t30 *= inv; this.t31 *= inv
        this.t32 *= inv; this.t33 *= inv
        return this
    }

    /**
     * Computes the inverse of this matrix.
     *
     * If the inverse cannot be found then an exception will be thrown.
     *
     * @return The inverse.
     * @throws CannotInvertMatrixException
     *         If the inverse cannot be computed.
     */
    fun invert(): Matrix4 {
        val s0 = this.t00 * this.t11 - this.t10 * this.t01
        val s1 = this.t00 * this.t12 - this.t10 * this.t02
        val s2 = this.t00 * this.t13 - this.t10 * this.t03
        val s3 = this.t01 * this.t12 - this.t11 * this.t02
        val s4 = this.t01 * this.t13 - this.t11 * this.t03
        val s5 = this.t02 * this.t13 - this.t12 * this.t03

        val c5 = this.t22 * this.t33 - this.t32 * this.t23
        val c4 = this.t21 * this.t33 - this.t31 * this.t23
        val c3 = this.t21 * this.t32 - this.t31 * this.t22
        val c2 = this.t20 * this.t33 - this.t30 * this.t23
        val c1 = this.t20 * this.t32 - this.t30 * this.t22
        val c0 = this.t20 * this.t31 - this.t30 * this.t21

        val det = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0

        if(det == 0f) {
            throw CannotInvertMatrixException(
                "The matrix is not invertible: ${this}."
            )
        }

        val invDet = 1f / det
        val inv    = Matrix4()

        inv.t00 = (this.t11 * c5 - this.t12 * c4 + this.t13 * c3)  * invDet
        inv.t01 = (-this.t01 * c5 + this.t02 * c4 - this.t03 * c3) * invDet
        inv.t02 = (this.t31 * s5 - this.t32 * s4 + this.t33 * s3)  * invDet
        inv.t03 = (-this.t21 * s5 + this.t22 * s4 - this.t23 * s3) * invDet

        inv.t10 = (-this.t10 * c5 + this.t12 * c2 - this.t13 * c1) * invDet
        inv.t11 = (this.t00 * c5 - this.t02 * c2 + this.t03 * c1)  * invDet
        inv.t12 = (-this.t30 * s5 + this.t32 * s2 - this.t33 * s1) * invDet
        inv.t13 = (this.t20 * s5 - this.t22 * s2 + this.t23 * s1)  * invDet

        inv.t20 = (this.t10 * c4 - this.t11 * c2 + this.t13 * c0)  * invDet
        inv.t21 = (-this.t00 * c4 + this.t01 * c2 - this.t03 * c0) * invDet
        inv.t22 = (this.t30 * s4 - this.t31 * s2 + this.t33 * s0)  * invDet
        inv.t23 = (-this.t20 * s4 + this.t21 * s2 - this.t23 * s0) * invDet

        inv.t30 = (-this.t10 * c3 + this.t11 * c1 - this.t12 * c0) * invDet
        inv.t31 = (this.t00 * c3 - this.t01 * c1 + this.t02 * c0)  * invDet
        inv.t32 = (-this.t30 * s3 + this.t31 * s1 - this.t32 * s0) * invDet
        inv.t33 = (this.t20 * s3 - this.t21 * s1 + this.t22 * s0)  * invDet

        return inv
    }

    /**
     * Computes the inverse of this matrix and also modifies this
     * matrix as a result.
     *
     * If the inverse cannot be found then an exception will be thrown.
     *
     * @return A reference to this matrix for easy chaining.
     * @throws CannotInvertMatrixException
     *         If the inverse cannot be computed.
     */
    fun invertSelf(): Matrix4 {
        val s0 = this.t00 * this.t11 - this.t10 * this.t01
        val s1 = this.t00 * this.t12 - this.t10 * this.t02
        val s2 = this.t00 * this.t13 - this.t10 * this.t03
        val s3 = this.t01 * this.t12 - this.t11 * this.t02
        val s4 = this.t01 * this.t13 - this.t11 * this.t03
        val s5 = this.t02 * this.t13 - this.t12 * this.t03

        val c5 = this.t22 * this.t33 - this.t32 * this.t23
        val c4 = this.t21 * this.t33 - this.t31 * this.t23
        val c3 = this.t21 * this.t32 - this.t31 * this.t22
        val c2 = this.t20 * this.t33 - this.t30 * this.t23
        val c1 = this.t20 * this.t32 - this.t30 * this.t22
        val c0 = this.t20 * this.t31 - this.t30 * this.t21

        val det = s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0

        if(det == 0f) {
            throw CannotInvertMatrixException(
                "The matrix is not invertible: ${this}."
            )
        }

        val invDet = 1f / det

        val tp00 = (this.t11 * c5 - this.t12 * c4 + this.t13 * c3)  * invDet
        val tp01 = (-this.t01 * c5 + this.t02 * c4 - this.t03 * c3) * invDet
        val tp02 = (this.t31 * s5 - this.t32 * s4 + this.t33 * s3)  * invDet
        val tp03 = (-this.t21 * s5 + this.t22 * s4 - this.t23 * s3) * invDet

        val tp10 = (-this.t10 * c5 + this.t12 * c2 - this.t13 * c1) * invDet
        val tp11 = (this.t00 * c5 - this.t02 * c2 + this.t03 * c1)  * invDet
        val tp12 = (-this.t30 * s5 + this.t32 * s2 - this.t33 * s1) * invDet
        val tp13 = (this.t20 * s5 - this.t22 * s2 + this.t23 * s1)  * invDet

        val tp20 = (this.t10 * c4 - this.t11 * c2 + this.t13 * c0)  * invDet
        val tp21 = (-this.t00 * c4 + this.t01 * c2 - this.t03 * c0) * invDet
        val tp22 = (this.t30 * s4 - this.t31 * s2 + this.t33 * s0)  * invDet
        val tp23 = (-this.t20 * s4 + this.t21 * s2 - this.t23 * s0) * invDet

        val tp30 = (-this.t10 * c3 + this.t11 * c1 - this.t12 * c0) * invDet
        val tp31 = (this.t00 * c3 - this.t01 * c1 + this.t02 * c0)  * invDet
        val tp32 = (-this.t30 * s3 + this.t31 * s1 - this.t32 * s0) * invDet
        val tp33 = (this.t20 * s3 - this.t21 * s1 + this.t22 * s0)  * invDet

        this.t00 = tp00; this.t01 = tp01; this.t02 = tp02; this.t03 = tp03
        this.t10 = tp10; this.t11 = tp11; this.t12 = tp12; this.t13 = tp13
        this.t20 = tp20; this.t21 = tp21; this.t22 = tp22; this.t23 = tp23
        this.t30 = tp30; this.t31 = tp31; this.t32 = tp32; this.t33 = tp33

        return this
    }

    operator fun minus(mat: Matrix4): Matrix4 =
        Matrix4(
            this.t00 - mat.t00, this.t01 - mat.t01,
            this.t02 - mat.t02, this.t03 - mat.t03,
            this.t10 - mat.t10, this.t11 - mat.t11,
            this.t12 - mat.t12, this.t13 - mat.t13,
            this.t20 - mat.t20, this.t21 - mat.t21,
            this.t22 - mat.t22, this.t23 - mat.t23,
            this.t30 - mat.t30, this.t31 - mat.t31,
            this.t32 - mat.t32, this.t33 - mat.t33
        )

    /**
     * Subtracts this matrix from the specified one and also modifies this
     * matrix as a result.
     *
     * @param mat
     *        The matrix to subtract.
     * @return A reference to this matrix for easy chaining.
     */
    fun minusSelf(mat: Matrix4): Matrix4 {
        this.t00 -= mat.t00; this.t01 -= mat.t01
        this.t02 -= mat.t02; this.t03 -= mat.t03
        this.t10 -= mat.t10; this.t11 -= mat.t11
        this.t12 -= mat.t12; this.t13 -= mat.t13
        this.t20 -= mat.t20; this.t21 -= mat.t21
        this.t22 -= mat.t22; this.t23 -= mat.t23
        this.t30 -= mat.t30; this.t31 -= mat.t31
        this.t32 -= mat.t32; this.t33 -= mat.t33
        return this
    }

    operator fun plus(mat: Matrix4): Matrix4 =
        Matrix4(
            this.t00 + mat.t00, this.t01 + mat.t01,
            this.t02 + mat.t02, this.t03 + mat.t03,
            this.t10 + mat.t10, this.t11 + mat.t11,
            this.t12 + mat.t12, this.t13 + mat.t13,
            this.t20 + mat.t20, this.t21 + mat.t21,
            this.t22 + mat.t22, this.t23 + mat.t23,
            this.t30 + mat.t30, this.t31 + mat.t31,
            this.t32 + mat.t32, this.t33 + mat.t33
        )

    /**
     * Adds this matrix to the specified one and also modifies this matrix as
     * a result.
     *
     * @param mat
     *        The matrix to add.
     * @return A reference to this matrix for easy chaining.
     */
    fun plusSelf(mat: Matrix4): Matrix4 {
        this.t00 += mat.t00; this.t01 += mat.t01
        this.t02 += mat.t02; this.t03 += mat.t03
        this.t10 += mat.t10; this.t11 += mat.t11
        this.t12 += mat.t12; this.t13 += mat.t13
        this.t20 += mat.t20; this.t21 += mat.t21
        this.t22 += mat.t22; this.t23 += mat.t23
        this.t30 += mat.t30; this.t31 += mat.t31
        this.t32 += mat.t32; this.t33 += mat.t33
        return this
    }

    override fun set(t00: Float, t01: Float, t02: Float, t03: Float,
                     t10: Float, t11: Float, t12: Float, t13: Float,
                     t20: Float, t21: Float, t22: Float, t23: Float,
                     t30: Float, t31: Float, t32: Float, t33: Float): Matrix4 {
        this.t00 = t00; this.t01 = t01; this.t02 = t02; this.t03 = t03
        this.t10 = t10; this.t11 = t11; this.t12 = t12; this.t13 = t13
        this.t20 = t20; this.t21 = t21; this.t22 = t22; this.t23 = t23
        this.t30 = t30; this.t31 = t31; this.t32 = t32; this.t33 = t33
        return this
    }

    override fun set(table: Table4?): Matrix4 {
        this.t00 = table!!.t00; this.t01 = table.t01
        this.t02 = table.t02; this.t03 = table.t03
        this.t10 = table.t10; this.t11 = table.t11
        this.t12 = table.t12; this.t13 = table.t13
        this.t20 = table.t20; this.t21 = table.t21
        this.t22 = table.t22; this.t23 = table.t23
        this.t30 = table.t30; this.t31 = table.t31
        this.t32 = table.t32; this.t33 = table.t33
        return this
    }

    operator fun times(scalar: Float): Matrix4 =
        Matrix4(
            this.t00 * scalar, this.t01 * scalar,
            this.t02 * scalar, this.t03 * scalar,
            this.t10 * scalar, this.t11 * scalar,
            this.t12 * scalar, this.t13 * scalar,
            this.t20 * scalar, this.t21 * scalar,
            this.t22 * scalar, this.t23 * scalar,
            this.t30 * scalar, this.t31 * scalar,
            this.t32 * scalar, this.t33 * scalar
        )

    operator fun times(mat: Matrix4): Matrix4 =
        Matrix4(
            this.t00 * mat.t00 + this.t01 * mat.t10 +
            this.t02 * mat.t20 + this.t03 * mat.t30,
            this.t00 * mat.t01 + this.t01 * mat.t11 +
            this.t02 * mat.t21 + this.t03 * mat.t31,
            this.t00 * mat.t02 + this.t01 * mat.t12 +
            this.t02 * mat.t22 + this.t03 * mat.t32,
            this.t00 * mat.t03 + this.t01 * mat.t13 +
            this.t02 * mat.t23 + this.t03 * mat.t33,
            this.t10 * mat.t00 + this.t11 * mat.t10 +
            this.t12 * mat.t20 + this.t13 * mat.t30,
            this.t10 * mat.t01 + this.t11 * mat.t11 +
            this.t12 * mat.t21 + this.t13 * mat.t31,
            this.t10 * mat.t02 + this.t11 * mat.t12 +
            this.t12 * mat.t22 + this.t13 * mat.t32,
            this.t10 * mat.t03 + this.t11 * mat.t13 +
            this.t12 * mat.t23 + this.t13 * mat.t33,
            this.t20 * mat.t00 + this.t21 * mat.t10 +
            this.t22 * mat.t20 + this.t23 * mat.t30,
            this.t20 * mat.t01 + this.t21 * mat.t11 +
            this.t22 * mat.t21 + this.t23 * mat.t31,
            this.t20 * mat.t02 + this.t21 * mat.t12 +
            this.t22 * mat.t22 + this.t23 * mat.t32,
            this.t20 * mat.t03 + this.t21 * mat.t13 +
            this.t22 * mat.t23 + this.t23 * mat.t33,
            this.t30 * mat.t00 + this.t31 * mat.t10 +
            this.t32 * mat.t20 + this.t33 * mat.t30,
            this.t30 * mat.t01 + this.t31 * mat.t11 +
            this.t32 * mat.t21 + this.t33 * mat.t31,
            this.t30 * mat.t02 + this.t31 * mat.t12 +
            this.t32 * mat.t22 + this.t33 * mat.t32,
            this.t30 * mat.t03 + this.t31 * mat.t13 +
            this.t32 * mat.t23 + this.t33 * mat.t33
        )

    /**
     * Multiplies this matrix by the specified scalar and also modifies this
     * matrix as a result.
     *
     * @param scalar
     *        The value to use.
     * @return A reference to this matrix for easy chaining.
     */
    fun timesSelf(scalar: Float): Matrix4 {
        this.t00 *= scalar; this.t01 *= scalar
        this.t02 *= scalar; this.t03 *= scalar
        this.t10 *= scalar; this.t11 *= scalar
        this.t12 *= scalar; this.t13 *= scalar
        this.t20 *= scalar; this.t21 *= scalar
        this.t22 *= scalar; this.t23 *= scalar
        this.t30 *= scalar; this.t31 *= scalar
        this.t32 *= scalar; this.t33 *= scalar
        return this
    }

    /**
     * Multiplies this matrix by the specified one and also modifies this
     * matrix as a result.
     *
     * @param mat
     *        The matrix to multiply.
     * @return A reference to this matrix for easy chaining.
     */
    fun timesSelf(mat: Matrix4): Matrix4 {
        val tp00 = this.t00 * mat.t00 + this.t01 * mat.t10 +
                   this.t02 * mat.t20 + this.t03 * mat.t30
        val tp01 = this.t00 * mat.t01 + this.t01 * mat.t11 +
                   this.t02 * mat.t21 + this.t03 * mat.t31
        val tp02 = this.t00 * mat.t02 + this.t01 * mat.t12 +
                   this.t02 * mat.t22 + this.t03 * mat.t32
        val tp03 = this.t00 * mat.t03 + this.t01 * mat.t13 +
                   this.t02 * mat.t23 + this.t03 * mat.t33
        val tp10 = this.t10 * mat.t00 + this.t11 * mat.t10 +
                   this.t12 * mat.t20 + this.t13 * mat.t30
        val tp11 = this.t10 * mat.t01 + this.t11 * mat.t11 +
                   this.t12 * mat.t21 + this.t13 * mat.t31
        val tp12 = this.t10 * mat.t02 + this.t11 * mat.t12 +
                   this.t12 * mat.t22 + this.t13 * mat.t32
        val tp13 = this.t10 * mat.t03 + this.t11 * mat.t13 +
                   this.t12 * mat.t23 + this.t13 * mat.t33
        val tp20 = this.t20 * mat.t00 + this.t21 * mat.t10 +
                   this.t22 * mat.t20 + this.t23 * mat.t30
        val tp21 = this.t20 * mat.t01 + this.t21 * mat.t11 +
                   this.t22 * mat.t21 + this.t23 * mat.t31
        val tp22 = this.t20 * mat.t02 + this.t21 * mat.t12 +
                   this.t22 * mat.t22 + this.t23 * mat.t32
        val tp23 = this.t20 * mat.t03 + this.t21 * mat.t13 +
                   this.t22 * mat.t23 + this.t23 * mat.t33
        val tp30 = this.t30 * mat.t00 + this.t31 * mat.t10 +
                   this.t32 * mat.t20 + this.t33 * mat.t30
        val tp31 = this.t30 * mat.t01 + this.t31 * mat.t11 +
                   this.t32 * mat.t21 + this.t33 * mat.t31
        val tp32 = this.t30 * mat.t02 + this.t31 * mat.t12 +
                   this.t32 * mat.t22 + this.t33 * mat.t32
        val tp33 = this.t30 * mat.t03 + this.t31 * mat.t13 +
                   this.t32 * mat.t23 + this.t33 * mat.t33

        this.t00 = tp00; this.t01 = tp01; this.t02 = tp02; this.t03 = tp03
        this.t10 = tp10; this.t11 = tp11; this.t12 = tp12; this.t13 = tp13
        this.t20 = tp20; this.t21 = tp21; this.t22 = tp22; this.t23 = tp23
        this.t30 = tp30; this.t31 = tp31; this.t32 = tp32; this.t33 = tp33

        return this
    }

    override fun toString(): String =
        this.toArray().joinToString(",", "[", "]")

    /**
     * Computes the transpose of this matrix.
     *
     * @return The transpose.
     */
    fun transpose(): Matrix4 =
        Matrix4(
            this.t00, this.t10, this.t20, this.t30,
            this.t01, this.t11, this.t21, this.t31,
            this.t02, this.t12, this.t22, this.t32,
            this.t03, this.t13, this.t23, this.t33
               )

    /**
     * Computes the transpose of this matrix and also modifies this matrix as
     * a result.
     *
     * @return A reference to this matrix for easy chaining.]
     */
    fun transposeSelf(): Matrix4 {
        val tp01 = this.t10
        val tp02 = this.t20
        val tp03 = this.t30
        val tp10 = this.t01
        val tp12 = this.t21
        val tp13 = this.t31
        val tp20 = this.t02
        val tp21 = this.t12
        val tp23 = this.t32
        val tp30 = this.t03
        val tp31 = this.t13
        val tp32 = this.t23

        this.t01 = tp01
        this.t02 = tp02
        this.t03 = tp03
        this.t10 = tp10
        this.t12 = tp12
        this.t13 = tp13
        this.t20 = tp20
        this.t21 = tp21
        this.t23 = tp23
        this.t30 = tp30
        this.t31 = tp31
        this.t32 = tp32

        return this
    }
}
