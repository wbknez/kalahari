package com.willoutwest.kalahari.util

import java.util.stream.Collectors

/**
 * Represents a mechanism to allow an object to associate itself with another
 * that operates upon it without knowing what that operating object actually
 * is by using an intermediary type (that may be extended).
 *
 * All of the above is a complicated way of saying that this class is,
 * essentially, a generic type factory that also allows lookups via container
 * objects that provide type association information.  This allows for those
 * objects to provide operational information about themselves seamlessly, as
 * well as allow the type of identification itself to be provided from
 * multiple sources (e.g. multiple enumerations).
 *
 * In this project, this class is used to provide a way for cameras, lights,
 * and shaders to signal which other objects should operate on them in an
 * extensible way that cooperates with Lua's typing system (no enum support).
 *
 * @property associations
 *           The mapping of type identifiers to operating methods.
 */
class TypeAssociator<T, A> {

    private val associations = mutableMapOf<T, A>()

    /**
     * Adds all of the type identifier mappings from the specified map to
     * this type associator.
     *
     * @param map
     *        The map to copy from.
     */
    fun addAll(map: Map<T, A>) {
        this.associations.putAll(map)
    }

    operator fun contains(id: T): Boolean =
        this.associations.containsKey(id)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is TypeAssociator<*, *> -> this.associations == other.associations
            else                    -> false
        }

    operator fun get(id: T): A =
        this.associations[id] ?: throw NoSuchElementException(
            "Could not find an association for ${id}."
        )

    override fun hashCode(): Int = hash(this.associations)

    operator fun set(id: T, assoc: A) {
        this.associations[id] = assoc
    }

    override fun toString(): String =
        this.associations.keys
            .stream()
            .map { key -> "${key}=${associations[key]}" }
            .collect(Collectors.joining( ", ", "TypeAssociator(", ")"))
}