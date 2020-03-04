package com.willoutwest.kalahari.util

/**
 * Represents an object that has some type of unique identifier associated
 * with it.
 *
 * @property type
 *           A unique identifier.
 */
interface Typeable<T> {

    var type: T
}
