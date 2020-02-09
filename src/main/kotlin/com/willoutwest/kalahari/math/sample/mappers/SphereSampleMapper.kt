package com.willoutwest.kalahari.math.sample.mappers

import com.willoutwest.kalahari.math.MathUtils.Companion.TwoPi
import com.willoutwest.kalahari.math.MathUtils.Companion.cos
import com.willoutwest.kalahari.math.MathUtils.Companion.sin
import com.willoutwest.kalahari.math.MathUtils.Companion.sqrt
import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.sample.SampleMapper

/**
 * Represents an implementation of [SampleMapper] that maps sample points
 * from a unit square, or grid, to a sphere.
 */
class SphereSampleMapper : SampleMapper<Point3> {

    override fun map(points: List<Point2>): List<Point3> {
        val sphere = List(points.size) { Point3() }

        for(i in points.indices) {
            val r0 = points[i].x
            val r1 = points[i].y

            val z   = 1.0f - (2.0f * r0)
            val r   = sqrt(1.0f - (z * z))
            val phi = TwoPi * r1

            val x = r * cos(phi)
            val y = r * sin(phi)

            sphere[i].x = x
            sphere[i].y = y
            sphere[i].z = z
        }

        return sphere
    }
}