package com.willoutwest.kalahari.util

/**
 * Allows the use of multiple objects with Kotlin's try-with-resources via
 * [AutoCloseable.close] idiom.
 *
 * This method is taken from the following blog post: "Effective Kotlin Item
 * 9: Prefer try-with-resources to try-finally" which may be found at:
 * https://medium.com/@appmattus/effective-kotlin-item-9-prefer-try-with-resources-to-try-finally-aec8c202c30a
 *
 * @param block
 *        The usage (resources) block to execute.
 */
inline fun <T : AutoCloseable?> Array<T>.use(block: () -> Unit) {
    var exception: Throwable? = null

    try {
        return block()
    }
    catch (t: Throwable) {
        exception = t

        throw t
    }
    finally {
        when(exception) {
            null -> forEach { it?.close() }
            else -> forEach {
                try {
                    it?.close()
                }
                catch (ce: Throwable) {
                    exception.addSuppressed(ce)
                }
            }
        }
    }
}