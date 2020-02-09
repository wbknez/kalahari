package com.willoutwest.kalahari.util

import java.util.SplittableRandom

/**
 * Applies the Fisher-Yates shuffle to this mutable list, resulting in a
 * statistically random ordering of elements.
 *
 * @param random
 *        The pseudo-random number generator to use.
 */
fun <T> MutableList<T>.fisherShuffle(random: SplittableRandom) {
    for(i in 0 until this.size - 1) {
        val index = random.nextInt(i)

        this[i] = this[index].also { this[index] = this[i] }
    }
}