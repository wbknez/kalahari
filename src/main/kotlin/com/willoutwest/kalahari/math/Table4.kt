package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.hash
import java.lang.IllegalArgumentException

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
open class Table4(@JvmField var t00: Float = 0f, @JvmField var t01: Float = 0f,
                  @JvmField var t02: Float = 0f, @JvmField var t03: Float = 0f,
                  @JvmField var t10: Float = 0f, @JvmField var t11: Float = 0f,
                  @JvmField var t12: Float = 0f, @JvmField var t13: Float = 0f,
                  @JvmField var t20: Float = 0f, @JvmField var t21: Float = 0f,
                  @JvmField var t22: Float = 0f, @JvmField var t23: Float = 0f,
                  @JvmField var t30: Float = 0f, @JvmField var t31: Float = 0f,
                  @JvmField var t32: Float = 0f, @JvmField var t33: Float = 0f)
    : Cloneable {

    /**
     * Constructor.
     *
     * @param array
     *        The array to copy from.
     */
    constructor(array: FloatArray)
        : this(array[0],  array[1],  array[2],  array[3],
               array[4],  array[5],  array[6],  array[7],
               array[8],  array[9],  array[10], array[11],
               array[12], array[13], array[14], array[15])

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

    /**
     * Returns the column at the specified index in this table as an array of
     * floating-point values.
     *
     * @return An array of column values.
     */
    fun column(index: Int): FloatArray =
        when(index) {
            0    -> floatArrayOf(this.t00, this.t10, this.t20, this.t30)
            1    -> floatArrayOf(this.t01, this.t11, this.t21, this.t31)
            2    -> floatArrayOf(this.t02, this.t12, this.t22, this.t32)
            3    -> floatArrayOf(this.t03, this.t13, this.t23, this.t33)
            else -> throw IndexOutOfBoundsException()
        }

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

    /**
     * Returns the row at the specified index in this table as an array of
     * floating-point values.
     *
     * @return An array of row values.
     */
    fun row(index: Int): FloatArray =
        when(index) {
            0    -> floatArrayOf(this.t00, this.t01, this.t02, this.t03)
            1    -> floatArrayOf(this.t10, this.t11, this.t12, this.t13)
            2    -> floatArrayOf(this.t20, this.t21, this.t22, this.t23)
            3    -> floatArrayOf(this.t30, this.t31, this.t32, this.t33)
            else -> throw java.lang.IndexOutOfBoundsException("")
        }

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
     * Sets the array of this table to the specified values.
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
     * Sets the array of this table to those of the specified array.
     *
     * @param array
     *        The array to copy from.
     * @return A reference to this table for easy chaining.
     */
    open fun set(array: FloatArray): Table4 =
        this.set(array[0],  array[1],  array[2],  array[3],
                 array[4],  array[5],  array[6],  array[7],
                 array[8],  array[9],  array[10], array[11],
                 array[12], array[13], array[14], array[15])

    /**
    * Sets the array of this table to those of the specified one.
    *
    * @param table
    *        The table to copy from.
    * @return A reference to this table for easy chaining.
    */
    open fun set(table: Table4?): Table4 =
        this.set(table!!.t00, table.t01, table.t02, table.t03,
                 table.t10,   table.t11, table.t12, table.t13,
                 table.t20,   table.t21, table.t22, table.t23,
                 table.t30,   table.t31, table.t32, table.t33)

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