package com.willoutwest.kalahari.math

import java.lang.RuntimeException

/**
 * Represents an implementation of [RuntimeException] that is thrown when
 * taking the inverse of a mathematical object that is not invertible.
 */
class NotInvertibleException(msg: String, t: Throwable? = null)
    : RuntimeException(msg, t)