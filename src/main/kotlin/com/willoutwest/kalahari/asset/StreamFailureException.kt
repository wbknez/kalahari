package com.willoutwest.kalahari.asset

/**
 * Represents an implementation of [RuntimeException] that is thrown when a
 * [StreamSource] encounters a problem when attempting to operate on (open,
 * read, or close) a stream.
 *
 * @param msg
 *        The error message to use.
 * @param t
 *        An exception to wrap.
 */
class StreamFailureException(msg: String, t: Throwable? = null) :
    RuntimeException(msg, t)