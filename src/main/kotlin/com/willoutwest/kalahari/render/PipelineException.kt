package com.willoutwest.kalahari.render

/**
 * Represents an implementation of [RuntimeException] that is thrown when a
 * [Pipeline] encounters an error during processing.
 *
 * @param msg
 *        The message describing the error.
 * @param t
 *        The exception representing the error.
 */
class PipelineException(msg: String?, t: Throwable?) : RuntimeException(msg, t)
