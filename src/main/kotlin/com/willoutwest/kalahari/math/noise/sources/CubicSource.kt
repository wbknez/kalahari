package com.willoutwest.kalahari.math.noise.sources

import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.RandomUtils
import com.willoutwest.kalahari.math.noise.NoiseSource

/**
 * Represents an implementation of [NoiseSource] that uses cubic
 * interpolation on random noise mapped to an integer lattice.
 *
 * @property mask
 *           The hashing mask.
 */
class CubicSource() :
    AbstractLatticeSource(TableSize, TableSize), Cloneable, NoiseSource {

    companion object {

        /**
         * The default permutation table.
         *
         * This is taken from the following source written by Darwyn Peachy:
         *  Ebert, D.S., Musgrave, K., and Peachy, D. (2003). Texturing and
         *  Modeling, Third Edition: A Procedural Approach. pp. 70.
         *  ISBN: 978-1558608481
         */
        val Indices         = shortArrayOf(
            225,155,210,108,175,199,221,144,203,116, 70,213, 69,158, 33,252,
            5, 82,173,133,222,139,174, 27,  9, 71, 90,246, 75,130, 91,191,
            169,138,  2,151,194,235, 81,  7, 25,113,228,159,205,253,134,142,
            248, 65,224,217, 22,121,229, 63, 89,103, 96,104,156, 17,201,129,
            36,  8,165,110,237,117,231, 56,132,211,152, 20,181,111,239,218,
            170,163, 51,172,157, 47, 80,212,176,250, 87, 49, 99,242,136,189,
            162,115, 44, 43,124, 94,150, 16,141,247, 32, 10,198,223,255, 72,
            53,131, 84, 57,220,197, 58, 50,208, 11,241, 28,  3,192, 62,202,
            18,215,153, 24, 76, 41, 15,179, 39, 46, 55,  6,128,167, 23,188,
            106, 34,187,140,164, 73,112,182,244,195,227, 13, 35, 77,196,185,
            26,200,226,119, 31,123,168,125,249, 68,183,230,177,135,160,180,
            12,  1,243,148,102,166, 38,238,251, 37,240,126, 64, 74,161, 40,
            184,149,171,178,101, 66, 29, 59,146, 61,254,107, 42, 86,154,  4,
            236,232,120, 21,233,209, 45, 98,193,114, 78, 19,206, 14,118,127,
            48, 79,147, 85, 30,207,219, 54, 88,234,190,122, 95, 67,143,109,
            137,214,145, 93, 92,100,245,  0,216,186, 60, 83,105, 97,204, 52
        )

        /**
         * The default number of elements in a table.
         */
        const val TableSize = 256
    }

    private val mask = this.indices.size - 1

    constructor(source: CubicSource?) : this() {
        source!!.indices.copyInto(this.indices)
        source.values.copyInto(this.values)
    }

    init {
        Indices.copyInto(this.indices)

        for(i in this.values.indices) {
            this.values[i] = 1f - 2f * RandomUtils.nextFloat()
        }
    }

    override fun clone(): CubicSource = CubicSource(this)

    /**
     * Computes the index on the lattice of this cubic source from which to
     * provide noise.
     *
     * @param x
     *        The x-axis component to use.
     * @param y
     *        The y-axis component to use.
     * @param z
     *        The z-axis component to use.
     * @return A permuted index.
     */
    fun indexOf(x: Int, y: Int, z: Int): Short =
        this.permute(x + this.permute(y + this.permute(z)))

    /**
     * Hashes the specified index with the mask of this cubic source,
     * forcing it to be within the bounds of
     * <code>[[0, table size - 1]]</code>.
     *
     * @param index
     *        The index to permute.
     * @return A permuted index.
     */
    fun permute(index: Int): Short = this.indices[index and this.mask]

    override fun output(x: Float, y: Float, z: Float): Float {
        val cache  = ComputeUtils.localCache

        val xKnots = cache.arrays.borrow()
        val yKnots = cache.arrays.borrow()
        val zKnots = cache.arrays.borrow()

        val iX = MathUtils.floori(x)
        val iY = MathUtils.floori(y)
        val iZ = MathUtils.floori(z)

        val fX = x - iX
        val fY = y - iY
        val fZ = z - iZ

        for(k in -1..2) {
            for(j in -1..2) {
                for(i in -1..2) {
                    val index = this.indexOf(iX + i, iY + j, iZ + k)

                    xKnots[i + 1] = this.values[index.toInt()]
                }

                yKnots[j + 1] = MathUtils.fourKnotSpline(fX, xKnots)
            }

            zKnots[k + 1] = MathUtils.fourKnotSpline(fY, yKnots)
        }

        cache.arrays.reuse(xKnots, yKnots, zKnots)

        return MathUtils.clamp(MathUtils.fourKnotSpline(fZ, zKnots), -1f, 1f)
    }
}