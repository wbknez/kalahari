package com.willoutwest.kalahari.script

import java.lang.RuntimeException

/**
 * Represents an implementation of [RuntimeException] that is thrown when an
 * attempt is made to use a method implementation for a proxy that does not
 * exist.
 */
class NoSuchImplementationException(msg: String, t: Throwable? = null)
    : RuntimeException(msg, t)