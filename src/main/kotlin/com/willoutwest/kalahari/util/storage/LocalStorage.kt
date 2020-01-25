package com.willoutwest.kalahari.util.storage

import java.util.concurrent.ConcurrentHashMap

/**
 * Represents a mechanism for storing arbitrary types of data in an
 * explicitly concurrency-aware manner.
 *
 * Implementations of this class are intended to be immutable from the
 * perspective of the user.  The one notable exception is the ability to
 * clear the current storage of all data; this allows the user to control the
 * lifetime of accumulated data in the storage without explicitly controlling
 * its creation.
 *
 * @property store
 *           The concurrent storage of arbitrary type associated by key.
 */
abstract class LocalStorage<K, V>(initialCapacity: Int = 16,
                                  loadFactor: Float = 0.75f,
                                  concurrencyLevel: Int = 16) {

    protected val store =
        ConcurrentHashMap<K, V>(initialCapacity, loadFactor, concurrencyLevel)

    /**
     * Clears this local storage of any data.
     */
    fun clear() {
        this.store.clear()
    }

    /**
     * Determines whether or not the specified key is associated with a value
     * in this local storage.
     *
     * @param key
     *        The key to search for.
     * @return Whether or not a key is present.
     */
    fun containsKey(key: K): Boolean =
        this.store.containsKey(key)

    /**
     * Returns the value associated with the specified key in this local
     * storage.
     *
     * @param key
     *        The key to use.
     * @return A value associated with a key (which is allowed to be null).
     * @throws NoSuchStorageException
     *         If there is no value associated with the key.
     */
    operator fun get(key: K): V? =
        this.store[key] ?: throw NoSuchStorageException(
            "Could not find stored value for key: $key."
        )
}
