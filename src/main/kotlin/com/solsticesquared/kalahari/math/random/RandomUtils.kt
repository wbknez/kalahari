package com.solsticesquared.kalahari.math.random

import com.solsticesquared.kalahari.util.storage.ThreadLocalStorage
import java.util.Random

/**
 * Represents a collection of utility methods for generating pseudo-random
 * numbers.
 *
 * @property localRandom
 *           The thread-specific random number generator.
 */
sealed class RandomUtils {

    companion object {

        /**
         * The collection of thread-local pseudo-random number generators.
         */
        private val randoms = ThreadLocalStorage({ MersenneTwister() })

        val localRandom: Random
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
        fun nextDouble(): Double = randoms.get().nextDouble()

        /**
         * Computes a pseudo-random number between zero (inclusive) and the
         * specified end value (exclusive).
         *
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as a double.
         */
        fun nextDouble(b: Double) = randoms.get().nextDouble() * b

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
            randoms.get().nextDouble() * (b - a) + a

        /**
         * Computes a pseudo-random number between zero (inclusive) and one
         * (exclusive).
         *
         * @return A pseudo-random number as a float.
         */
        fun nextFloat(): Float = randoms.get().nextFloat()

        /**
         * Computes a pseudo-random number between zero (inclusive) and the
         * specified end value (exclusive).
         *
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as a float.
         */
        fun nextFloat(b: Float): Float = randoms.get().nextFloat() * b

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
        fun nextFloat(a: Float, b: Float): Float =
            randoms.get().nextFloat() * (b - a) + a

        /**
         * Computes a pseudo-random number between zero (inclusive) and the
         * specified end value (exclusive).
         *
         * @param b
         *        The end value to use.
         * @return A pseudo-random number as an integer.
         */
        fun nextInt(b: Int) = randoms.get().nextInt(b)

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
        fun nextInt(a: Int, b: Int) = randoms.get().nextInt(b - a) + a
    }
}
