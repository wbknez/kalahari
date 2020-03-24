package com.willoutwest.kalahari.util

/**
 * Represents a collection of parameter values, associated by name, that serve
 * as the basis for ray tracing related objects such as cameras, lights, and
 * materials.
 *
 * In previous versions of this project, this class utilized a hash map
 * directly and provided numerous typecasting functions.  In this version,
 * however, Kotlin property delegate obviate this need and allow seamless
 * optional field access with a built-in API accessible from Lua scripts via
 * generated per property get/set methods.
 *
 * @property id
 *           The unique identifier; usually context specific.
 * @property params
 *           The collection of parameter values associated by name; these
 *           are typically accessed through property delegation.
 */
open class ParameterMap(val id: String) : Cloneable {

    protected val params = mutableMapOf<String, Any>()

    /**
     * Constructor.
     *
     * @param map
     *        The parameter map to copy from.
     */
    constructor(map: ParameterMap?) : this(map!!.id) {
        this.params.putAll(map.params)
    }

    public override fun clone(): ParameterMap = ParameterMap(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is ParameterMap -> this.id == other.id &&
                               this.params == other.params
            else            -> false
        }

    /**
     * Searches and returns the value associated with the specified key; if
     * none is found, the specified default value will be returned instead.
     *
     * @param key
     *        The key to search for.
     * @param defaultValue
     *        The value to use if no association exists.
     * @return A value associated with a key.
     */
    fun getOrDefault(key: String, defaultValue: Any): Any =
        this.params.getOrDefault(key, defaultValue)

    override fun hashCode(): Int = hash(this.id, this.params)

    override fun toString() = "ParameterMap(${this.id}, ${this.params})"
}