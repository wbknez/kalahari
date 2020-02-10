package com.willoutwest.kalahari.util

import java.util.SplittableRandom

/**
 * Applies the Fisher-Yates shuffling algorithm to this mutable list,
 * resulting in a statistically random ordering of elements.
 *
 * @param random
 *        The pseudo-random number generator to use.
 */
fun <T> MutableList<T>.fisherShuffle(random: SplittableRandom) {
    for(i in this.size - 1 downTo 1) {
        val choice = random.nextInt(i + 1)

        this[i] = this[choice].also { this[choice] = this[i] }
    }
}