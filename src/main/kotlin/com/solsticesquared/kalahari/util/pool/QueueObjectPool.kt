package com.solsticesquared.kalahari.util.pool

import java.util.ArrayDeque

/**
 * Represents an implementation of [ObjectPool] that is backed by an
 * unsynchronized array-based dequeue.
 *
 * @param preallocate
 *        Whether or not to pre-allocate objects to the queue up to capacity.
 * @property borrowed
 *           The number of objects that have been borrowed.
 * @property queue
 *           The object queue.
 */
class QueueObjectPool<T>(override var capacity: Int,
                         override var handler: ConstraintHandler,
                         private val supplier: () -> T,
                         preallocate: Boolean = false)
    : ObjectPool<T> {

    var borrowed: Int = 0
        private set

    private val queue = ArrayDeque<T>(capacity)

    init {
        if(preallocate) {
            for(i in 0..(this.capacity - 1)) {
                this.queue.offer(this.supplier.invoke())
            }
        }
    }

    override val available: Int
        get() = this.capacity - this.borrowed

    override fun borrow(): T {
        if(this.available - 1 < 0) {
            this.handler.handleViolation(this)
        }

        this.borrowed += 1
        return when(!this.queue.isEmpty()) {
            false -> this.supplier.invoke()
            true  -> this.queue.poll()
        }
    }

    override fun clear() {
        this.queue.clear()
    }

    override fun reuse(obj: T) {
        if(this.available + 1 > this.capacity) {
            throw ObjectPoolException(
                "The number of available objects exceeds the capacity: " +
                "${this.available + 1} instead of ${this.capacity}."
                                     )
        }

        this.queue.offer(obj)
        this.borrowed -= 1
    }

    override fun reuse(vararg objs: T) {
        if(this.available + objs.size > this.capacity) {
            throw ObjectPoolException(
                "The number of available objects exceeds the capacity: " +
                "${this.available + objs.size} instead of ${this.capacity}."
            )
        }

        objs.forEach { this.queue.offer(it) }
        this.borrowed -= objs.size
    }
}
