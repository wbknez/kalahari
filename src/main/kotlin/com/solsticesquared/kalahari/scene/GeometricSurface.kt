package com.solsticesquared.kalahari.scene

import com.solsticesquared.kalahari.math.intersect.Intersectable

/**
 * Represents an arbitrarily-defined geometric surface.
 *
 * In this project, geometric surfaces are divided into two categories:
 *  1. Implicit surfaces.  These are surfaces that are defined by one or more
 *  mathematical functions.
 *  2. Meshes.  These are surfaces that are composed of triangles in an
 *  organized manner.
 * Contrary to the previous iteration of this project, implicit surfaces are
 * simply assumed to be the default geometric surfaces used in most scenes
 * and thus do not have their own specific marker interface to denote this.
 * Meshes, on the other hand, have an additional layer that accommodates the
 * different ways triangles may be rendered.
 *
 * Because geometric surfaces denote the bottom-most layer of the scene graph
 * (they are always leaves), all intersections are assumed to take place in
 * object space.
 */
interface GeometricSurface : Intersectable
