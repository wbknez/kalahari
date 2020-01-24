package com.willoutwest.kalahari.util.pool

/**
 * Represents a mechanism to enforce arbitrary size constraints upon an
 * [ObjectPool].
 */
@FunctionalInterface
interface ConstraintHandler {

    /**
     * Determines how to respond when an attempt is made to borrow more
     * objects from an object pool that its capacity allows.
     *
     * A constraint handler may resolve a violation in one of four ways:
     *  1. It may alter the pool in some way.
     *  2. It may attempt to alter the pool in some way and fail.
     *  3. It may throw an exception.
     *  4. It may do nothing.
     * Which course of action is taken depends on the implementation.  For
     * purposes of this project, only (1) and (3) are supported via
     * implementation with (1) being the default.
     *
     * @param pool
     *        The object pool that generated the violation.
     * @throws EmptyPoolException
     *         If a violation needs to be propagated to a caller, thereby
     *         halting pool operations.
     */
    fun handleViolation(pool: ObjectPool<*>)
}
