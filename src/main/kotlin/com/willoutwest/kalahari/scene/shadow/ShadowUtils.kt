package com.willoutwest.kalahari.scene.shadow

import com.willoutwest.kalahari.scene.shadow.detectors.BasicDetector
import com.willoutwest.kalahari.util.storage.ThreadLocalStorage

/**
 * Represents a collection of utility methods for working with shadow
 * detection.
 *
 * @property caches
 *           The mapping of shadow detectors to thread identifiers.
 * @property localDetector
 *           A thread-specific shadow detector.
 */
sealed class ShadowUtils {
    companion object {

        private var caches: ThreadLocalStorage<ShadowDetector> =
            ThreadLocalStorage({ BasicDetector() })

        val localDetector: ShadowDetector
            get() = caches.get()

        /**
         * Sets the type of shadow detector to use when computing shadows.
         *
         * @param supplier
         *        The shadow cache to use.
         */
        fun setDetector(supplier: () -> ShadowDetector) {
            caches.clear()
            caches = ThreadLocalStorage(supplier)
        }
    }
}