package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.intersect.BoundingVolume
import com.willoutwest.kalahari.math.intersect.Intersectable

/**
 * Represents a spatially-aware scenegraph element.
 *
 * In this project, the scenegraph is effectively a large-scale spatial
 * partitioning tool.  It separates elements into logical spaces - using
 * tools like grids and KD-trees - in order to speed up intersection testing.
 * As such, only the leaf nodes contain any rendering information; this is
 * reflected in the design of the intersection testing framework.
 *
 * @property bounds
 *           The bounding volume to speed up intersection tests, if any.
 * @property enabled
 *           Whether or not an actor is visible; this is mostly for debugging
 *           purposes.
 * @property name
 *           A unique name or identifier.
 * @property parent
 *           The parent in the scene graph, if any.
 */
interface Actor : Cloneable, Intersectable {

    var bounds: BoundingVolume?

    var enabled: Boolean

    val name: String

    var parent: Actor?

    /**
     * Returns a copy of this actor.
     *
     * The actual semantics depend on the deriving class this method is
     * called on.  Because many scenegraph elements are immutable, cloned
     * actors usually return any sub-elements by reference.  Therefore, most
     * implementations of this method will return a shallow clone.
     *
     * @return A clone.
     */
    public override fun clone(): Actor
}