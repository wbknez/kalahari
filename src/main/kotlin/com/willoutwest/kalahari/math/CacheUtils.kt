package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.pool.FlexibleConstraintHandler
import com.willoutwest.kalahari.util.storage.ThreadLocalStorage

/**
 * Represents a collection of utility methods for working with caches of
 * temporary variables.
 *
 * @property caches
 *           The mapping of computing caches to thread identifiers.
 * @property localCache
 *           A thread-specific computing cache.
 */
sealed class CacheUtils {

    companion object {

        private val caches = ThreadLocalStorage ({
            ComputeCache(FlexibleConstraintHandler(0, 0.5f))
        })

        val localCache: ComputeCache
            get() = caches.get()
    }
}