package com.willoutwest.kalahari.util.pool

/**
 * An implementation of [ConstraintHandler] that expands the capacity of an
 * [ObjectPool] by a given amount during overflow.
 *
 * @property increment
 *           The number of elements to linearly increase capacity by.
 * @property scale
 *           The percentage of original capacity to increase by.
 */
class FlexibleConstraintHandler(val increment: Int = 0,
                                val scale: Float = 2f) : ConstraintHandler {

    init {
        require(this.increment >= 0) {
            "Flexible increment must be greater than or equal to zero."
        }

        require(this.scale >= 0f) {
            "Flexible scale must be greater than or equal to zero."
        }

        require((this.increment != 0) || (this.scale != 0f)) {
            "Flexible constraint handler must have at least one of non-zero " +
            "increment or scale."
        }
    }

    override fun handleViolation(pool: ObjectPool<*>) {
        val computed = pool.capacity + this.increment +
                       (pool.capacity * this.scale).toInt()

        pool.capacity = when(computed != pool.capacity) {
            false -> pool.capacity + 1
            true  -> computed
        }
    }

    override fun toString(): String = "(inc: ${this.increment}, " +
                                      "scale: ${this.scale})"
}
