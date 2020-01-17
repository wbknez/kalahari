package com.willoutwest.kalahari.asset

/**
 * Represents an implementation of [RuntimeException] that is thrown when an
 * [AssetLoader] fails to find an [AssetReader] for a given path.
 *
 * @param msg
 *        The error message to use.
 * @param t
 *        An exception to wrap.
 */
class NoSuchReaderException(msg: String, t: Throwable? = null) :
    RuntimeException(msg, t)