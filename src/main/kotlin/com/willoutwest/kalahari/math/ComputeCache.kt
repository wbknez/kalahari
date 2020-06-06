package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer
import com.willoutwest.kalahari.util.pool.ConstraintHandler
import com.willoutwest.kalahari.util.pool.QueueObjectPool

/**
 * Represents a collection of mathematical objects that may be used to avoid
 * the cost of frequent object creation in repetitive computations.
 *
 * One of the key benefits, in regards how this cache is implemented, is that
 * all borrowed objects are inherently scope safe.  This is in stark
 * contrast to a naked object cache where one must always be mindful of
 * execution order to prevent important cached values from being accidentally
 * overwritten.  This is not a concern here because the cached object
 * reference is completely removed from the cache itself; this allows it to
 * be treated as an autonomous object by the method that borrowed it and
 * renders it immune to accidental re-use because the cache itself no longer
 * has a reference to it until it is returned.  In effect, this cache serves
 * as an ad-hoc small object allocator for specific mathematical types.
 *
 * Please note that this approach trades one set of problems for another.  In
 * particular, great care must be taken to always return the exact number of
 * objects that were borrowed.  Otherwise, dangling objects will litter
 * various methods (a potentially significant problem with the amount of
 * recursion used in this project) and thereby defeat the original purpose of
 * this cache: to reduce object creation load.
 *
 * @param handler
 *        The constraint handler to use.
 * @property colors
 *           The pool of temporary colors.
 * @property matrices
 *           The pool of temporary matrices.
 * @property objects
 *           The pool of temporary object containers (for minimum parametric
 *           time).
 * @property points
 *           The pool of temporary three-dimensional points.
 * @property quats
 *           The pool of temporary quaternions.
 * @property rays
 *           The pool of temporary rays.
 * @property records
 *           The pool of temporary intersection records.
 * @property tmins
 *           The pool of temporary float containers (for minimum parametric
 *           time).
 * @property vectors
 *           The pool of temporary three-dimensional vectors.
 */
class ComputeCache(handler: ConstraintHandler) {

    val colors = QueueObjectPool(4, handler, { Color3() })

    val matrices = QueueObjectPool(2, handler, { Matrix4() })

    val objects = QueueObjectPool(4, handler, { ObjectContainer(null) })

    val points = QueueObjectPool(2, handler, { Point3() })

    val quats = QueueObjectPool(2, handler, { Quaternion() })

    val rays = QueueObjectPool(2, handler, { Ray3() })

    val records = QueueObjectPool(2, handler, { Intersection() })

    val tmins = QueueObjectPool(4, handler, { FloatContainer(0f) })

    val uvs = QueueObjectPool(1, handler, { TexCoord2() })

    val vectors = QueueObjectPool(4, handler, { Vector3() })
}