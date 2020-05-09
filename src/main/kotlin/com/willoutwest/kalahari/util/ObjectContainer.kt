package com.willoutwest.kalahari.util

/**
 * Represents an modifiable [Any] object that may be passed up and down a
 * call stack.
 */
data class ObjectContainer(@JvmField var obj: Any?) : Cloneable