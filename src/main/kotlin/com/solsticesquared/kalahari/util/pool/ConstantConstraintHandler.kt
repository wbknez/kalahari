package com.solsticesquared.kalahari.util.pool

/**
 * Represents an implementation of [ConstraintHandler] that always throws an
 * exception when a constraint violatio occurs, effectively enforcing a
 * constant-size constraint on an object pool.
 */
class ConstantConstraintHandler : ConstraintHandler {

    override fun handleViolation(pool: ObjectPool<*>) {
        throw ObjectPoolException(
            "The object pool is at full capacity and cannot borrow more."
                                 )
    }
}
