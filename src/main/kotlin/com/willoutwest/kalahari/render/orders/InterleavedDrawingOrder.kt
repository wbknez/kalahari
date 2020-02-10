package com.willoutwest.kalahari.render.orders

import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.render.DrawingOrder

/**
 * Represents an implementation of [DrawingOrder] that organizes pixel
 * coordinates in an interleaved order by alternating x- and y-axis coordinates
 * using a parity-based pattern.
 *
 * @property xParity
 *           The x-axis filter.
 * @property yParity
 *           The y-axis filter.
 */
class InterleavedDrawingOrder(val xParity: Parity = Parity.Even,
                              val yParity: Parity = Parity.Any) :
    DrawingOrder {

    /**
     * Represents a collection of different axis traversal criteria that are used
     * for constructing sequences of pixel coordinates
     *
     * @property filter
     *           The function to determine whether or not a coordinate
     *           should be accepted.
     */
    enum class Parity {

        /**
         * Represents a filter that accepts every coordinate it receives.
         */
        Any {
            override val filter: (Int) -> Boolean = { true }
        },

        /**
         * Represents a filter that accepts only even coordinates it receives.
         */
        Even {
            override val filter: (Int) -> Boolean = { it % 2 == 0 }
        },

        /**
         * Represents a filter that rejects every coordinate it receives.
         */
        None {
            override val filter: (Int) -> Boolean = { false }
        },

        /**
         * Represents a filter that accepts only odd coordinates it receives.
         */
        Odd {
            override val filter: (Int) -> Boolean = { it % 2 != 0 }
        }
        ;

        abstract val filter: (Int) -> Boolean
    }

    companion object {

        /**
         * The mapping of each parity to its operational opposite.
         */
        private val opposites = mapOf<Parity, Parity>(
            Parity.Any  to Parity.None,
            Parity.Even to Parity.Odd,
            Parity.None to Parity.Any,
            Parity.Odd  to Parity.Even
        )
    }

    override fun orderOf(bounds: Bounds): List<Coords> {
        val xOpposite = opposites[this.xParity]!!
        val yOpposite = opposites[this.yParity]!!

        val leaves = listOf<MutableList<Coords>>(
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )

        for(i in 0 until bounds.area) {
            val x = bounds.x + (i % bounds.width)
            val y = bounds.y + (i / bounds.width)

            val index = when {
                match(xParity,   yParity,   x, y) -> 0
                match(xOpposite, yOpposite, x, y) -> 1
                match(xParity,   yOpposite, x, y) -> 2
                match(xOpposite, yParity,   x, y) -> 3
                else -> throw IllegalStateException(
                    "Coordinate was not filtered: $x, $y."
                )
            }

            leaves[index].add(Coords(x, y))
        }

        return leaves.flatten()
    }

    /**
     * Determines whether or not the specified x- and y-axis coordinates meet
     * the specified filtration conditions.
     *
     * @param xPar
     *        The x-axis parity condition to use.
     * @param yPar
     *        The y-axis parity condition to use.
     * @param x
     *        The x-axis coordinate to check.
     * @param y
     *        The y-axis coordinate to check.
     * @return Whether or not to filter a pair of coordinates.
     */
    private fun match(xPar: Parity, yPar: Parity, x: Int, y: Int): Boolean =
        xPar.filter(x) && yPar.filter(y)
}