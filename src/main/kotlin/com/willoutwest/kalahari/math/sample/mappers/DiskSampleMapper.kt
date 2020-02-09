package com.willoutwest.kalahari.math.sample.mappers

import com.willoutwest.kalahari.math.MathUtils.Companion.PiOverFour
import com.willoutwest.kalahari.math.MathUtils.Companion.cos
import com.willoutwest.kalahari.math.MathUtils.Companion.sin
import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.sample.SampleMapper

/**
 * Represents an implementation of [SampleMapper] that maps sample points
 * from a unit square, or grid, to a disk.
 */
class DiskSampleMapper : SampleMapper<Point2> {

    override fun map(points: List<Point2>): List<Point2> {
        val disk = List(points.size) { Point2() }

        for(i in points.indices) {
            val x = (2f * points[i].x) - 1f
            val y = (2f * points[i].y) - 1f

            val top      = x > -y
            val positive = x > y

            var r: Float
            var phi: Float

            if(top && positive) {
                r   = x
                phi = y / x
            }
            else if(top) {
                r   = y
                phi = 2f - (x / y)
            }
            else if(positive) {
                r   = -x
                phi = 4f + (y / x)
            }
            else {
                r   = -y
                phi = when(y != 0f) {
                    false -> 0f
                    true  -> 6f - (x / y)
                }
            }

            phi *= PiOverFour

            disk[i].x = r * cos(phi)
            disk[i].y = r * sin(phi)
        }

        return disk
    }
}