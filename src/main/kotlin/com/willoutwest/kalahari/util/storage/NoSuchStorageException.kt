package com.willoutwest.kalahari.util.storage

/**
 * An implementation of [RuntimeException] that is thrown when a
 * [LocalStorage] object fails to find a stored value associated with a
 * particular key.
 *
 * @param msg
 *        The message describing the error.
 */
class NoSuchStorageException(msg: String) : RuntimeException(msg)
