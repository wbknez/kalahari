package com.willoutwest.kalahari.util.pool

/**
 * Represents an implementation of [RuntimeException] that is thrown when a
 * caller tries to submit too many objects to an object pool for reuse.
 */
class FullPoolException(msg: String, t: Throwable? = null)
    : RuntimeException(msg, t)