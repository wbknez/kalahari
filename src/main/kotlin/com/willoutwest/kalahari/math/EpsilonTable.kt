package com.willoutwest.kalahari.math

/**
 * Represents a collection of parametric epsilon values that denote when
 * an intersection between a ray and an arbitrary geometric surface is
 * successful.
 *
 * Epsilon values are associated with a name, where this name represents the
 * type of geometric surface used during a specific intersection test.  If a
 * mapping between a geometric surface and an epsilon does not exist, then a
 * default epsilon value will be used instead.
 *
 * @property defaultEpsilon
 *           The epsilon value to use if a specific mapping cannot be found.
 * @property table
 *           The mapping of names to epsilon values.
 */
data class EpsilonTable(val defaultEpsilon: Float,
                        private val table: MutableMap<String, Float>)
    : Cloneable {

    /**
     * Constructor.
     *
     * @param epsilons
     *        The epsilon table to copy from.
     */
    constructor(epsilons: EpsilonTable?)
        : this(epsilons!!.defaultEpsilon, epsilons.table)

    public override fun clone(): EpsilonTable = EpsilonTable(this)

    operator fun get(name: String): Float =
        this.table.getOrDefault(name, this.defaultEpsilon)

    operator fun set(name: String, epsilon: Float) {
        this.table[name] = epsilon
    }
}