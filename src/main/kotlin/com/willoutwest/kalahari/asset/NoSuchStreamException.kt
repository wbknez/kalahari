package com.willoutwest.kalahari.asset

/**
 * Represents an implementation of [RuntimeException] that is thrown when a
 * [StreamSource] attempts to open a stream to a path that does not exist.
 *
 * @param msg
 *        The error message to use.
 * @param t
 *        An exception to wrap.
 */
class NoSuchStreamException(msg: String, t: Throwable? = null) :
    RuntimeException(msg, t)