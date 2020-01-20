package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.hash

/**
 * Represents a matrix-like mathematical object in four dimensional space.
 *
 * @property t00
 *           A table element.
 * @property t01
 *           A table element.
 * @property t02
 *           A table element.
 * @property t03
 *           A table element.
 * @property t10
 *           A table element.
 * @property t11
 *           A table element.
 * @property t12
 *           A table element.
 * @property t13
 *           A table element.
 * @property t20
 *           A table element.
 * @property t21
 *           A table element.
 * @property t22
 *           A table element.
 * @property t23
 *           A table element.
 * @property t30
 *           A table element.
 * @property t31
 *           A table element.
 * @property t32
 *           A table element.
 * @property t33
 *           A table element.
 */
open class Table4(var t00: Float = 0f, var t01: Float = 0f,
                  var t02: Float = 0f, var t03: Float = 0f,
                  var t10: Float = 0f, var t11: Float = 0f,
                  var t12: Float = 0f, var t13: Float = 0f,
                  var t20: Float = 0f, var t21: Float = 0f,
                  var t22: Float = 0f, var t23: Float = 0f,
                  var t30: Float = 0f, var t31: Float = 0f,
                  var t32: Float = 0f, var t33: Float = 0f) : Cloneable {

    /**
     * Constructor.
     *
     * @param elements
     *        The array to copy from.
     */
    constructor(elements: FloatArray)
        : this(elements[0],  elements[1],  elements[2],  elements[3],
               elements[4],  elements[5],  elements[6],  elements[7],
               elements[8],  elements[9],  elements[10], elements[11],
               elements[12], elements[13], elements[14], elements[15])

    /**
     * Constructor.
     *
     * @param table
     *        The table to copy from.
     */
    constructor(table: Table4?)
        : this(table!!.t00, table.t01, table.t02, table.t03,
               table.t10,   table.t11, table.t12, table.t13,
               table.t20,   table.t21, table.t22, table.t23,
               table.t30,   table.t31, table.t32, table.t33)

    public override fun clone(): Table4 = Table4(this)

    operator fun component1(): Float  = this.t00
    operator fun component2(): Float  = this.t01
    operator fun component3(): Float  = this.t02
    operator fun component4(): Float  = this.t03
    operator fun component5(): Float  = this.t10
    operator fun component6(): Float  = this.t11
    operator fun component7(): Float  = this.t12
    operator fun component8(): Float  = this.t13
    operator fun component9(): Float = this.t20
    operator fun component10(): Float = this.t21
    operator fun component11(): Float = this.t22
    operator fun component12(): Float = this.t23
    operator fun component13(): Float = this.t30
    operator fun component14(): Float = this.t31
    operator fun component15(): Float = this.t32
    operator fun component16(): Float = this.t33

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Table4 -> this.t00 == other.t00 && this.t01 == other.t01 &&
                         this.t02 == other.t02 && this.t03 == other.t03 &&
                         this.t10 == other.t10 && this.t11 == other.t11 &&
                         this.t12 == other.t12 && this.t13 == other.t13 &&
                         this.t20 == other.t20 && this.t21 == other.t21 &&
                         this.t22 == other.t22 && this.t23 == other.t23 &&
                         this.t30 == other.t30 && this.t31 == other.t31 &&
                         this.t32 == other.t32 && this.t33 == other.t33
            else      -> false
        }

    operator fun get(index: Int): Float =
        when(index) {
            0    -> this.t00
            1    -> this.t01
            2    -> this.t02
            3    -> this.t03
            4    -> this.t10
            5    -> this.t11
            6    -> this.t12
            7    -> this.t13
            8    -> this.t20
            9    -> this.t21
            10   -> this.t22
            11   -> this.t23
            12   -> this.t30
            13   -> this.t31
            14   -> this.t32
            15   -> this.t33
            else -> throw IndexOutOfBoundsException("Invalid table index: " +
                                                    "${index}.")
        }

    override fun hashCode(): Int =
        hash(this.t00, this.t01, this.t02, this.t03,
             this.t10, this.t11, this.t12, this.t13,
             this.t20, this.t21, this.t22, this.t23,
             this.t30, this.t31, this.t32, this.t33)

    operator fun set(index: Int, value: Float) =
        when(index) {
            0    -> this.t00 = value
            1    -> this.t01 = value
            2    -> this.t02 = value
            3    -> this.t03 = value
            4    -> this.t10 = value
            5    -> this.t11 = value
            6    -> this.t12 = value
            7    -> this.t13 = value
            8    -> this.t20 = value
            9    -> this.t21 = value
            10   -> this.t22 = value
            11   -> this.t23 = value
            12   -> this.t30 = value
            13   -> this.t31 = value
            14   -> this.t32 = value
            15   -> this.t33 = value
            else -> throw IndexOutOfBoundsException("Invalid table index: " +
                                                    "${index}.")
        }

    /**
     * Sets the elements of this table to the specified values.
     *
     * @param t00
     *        A table element to use.
     * @param t01
     *        A table element to use.
     * @param t02
     *        A table element to use.
     * @param t03
     *        A table element to use.
     * @param t10
     *        A table element to use.
     * @param t11
     *        A table element to use.
     * @param t12
     *        A table element to use.
     * @param t13
     *        A table element to use.
     * @param t20
     *        A table element to use.
     * @param t21
     *        A table element to use.
     * @param t22
     *        A table element to use.
     * @param t23
     *        A table element to use.
     * @param t30
     *        A table element to use.
     * @param t31
     *        A table element to use.
     * @param t32
     *        A table element to use.
     * @param t33
     *        A table element to use.
     * @return A reference to this table for easy chaining.
     */
    open fun set(t00: Float, t01: Float, t02: Float, t03: Float,
                 t10: Float, t11: Float, t12: Float, t13: Float,
                 t20: Float, t21: Float, t22: Float, t23: Float,
                 t30: Float, t31: Float, t32: Float, t33: Float): Table4 {
        this.t00 = t00; this.t01 = t01; this.t02 = t02; this.t03 = t03
        this.t10 = t10; this.t11 = t11; this.t12 = t12; this.t13 = t13
        this.t20 = t20; this.t21 = t21; this.t22 = t22; this.t23 = t23
        this.t30 = t30; this.t31 = t31; this.t32 = t32; this.t33 = t33

        return this
    }

    /**
     * Sets the elements of this table to those of the specified array.
     *
     * @param elements
     *        The array to copy from.
     * @return A reference to this table for easy chaining.
     */
    fun set(elements: FloatArray): Table4 {
        this.t00 = elements[0];  this.t01 = elements[1]
        this.t02 = elements[2];  this.t03 = elements[3]
        this.t10 = elements[4];  this.t11 = elements[5]
        this.t12 = elements[6];  this.t13 = elements[7]
        this.t20 = elements[8];  this.t21 = elements[9]
        this.t22 = elements[10]; this.t23 = elements[11]
        this.t30 = elements[12]; this.t31 = elements[13]
        this.t32 = elements[14]; this.t33 = elements[15]

        return this
    }

    /**
    * Sets the elements of this table to those of the specified one.
    *
    * @param table
    *        The table to copy from.
    * @return A reference to this table for easy chaining.
    */
    open fun set(table: Table4?): Table4 {
        this.t00 = table!!.t00; this.t01 = table.t01
        this.t02 = table.t02;   this.t03 = table.t03
        this.t10 = table.t10;   this.t11 = table.t11
        this.t12 = table.t12;   this.t13 = table.t13
        this.t20 = table.t20;   this.t21 = table.t21
        this.t22 = table.t22;   this.t23 = table.t23
        this.t30 = table.t30;   this.t31 = table.t31
        this.t32 = table.t32;   this.t33 = table.t33

        return this
    }

    /**
    * Converts this table to a sixteen element row-major floating-point array.
    *
    * @return A table as an array.
    */
    fun toArray(): FloatArray =
        floatArrayOf(this.t00, this.t01, this.t02, this.t03,
                     this.t10, this.t11, this.t12, this.t13,
                     this.t20, this.t21, this.t22, this.t23,
                     this.t30, this.t31, this.t32, this.t33)

    override fun toString(): String =
        this.toArray().joinToString(",", "(", ")")
}