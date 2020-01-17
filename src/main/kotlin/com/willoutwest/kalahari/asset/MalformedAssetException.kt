package com.willoutwest.kalahari.asset

/**
 * Represents an implementation of [RuntimeException] that is thrown when an
 * [AssetReader] attempts to read an asset from a stream and fails because
 * the data in the stream is not as expected.
 *
 * @param msg
 *        The error message to use.
 * @param t
 *        An exception to wrap.
 */
class MalformedAssetException(msg: String, t: Throwable? = null) :
    RuntimeException(msg, t)
