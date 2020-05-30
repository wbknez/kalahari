package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.math.Normal3
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.Actor
import com.willoutwest.kalahari.scene.Geometric
import com.willoutwest.kalahari.scene.Group
import com.willoutwest.kalahari.scene.surfaces.Box
import com.willoutwest.kalahari.scene.surfaces.Cylinder
import com.willoutwest.kalahari.scene.surfaces.Disk
import com.willoutwest.kalahari.scene.surfaces.Plane
import com.willoutwest.kalahari.scene.surfaces.Rectangle
import com.willoutwest.kalahari.scene.surfaces.Sphere
import com.willoutwest.kalahari.scene.surfaces.Torus
import com.willoutwest.kalahari.scene.surfaces.Triangle
import com.willoutwest.kalahari.script.ScriptingLibrary

/**
 * Represents a collection of utility methods for creating and working with
 * [Actor] objects.
 */
class ActorLibrary : ScriptingLibrary {

    /**
     * Creates a new actor whose geometry is represented as a box with the
     * specified properties and no material.
     *
     * @param name
     *        The actor name to use.
     * @param center
     *        The center of a box to use.
     * @param xLen
     *        The extension along the x-axis.
     * @param yLen
     *        The extension along the y-axis.
     * @param zLen
     *        The extension along the z-axis.
     * @return A new actor shaped as a box.
     */
    fun box(name: String, center: Point3, xLen: Float, yLen: Float,
            zLen: Float): Geometric =
        box(name, center, xLen, yLen, zLen, null)

    /**
     * Creates a new actor whose geometry is represented as a box with the
     * specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param center
     *        The center of a box to use.
     * @param xLen
     *        The extension along the x-axis.
     * @param yLen
     *        The extension along the y-axis.
     * @param zLen
     *        The extension along the z-axis.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a box.
     */
    fun box(name: String, center: Point3, xLen: Float, yLen: Float,
            zLen: Float, material: Material?): Geometric =
        Geometric(name, Box(center, xLen, yLen, zLen), material)

    /**
     * Creates a new actor whose geometry is represented as a disk with the
     * specified properties and no material.
     *
     * @param name
     *        The actor name to use.
     * @param center
     *        The center of a disk to use.
     * @param radius
     *        The radius to use.
     * @param normal
     *        The normal direction to face.
     * @return A new actor shaped as a disk.
     */
    fun disk(name: String, center: Point3, radius: Float, normal: Normal3):
        Geometric =
        disk(name, center, radius, normal, null)

    /**
     * Creates a new actor whose geometry is represented as a disk with the
     * specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param center
     *        The center of a disk to use.
     * @param radius
     *        The radius of a disk to use.
     * @param normal
     *        The normal direction to face.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a disk.
     */
    fun disk(name: String, center: Point3, radius: Float, normal: Normal3,
             material: Material?): Geometric =
        Geometric(name, Disk(center, radius, normal), material)

    /**
     * Creates a new grouping with the specified name of a variable number of
     * other actors.
     *
     * @param name
     *        The name of the group.
     * @param actors
     *        One or more actors to add to the group.
     * @return A new group of actors.
     */
    fun groupOf(name: String, vararg actors: Actor): Group {
        val group = Group(name)

        actors.forEach(group::addChild)

        return group
    }

    /**
     * Creates a new actor whose geometry is represented as an open cylinder
     * with the specified properties and no material.
     *
     * @param name
     *        The actor name to use.
     * @param bottom
     *        The bottom y-axis coordinate to use.
     * @param top
     *        The top y-axis coordinate to use.
     * @param radius
     *        The radius to use.
     * @return A new actor shaped as an open cylinder.
     */
    fun openCylinder(name: String, bottom: Float, top: Float, radius: Float):
        Geometric =
        Geometric(name, Cylinder(bottom, top, radius), null)

    /**
     * Creates a new actor whose geometry is represented as an open cylinder
     * with the specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param bottom
     *        The bottom y-axis coordinate to use.
     * @param top
     *        The top y-axis coordinate to use.
     * @param radius
     *        The radius to use.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as an open cylinder.
     */
    fun openCylinder(name: String, bottom: Float, top: Float, radius: Float,
                     material: Material?): Geometric =
        Geometric(name, Cylinder(bottom, top, radius), material)

    /**
     * Creates a new actor whose geometry is represented as a plane with the
     * specified properties and no material.
     *
     * @param name
     *        The actor name to use.
     * @param point
     *        A point on a plane to use.
     * @param normal
     *        The normal on a plane to use.
     * @return A new actor shaped as a plane.
     */
    fun plane(name: String, point: Point3, normal: Normal3): Geometric =
        plane(name, point, normal, null)

    /**
     * Creates a new actor whose geometry is represented as a plane with the
     * specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param point
     *        A point on a plane to use.
     * @param normal
     *        The normal on a plane to use.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a plane.
     */
    fun plane(name: String, point: Point3, normal: Normal3,
              material: Material?): Geometric =
        Geometric(name, Plane(point, normal), material)

    /**
     * Creates a new actor whose geometry is represented as a rectangle with
     * the specified properties and no material.
     *
     * @param name
     *        The actor name to use.
     * @param origin
     *        The origin point to use.
     * @param a
     *        An extent to use.
     * @param b
     *        Another extent to use.
     * @param normal
     *        The direction to face.
     * @return A new actor shaped as a rectangle.
     */
    fun rectangle(name: String, origin: Point3, a: Vector3, b: Vector3,
                  normal: Normal3): Geometric =
        rectangle(name, origin, a, b, normal, null)

    /**
     * Creates a new actor whose geometry is represented as a rectangle with
     * the specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param origin
     *        The origin point to use.
     * @param a
     *        An extent to use.
     * @param b
     *        Another extent to use.
     * @param normal
     *        The direction to face.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a rectangle.
     */
    fun rectangle(name: String, origin: Point3, a: Vector3, b: Vector3,
                  normal: Normal3, material: Material?): Geometric =
        Geometric(name, Rectangle(origin, a, b, normal), material)

    /**
     * Creates a new actor whose geometry is represented as a sphere with the
     * specified properties and no material.
     *
     * @param name
     *        The actor name to use.
     * @param center
     *        The center of a sphere to use.
     * @param radius
     *        The radius of a sphere to use.
     * @return A new actor shaped as a sphere.
     */
    fun sphere(name: String, center: Point3, radius: Float): Geometric =
        sphere(name, center, radius, null)

    /**
     * Creates a new actor whose geometry is represented as a sphere with the
     * specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param center
     *        The center of a sphere to use.
     * @param radius
     *        The radius of a sphere to use.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a sphere.
     */
    fun sphere(name: String, center: Point3, radius: Float,
               material: Material?): Geometric =
        Geometric(name, Sphere(center, radius), material)

    /**
     * Creates a new actor whose geometry is represented as a torus with the
     * specified properties and with no material.
     *
     * @param name
     *        The actor name to use.
     * @param a
     *        The center of a sphere to use.
     * @param b
     *        The radius of a sphere to use.
     * @return A new actor shaped as a torus.
     */
    fun torus(name: String, a: Float, b: Float): Geometric
        = torus(name, a, b, null)

    /**
     * Creates a new actor whose geometry is represented as a torus with the
     * specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param a
     *        The center of a sphere to use.
     * @param b
     *        The radius of a sphere to use.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a torus.
     */
    fun torus(name: String, a: Float, b: Float, material: Material?):
        Geometric =
        Geometric(name, Torus(a, b), material)

    /**
     * Creates a new actor whose geometry is represented as a triangle with the
     * specified properties and with no material.
     *
     * @param name
     *        The actor name to use.
     * @param v0
     *        A vertex to use.
     * @param v1
     *        Another vertex to use.
     * @param v2
     *        Another vertex to use.
     * @return A new actor shaped as a triangle.
     */
    fun triangle(name: String, v0: Point3, v1: Point3, v2: Point3): Geometric =
        triangle(name, v0, v1, v2, null)

    /**
     * Creates a new actor whose geometry is represented as a triangle with the
     * specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param v0
     *        A vertex to use.
     * @param v1
     *        Another vertex to use.
     * @param v2
     *        Another vertex to use.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a triangle.
     */
    fun triangle(name: String, v0: Point3, v1: Point3, v2: Point3,
                 material: Material?): Geometric =
        Geometric(name, Triangle(v0, v1, v2), material)

    /**
     * Creates a new actor whose geometry is represented as a triangle with the
     * specified properties and with no material.
     *
     * @param name
     *        The actor name to use.
     * @param v0
     *        A vertex to use.
     * @param v1
     *        Another vertex to use.
     * @param v2
     *        Another vertex to use.
     * @param normal
     *        The direction to face.
     * @return A new actor shaped as a triangle.
     */
    fun triangle(name: String, v0: Point3, v1: Point3, v2: Point3,
                 normal: Normal3): Geometric =
        triangle(name, v0, v1, v2, normal, null)

    /**
     * Creates a new actor whose geometry is represented as a triangle with the
     * specified properties and with the specified material, if any.
     *
     * @param name
     *        The actor name to use.
     * @param v0
     *        A vertex to use.
     * @param v1
     *        Another vertex to use.
     * @param v2
     *        Another vertex to use.
     * @param normal
     *        The direction to face.
     * @param material
     *        The collection of rendering parameters to use (optional).
     * @return A new actor shaped as a triangle.
     */
    fun triangle(name: String, v0: Point3, v1: Point3, v2: Point3,
                 normal: Normal3, material: Material?): Geometric =
        Geometric(name, Triangle(v0, v1, v2, normal), material)
}