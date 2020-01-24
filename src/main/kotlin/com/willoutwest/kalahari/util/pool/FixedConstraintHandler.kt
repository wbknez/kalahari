package com.willoutwest.kalahari.util.pool

/**
 * Represents an implementation of [ConstraintHandler] that always throws an
 * exception when a constraint violation occurs, effectively enforcing a
 * fixed-size constraint on an object pool.
 */
class FixedConstraintHandler : ConstraintHandler {

    override fun handleViolation(pool: ObjectPool<*>) {
        throw EmptyPoolException(
            "The object pool is empty and has no objects to borrow."
        )
    }
}