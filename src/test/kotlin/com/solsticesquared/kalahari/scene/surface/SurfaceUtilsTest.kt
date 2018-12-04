package com.solsticesquared.kalahari.scene.surface

import com.solsticesquared.kalahari.math.Normal3
import com.solsticesquared.kalahari.math.Normal3Generator
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Point3Generator
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.math.Ray3Generator
import com.solsticesquared.kalahari.math.Vector3
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

/**
 * Test suite for [SurfaceUtils].
 */
class SurfaceUtilsTest : ShouldSpec() {

    companion object {

        /**
         * The utility to create randomized [Normal3] instances.
         */
        val Nor3Gen = Normal3Generator()

        /**
         * The utility to create randomized [Point3] instances.
         */
        val Poi3Gen = Point3Generator()

        /**
         * The utility to create randomized [Ray3] instances.
         */
        val Ray3Gen = Ray3Generator()
    }

    init {

        "Computing the time of intersection between a ray and a point" {
            should("be equivalent to the quotient of the dot products " +
                   "of the point direction and ray direction with the " +
                   "normal.") {
                forAll(Nor3Gen, Poi3Gen, Ray3Gen) {
                    normal: Normal3, point: Point3, ray: Ray3 ->

                    val dDotN = ray.dir.dot(normal)
                    val pDotN = Vector3(point - ray.origin).dot(normal)

                    (pDotN / dDotN) == SurfaceUtils.getIntersectionTime(
                        point, normal, ray
                    )
                }
            }
        }
    }
}
