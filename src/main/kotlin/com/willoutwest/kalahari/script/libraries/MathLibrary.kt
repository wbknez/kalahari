package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Matrix4
import com.willoutwest.kalahari.math.Normal3
import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.script.ScriptingLibrary

/**
 * Represents a mechanism for creating and working with mathematical objects
 * in a Lua scripting environment.
 */
class MathLibrary : ScriptingLibrary {

    /**
     * Creates a new RGB color with the specified components.
     *
     * @param red
     *        The red color component to use.
     * @param green
     *        The green color component to use.
     * @param blue
     *        The blue color component to use.
     * @return A new color.
     */
    fun color(red: Float, green: Float, blue: Float): Color3 =
        Color3(red, green, blue)

    /**
     * Creates a new four-dimensional matrix with the specified elements.
     *
     * @property t00
     *           A matrix element.
     * @property t01
     *           A matrix element.
     * @property t02
     *           A matrix element.
     * @property t03
     *           A matrix element.
     * @property t10
     *           A matrix element.
     * @property t11
     *           A matrix element.
     * @property t12
     *           A matrix element.
     * @property t13
     *           A matrix element.
     * @property t20
     *           A matrix element.
     * @property t21
     *           A matrix element.
     * @property t22
     *           A matrix element.
     * @property t23
     *           A matrix element.
     * @property t30
     *           A matrix element.
     * @property t31
     *           A matrix element.
     * @property t32
     *           A matrix element.
     * @property t33
     *           A matrix element.
     * @return A new matrix.
     */
    fun matrix(t00: Float, t01: Float, t02: Float, t03: Float,
               t10: Float, t11: Float, t12: Float, t13: Float,
               t20: Float, t21: Float, t22: Float, t23: Float,
               t30: Float, t31: Float, t32: Float, t33: Float): Matrix4 =
        Matrix4(t00, t01, t02, t03, t10, t11, t12, t13, t20, t21, t22, t23,
                t30, t31, t32, t33)

    /**
     * Creates a new three-dimensional normal with the specified coordinates.
     *
     * @param x
     *        The x-axis coordinate to use.
     * @param y
     *        The y-axis coordinate to use.
     * @param z
     *        The z-axis coordinate to use.
     * @return A new normal.
     */
    fun normal(x: Float, y: Float, z: Float): Normal3 = Normal3(x, y, z)

    /**
     * Creates a new two-dimensional point with the specified coordinates.
     *
     * @param x
     *        The x-axis coordinate to use.
     * @param y
     *        The y-axis coordinate to use.
     * @return A new point.
     */
    fun point2(x: Float, y: Float): Point2 = Point2(x, y)

    /**
     * Creates a new three-dimensional point with the specified coordinates.
     *
     * @param x
     *        The x-axis coordinate to use.
     * @param y
     *        The y-axis coordinate to use.
     * @param z
     *        The z-axis coordinate to use.
     * @return A new point.
     */
    fun point3(x: Float, y: Float, z: Float): Point3 = Point3(x, y, z)

    /**
     * Creates a new quaternion with the specified coordinates.
     *
     * @param x
     *        The x-axis coordinate to use.
     * @param y
     *        The y-axis coordinate to use.
     * @param z
     *        The z-axis coordinate to use.
     * @param w
     *        The w-axis coordinate to use.
     * @return A new quaternion.
     */
    fun quaternion(x: Float, y: Float, z: Float, w: Float): Quaternion =
        Quaternion(x, y, z, w)

    /**
     * Creates a new ray with the specified direction and starting point.
     *
     * @param dir
     *        The direction to use.
     * @param origin
     *        The starting point to use.
     * @return A new ray.
     */
    fun ray(dir: Vector3, origin: Point3): Ray3 = Ray3(dir, origin)

    /**
     * Creates a new three-dimensional vector with the specified coordinates.
     *
     * @param x
     *        The x-axis coordinate to use.
     * @param y
     *        The y-axis coordinate to use.
     * @param z
     *        The z-axis coordinate to use.
     * @return A new vector.
     */
    fun vector(x: Float, y: Float, z: Float): Vector3 = Vector3(x, y, z)
}