package com.willoutwest.kalahari.math.sample.mappers

import com.willoutwest.kalahari.math.MathUtils.Companion.TwoPi
import com.willoutwest.kalahari.math.MathUtils.Companion.cos
import com.willoutwest.kalahari.math.MathUtils.Companion.pow
import com.willoutwest.kalahari.math.MathUtils.Companion.sin
import com.willoutwest.kalahari.math.MathUtils.Companion.sqrt
import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.sample.SampleMapper

/**
 * Represents an implementation of [SampleMapper] that maps sample points
 * from a unit square, or grid, to a unit hemisphere with an arbitrary
 * exponent.
 *
 * @property exponent
 *           The exponent to use.
 */
class HemisphereSampleMapper(val exponent: Float) : SampleMapper<Point3> {

    override fun map(points: List<Point2>): List<Point3> {
        val hemisphere = List(points.size) { Point3() }
        val expContrib = 1f / (this.exponent + 1f)

        for(i in points.indices) {
            val cosPhi = cos(points[i].x * TwoPi)
            val sinPhi = sin(points[i].x * TwoPi)
            val cosTheta = pow(1f - points[i].y, expContrib)
            val sinTheta = sqrt(1f - cosTheta * cosTheta)

            val u = sinTheta * cosPhi
            val v = sinTheta * sinPhi

            hemisphere[i].x = u
            hemisphere[i].y = v
            hemisphere[i].z = cosTheta
        }

        return hemisphere
    }
}