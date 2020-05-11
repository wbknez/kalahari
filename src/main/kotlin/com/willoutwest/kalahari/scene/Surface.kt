package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.intersect.Intersectable
import com.willoutwest.kalahari.scene.shadow.ShadowCaster

/**
 * Represents an arbitrarily-defined geometric surface.
 *
 * In this project, geometric surfaces are divided into two categories:
 *  1. Implicit surfaces.  These are surfaces that are defined by one or more
 *  mathematical functions.  These may be found in the <code>surfaces</code> package.
 *  2. Meshes.  These are surfaces that are composed of triangles in an
 *  organized manner.  Different kinds of meshes may be found in the
 *  <code>mesh</code> package.
 * Contrary to the previous iteration of this project, implicit surfaces are
 * simply assumed to be the default geometric surfaces used in most scenes
 * and thus do not have their own specific marker interface to denote this.
 * Meshes, on the other hand, have an additional layer that accommodates the
 * different ways triangles can be rendered.
 *
 * Because geometric surfaces denote the bottom-most layer of the scene graph
 * (they are always leaves), all intersections are assumed to take place in
 * object space.
 */
interface Surface : Cloneable, Intersectable, ShadowCaster {

    /**
     * Returns a copy of this surface.
     *
     * Because surfaces are immutable, this method will almost always return
     * a shallow clone.
     *
     * @return A clone.
     */
    public override fun clone(): Surface
}