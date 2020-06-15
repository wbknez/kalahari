package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.storage.ThreadLocalStorage
import java.util.SplittableRandom
import kotlin.math.nextDown

/**
 * Represents a collection of utility methods for generating pseudo-random
 * numbers.
 *
 * @property base
 *           The pseudo-random number generator to derive new
 *           generators with similar statistical properties from.
 * @property localRandom
 *           A thread-specific random number generator.
 * @property randoms
 *           The cache of thread-local pseudo-random numebr generators.
 */
sealed class RandomUtils {

    companion object {

        private var base    = SplittableRandom(System.currentTimeMillis())

        private val randoms = ThreadLocalStorage({ base.split() })

        val localRandom: SplittableRandom
            get() = randoms.get()

        /**
         * Clears all thread-local pseudo-random number generators that have
         * been created.
         */
        fun clearLocalRandoms() {
            randoms.clear()
        }

        /**
         * Computes a pseudo-random number between zero (inclusive) and one
         * (exclusive).
         *
         * @return A pseudo-random number as a double.
         */
        fun nextDouble(): Double = localRandom.nextDouble()

        /**
         * Computes a pseudo-random number between zero (inclusive) and the
         * specified end value (exclusive).
         *
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as a double.
         */
        fun nextDouble(b: Double) = localRandom.nextDouble(b)

        /**
         * Computes a pseudo-random number between the specified start value
         * (inclusive) and the specified end value (exclusive).
         *
         * @param a
         *        The start value to use.
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as a double.
         */
        fun nextDouble(a: Double, b: Double): Double =
            localRandom.nextDouble(a, b)

        /**
         * Computes a pseudo-random number between zero (inclusive) and one
         * (exclusive).
         *
         * @return A pseudo-random number as a float.
         */
        fun nextFloat(): Float = nextDouble().toFloat()

        /**
         * Computes a pseudo-random number between zero (inclusive) and the
         * specified end value (exclusive).
         *
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as a float.
         */
        fun nextFloat(b: Float): Float {
            val x = nextDouble(b.toDouble()).toFloat()

            return when(x != b) {
                false -> x.nextDown()
                true  -> x
            }
        }

        /**
         * Computes a pseudo-random number between the specified start value
         * (inclusive) and the specified end value (exclusive).
         *
         * @param a
         *        The start value to use.
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as a float.
         */
        fun nextFloat(a: Float, b: Float): Float {
            val x = nextDouble(a.toDouble(), b.toDouble()).toFloat()

            return when(x != b) {
                false -> x.nextDown()
                true  -> x
            }
        }

        /**
         * Computes a pseudo-random number between the minimum and maximum 
         * possible integer values, inclusive.
         * 
         * @return A pseudo-random number as an integer.
         */
        fun nextInt(): Int = localRandom.nextInt()

        /**
         * Computes a pseudo-random number between zero (inclusive) and the
         * specified end value (exclusive).
         *
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as an integer.
         */
        fun nextInt(b: Int) = localRandom.nextInt(b)

        /**
         * Computes a pseudo-random number between the specified start value
         * (inclusive) and the specified end value (exclusive).
         *
         * @param a
         *        The start value to use.
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as an integer.
         */
        fun nextInt(a: Int, b: Int) = localRandom.nextInt(a, b)

        /**
         * Creates a new parent pseudo-random number generator using the 
         * specified seed.
         * 
         * @param seed
         *        The seed to use.
         */
        fun setSeed(seed: Long) {
            base = SplittableRandom(seed)
        }
    }
}