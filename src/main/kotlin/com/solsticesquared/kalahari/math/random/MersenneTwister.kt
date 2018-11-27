package com.solsticesquared.kalahari.math.random

import java.util.Random

/**
 * Represents an implementation of the Mersenne Twister pseudo-random number
 * generator as first described by Makoto Matsumoto and Takuji Nishimura.
 *
 * The original C implementation and copyright notice may be found here:
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/VERSIONS/C-LANG/980409/mt19937int.c
 *
 * @property mt
 *           The internal state array.
 * @property mti
 *           The index of the next state value to use.
 */
class MersenneTwister(seed: Long = System.currentTimeMillis())
    : Cloneable, Random(seed) {

    companion object {

        const val N           = 624
        const val M           = 397
        const val UpperMask   = 0x80000000.toInt()
        const val LowerMask   = 0x7fffffff

        val MagicMatrix       = intArrayOf(0x0, 0x9908b0df.toInt())
        const val Magic0      = 1812433253
        const val Magic1      = 1664525
        const val Magic2      = 1566083941
        const val Magic3      = 0x9d2c5680.toInt()
        const val Magic4      = 0xefc60000.toInt()
        const val BaseSeed    = 19650218
    }

    private val mt  = IntArray(N)

    private var mti = 0

    init {
        this.setSeed(seed)
    }

    public override fun clone(): MersenneTwister {
        val cloned = MersenneTwister()
        cloned.mti = this.mti
        System.arraycopy(this.mt, 0, cloned.mt, 0, this.mt.size)
        return cloned
    }

    override fun next(bits: Int): Int {
        var y: Int

        if(this.mti >= N) {
            for(k in 0..(N - M - 1)) {
                y = (this.mt[k] and UpperMask) or (this.mt[k + 1] and LowerMask)
                this.mt[k] = this.mt[k + M] xor (y shr 1) xor
                    MagicMatrix[y and 0x1]
            }

            for(k in (N - M)..(N - 2)) {
                y = (this.mt[k] and UpperMask) or (this.mt[k + 1] and LowerMask)
                this.mt[k] = this.mt[k + (M - N)] xor (y shr 1) xor
                    MagicMatrix[y and 0x1]
            }

            y = (this.mt[N - 1] and UpperMask) or (this.mt[0] and LowerMask)
            this.mt[N - 1] = this.mt[M - 1] xor (y ushr 1) xor
                MagicMatrix[y and 0x1]

            this.mti = 0
        }

        y = this.mt[this.mti++]

        y = y xor (y ushr 11)
        y = y xor ((y shl 7) and Magic3)
        y = y xor ((y shl 15) and Magic4)
        y = y xor (y ushr 18)

        return (y ushr (32 - bits))
    }

    /**
     * Sets the seed of this Mersenne Twister to the specified 32-bit value.
     *
     * @param seed
     *        The seed to use.
     */
    fun setSeed(seed: Int) {
        this.mt[0] = seed
        for(i in 1..(N - 1)) {
            this.mt[i] =
                (Magic0 * (this.mt[i - 1] xor (this.mt[i - 1] ushr 30)) + i)
        }
        this.mti = N
    }

    override fun setSeed(seed: Long) {
        try {
            this.setSeed(
                intArrayOf(seed.toInt(), (seed ushr 32).toInt())
                        )
        }
        catch(ignored: NullPointerException) {
            // This should only happen at creation due to how Kotlin
            // constructs objects (super class first).
        }
    }

    /**
     * Sets the seed of this Mersenne Twister to the specified value given as
     * an array.
     *
     * @param seed
     *        The seed array to use.
     */
    fun setSeed(seed: IntArray) {
        this.setSeed(BaseSeed)

        var i = 1
        var j = 0
        val start = when(N > seed.size) {
            false -> seed.size
            true  -> N
        }

        for(k in start downTo 1) {
            this.mt[i] = (this.mt[i] xor
                ((this.mt[i - 1] xor (this.mt[i - 1] ushr 30)) *
                 Magic1)) + seed[j] + j

            i++
            j++

            if(i >= N) {
                this.mt[0] = this.mt[N - 1]
                i = 1
            }

            if(j >= seed.size) {
                j = 0
            }
        }

        for(k in (N - 1) downTo 1) {
            this.mt[i] = (this.mt[i] xor
                ((this.mt[i - 1] xor (this.mt[i - 1] ushr 30)) *
                 Magic2)) - i

            i++

            if(i >= N) {
                this.mt[0] = this.mt[N - 1]
                i = 1
            }
        }

        this.mt[0] = UpperMask
    }
}
