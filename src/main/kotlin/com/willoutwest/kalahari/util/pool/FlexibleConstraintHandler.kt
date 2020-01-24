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

    override fun handleViolation(pool: ObjectPool<*>) {
        pool.capacity = pool.capacity + this.increment +
                        (pool.capacity * this.scale).toInt()
    }
}
