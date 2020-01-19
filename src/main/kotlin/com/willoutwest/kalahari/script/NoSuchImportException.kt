package com.willoutwest.kalahari.script

import java.lang.RuntimeException

/**
 * Represents an implementation of [RuntimeException] that is thrown when an
 * attempt is made to import a class or package into Lua that does not exist.
 */
class NoSuchImportException(msg: String, t: Throwable?)
    : RuntimeException(msg, t)