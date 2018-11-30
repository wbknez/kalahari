package com.solsticesquared.kalahari.util.container

/**
 * Represents an modifiable [Any] object that may be passed up and down a
 * call stack.
 */
data class ObjectContainer(var obj: Any?) : Cloneable
