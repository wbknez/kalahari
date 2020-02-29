package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.Matrix4
import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Vector3
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
 * @property invTransform
 *           The inverse transformation matrix in world space.
 * @property motion
 *           The local spatial attributes.
 * @property name
 *           A unique name or identifier.
 * @property parent
 *           The parent in the scene graph, if any.
 */
interface Actor : Cloneable, Intersectable {

    var bounds: BoundingVolume?

    var enabled: Boolean

    val invTransform: Matrix4

    val motion: Motion

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

    /**
     * Translates this actor by the specified amounts along each axis.
     *
     * @param x
     *        The amount to move in the x-axis direction.
     * @param y
     *        The amount to move in the y-axis direction.
     * @param z
     *        The amount to move in the z-axis direction.
     * @return A reference to this actor for easy chaining.
     */
    fun move(x: Float, y: Float, z: Float): Actor

    /**
     * Translates this actor by the specified amount.
     *
     * @param vec
     *        The amount to move.
     * @return A reference to this actor for easy chaining.
     */
    fun move(vec: Vector3): Actor

    /**
     * Rotates this actor by the specified amount around the specified axis.
     *
     * @param angle
     *        The amount to rotate in radians.
     * @param axis
     *        The axis to rotate around.
     * @return A reference to this actor for easy chaining.
     */
    fun rotate(angle: Float, axis: Vector3): Actor

    /**
     * Rotates this actor by the specified amounts along each axis.
     *
     * @param roll
     *        The angle to rotate in radians around the x-axis.
     * @param pitch
     *        The angle to rotate in radians around the y-axis.
     * @param yaw
     *        The angle to rotate in radians around the z-axis.
     * @return A reference to this actor for easy chaining.
     */
    fun rotate(roll: Float, pitch: Float, yaw: Float): Actor

    /**
     * Rotates this actor by the specified quaternion.
     *
     * @param quat
     *        The quaternion to rotate by.
     * @return A reference to this actor for easy chaining.
     */
    fun rotate(quat: Quaternion): Actor

    /**
     * Scales this actor by the specified amounts along each axis.
     *
     * @param x
     *        The amount to scale in the x-axis direction.
     * @param y
     *        The amount to scale in the y-axis direction.
     * @param z
     *        The amount to scale in the z-axis direction.
     * @return A reference to this actor for easy chaining.
     */
    fun scale(x: Float, y: Float, z: Float): Actor

    /**
     * Scales this actor by the specified amount.
     *
     * @param vec
     *        The amount to scale.
     * @return A reference to this actor for easy chaining.
     */
    fun scale(vec: Vector3): Actor
}