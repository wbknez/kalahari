package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.Matrix4
import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Vector3

/**
 * Represents a mechanism for transforming objects in three-dimensional space.
 * 
 * @property invTransform
 *           The inverse transformation matrix in world space.
 * @property motion
 *           The local spatial attributes.
 */
interface Movable {

    val invTransform: Matrix4

    val motion: Motion

    /**
     * Translates this movable by the specified amounts along each axis.
     *
     * @param x
     *        The amount to move in the x-axis direction.
     * @param y
     *        The amount to move in the y-axis direction.
     * @param z
     *        The amount to move in the z-axis direction.
     * @return A reference to this movable for easy chaining.
     */
    fun move(x: Float, y: Float, z: Float): Movable

    /**
     * Translates this movable by the specified amount.
     *
     * @param vec
     *        The amount to move.
     * @return A reference to this movable for easy chaining.
     */
    fun move(vec: Vector3): Movable

    /**
     * Rotates this movable by the specified amount around the specified axis.
     *
     * @param angle
     *        The amount to rotate in radians.
     * @param axis
     *        The axis to rotate around.
     * @return A reference to this movable for easy chaining.
     */
    fun rotate(angle: Float, axis: Vector3): Movable

    /**
     * Rotates this movable by the specified amounts along each axis.
     *
     * @param roll
     *        The angle to rotate in radians around the x-axis.
     * @param pitch
     *        The angle to rotate in radians around the y-axis.
     * @param yaw
     *        The angle to rotate in radians around the z-axis.
     * @return A reference to this movable for easy chaining.
     */
    fun rotate(roll: Float, pitch: Float, yaw: Float): Movable

    /**
     * Rotates this movable by the specified quaternion.
     *
     * @param quat
     *        The quaternion to rotate by.
     * @return A reference to this movable for easy chaining.
     */
    fun rotate(quat: Quaternion): Movable

    /**
     * Scales this movable by the specified amounts along each axis.
     *
     * @param x
     *        The amount to scale in the x-axis direction.
     * @param y
     *        The amount to scale in the y-axis direction.
     * @param z
     *        The amount to scale in the z-axis direction.
     * @return A reference to this movable for easy chaining.
     */
    fun scale(x: Float, y: Float, z: Float): Movable

    /**
     * Scales this movable by the specified amount.
     *
     * @param vec
     *        The amount to scale.
     * @return A reference to this movable for easy chaining.
     */
    fun scale(vec: Vector3): Movable

    /**
     * Updates the inverse transformation matrix of this movable.
     */
    fun updateTransform()
}