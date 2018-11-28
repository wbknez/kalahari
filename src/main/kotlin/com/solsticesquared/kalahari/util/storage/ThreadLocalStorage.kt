package com.solsticesquared.kalahari.util.storage

/**
 * Represents an implementation of [LocalStorage] that associates data of
 *
 * This implementation is meant to rectify one of the largest problems with
 * Java's [ThreadLocal] class; the inability to explicitly control the
 * lifetime of created objects.  This class, as per the specification of
 * [LocalStorage], allows the explicit removal of all current storage items
 * on demand.  In addition, storage objects are created upon request by a
 * user-specified function, allowing this class to be used blindly across any
 * coroutine or thread context.
 *
 * @property supplier
 *           The creation method to use when a value is not present.
 */
class ThreadLocalStorage<V>(private val supplier: () -> V,
                            initialCapacity: Int = 16,
                            loadFactor: Float = 0.75f,
                            concurrencyLevel: Int = 16)
    : LocalStorage<Long, V>(initialCapacity, loadFactor, concurrencyLevel) {

    /**
     * Returns an arbitrarily typed value using the currently executing
     * thread's identifier as the key; if none exists.
     *
     * If no such value is found, a new one will be created and added to this
     * thread local storage.
     *
     * @return A value associated with a thread.
     */
    fun get(): V {
        val id = Thread.currentThread().id

        if(!this.store.containsKey(id)) {
            this.store[id] = this.supplier.invoke()
        }

        return this.store[id]!!
    }
}
