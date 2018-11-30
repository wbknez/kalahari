package com.solsticesquared.kalahari.util.pool

/**
 * Represents an implementation of [RuntimeException] that is thrown when a
 * caller tries to borrow or reuse too many objects from an object pool.
 */
class ObjectPoolException(msg: String, t: Throwable? = null)
    : RuntimeException(msg, t)

/**
 * Represents a mechanism for reusing objects of an arbitrary type.
 *
 * @property available
 *           The current number of objects available to be borrowed.
 * @property capacity
 *           The maximum number of objects that may be borrowed or reused.
 * @property handler
 *           The handler to use to react to borrow constraint violations.
 */
interface ObjectPool<T> {

    val available: Int

    var capacity: Int

    var handler: ConstraintHandler

    /**
     * Returns an object for use from this object pool.
     *
     * @return An object for use.
     * @throws ObjectPoolException
     *         If an attempt is made to borrow more than the allowed number
     *         of objects and the constraint handler throws an exception.
     */
    fun borrow(): T

    /**
     * Removes all cached objects from this object pool.
     *
     * Please note that this method does not reset any of the mechanisms used
     * to track the number of borrowed objects.  This method is generally
     * used to free utilized memory or ensure the state of the objects
     * returned by the pool (for unit testing purposes).
     */
    fun clear()

    /**
     * Adds the specified object to this object pool so that it may be used
     * again.
     *
     * This method increases the number of objects that may be borrowed by one.
     *
     * @param obj
     *        The object to return to the pool.
     * @throws ObjectPoolException
     *         If the number of objects that may be borrowed exceeds the
     *         capacity.
     */
    fun reuse(obj: T)

    /**
     * Adds the specified objects to this object pool so that they may be
     * used again.
     *
     * This method increases the number of objects that may be borrowed by
     * the number of objects reused.
     *
     * @param objs
     *        An arbitrary number of objects to return to the pool.
     * @throws ObjectPoolException
     *         If the number of objects that may be borrowed exceeds the
     *         capacity.
     */
    fun reuse(vararg objs: T)
}
