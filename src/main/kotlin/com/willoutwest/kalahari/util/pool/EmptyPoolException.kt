package com.willoutwest.kalahari.util.pool

/**
 * Represents an implementation of [RuntimeException] that is thrown when a
 * caller tries to borrow too many objects from an object pool.
 */
class EmptyPoolException(msg: String, t: Throwable? = null)
    : RuntimeException(msg, t)
