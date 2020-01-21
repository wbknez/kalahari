package com.willoutwest.kalahari.math

import java.lang.Float.floatToIntBits
import kotlin.math.pow

/**
 * Represents a collection of mathematics-related utility methods.
 */
sealed class MathUtils {

    companion object {

        /**
         * Represents the fraction of one half (1/2).
         */
        const val OneHalf          = 1f / 2f

        /**
         * Represents the fraction of one third (1/3).
         */
        const val OneThird         = 1f / 3f

        /**
         * Represents the fraction of one fourth (1/4).
         */
        const val OneFourth        = 1f / 4f

        /**
         * Represents the fraction of one fifth (1/5).
         */
        const val OneFifth         = 1f / 5f

        /**
         * Represents the glorious constant pi, or π.
         */
        const val Pi               = Math.PI.toFloat()

        /**
         * Represents the inverse of pi, 1/π.
         */
        const val InvPi            = (1.0 / Math.PI).toFloat()

        /**
         * Represents pi over two, or π/2.
         */
        const val PiOverTwo        = (Math.PI / 2.0).toFloat()

        /**
         * Represents pi over three, or π/3.
         */
        const val PiOverThree      = (Math.PI / 3.0).toFloat()

        /**
         * Represents pi over four, or π/4.
         */
        const val PiOverFour       = (Math.PI / 4.0).toFloat()

        /**
         * Represents pi over six, or π/6.
         */
        const val PiOverSix        = (Math.PI / 6.0).toFloat()

        /**
         * Represents pi over one-hundred and eighty, or π/2.
         */
        const val PiOver180        = (Math.PI / 180.0).toFloat()

        /**
         * Represents two times pi, or 2π.
         */
        const val TwoPi            = (2.0 * Math.PI).toFloat()

        /**
         * Integer bitwise representation of negative zero.
         */
        const val NegativeZeroBits = 0x80000000.toInt()

        /**
         * Computes the absolute value of the specified value.
         *
         * @param x
         *        The value to use.
         * @return The absolute value.
         */
        fun abs(x: Float): Float = kotlin.math.abs(x)

        /**
         * Determines whether the specified value is within the range given
         * by the specified maximum and minimum, respectively, and returns
         * the value that should be used instead if so.
         *
         * @param x
         *        The value to use.
         * @param min
         *        The minimum value to use.
         * @param max
         *        The maximum value to use.
         * @return A clamped value.
         */
        fun clamp(x: Float, min: Float, max: Float): Float =
            x.coerceIn(min, max)

        /**
         * Computes the cosine of the specified angle in radians.
         *
         * @param angle
         *        The angle to use in radians.
         * @return The cosine of an angle.
         */
        fun cos(angle: Float): Float = kotlin.math.cos(angle)

        /**
         * Computes the cotangent of the specified angle in radians.
         *
         * @param angle
         *        The angle to use in radians.
         * @return The cotangent of an angle.
         */
        fun cot(angle: Float): Float = 1f / kotlin.math.tan(angle)

        /**
         * Computes the cosecant of the specified angle in radians.
         *
         * @param angle
         *        The angle to use in radians.
         * @return The cosecant of an angle.
         */
        fun csc(angle: Float): Float = 1f / kotlin.math.sin(angle)

        /**
         * Computes the largest integer that is less than the specified value
         * and returns it as a floating-point number.
         *
         * @param x
         *        The value to use.
         * @return The largest integer smaller than a value.
         */
        fun floor(x: Float): Float = kotlin.math.floor(x)

        /**
         * Computes the largest integer that is less than the specified value.
         *
         * @param x
         *        The value to use.
         * @return The largest integer small than a value.
         */
        fun floori(x: Float): Int = kotlin.math.floor(x).toInt()

        /**
         * Computes and applies the coefficients for a cubic spline from the
         * specified noise values and returns the interpolated result.
         *
         * @param x
         *        The starting value to interpolate from.
         * @param k0
         *        A noise value.
         * @param k1
         *        Another noise value.
         * @param k2
         *        Another noise value.
         * @param k3
         *        Another noise value.
         * @return A spline-based interpolated value.
         */
        fun fourKnotSpline(x: Float,
                           k0: Float, k1: Float, k2: Float, k3: Float)
            : Float {
            val c3 = -0.5f * k0 + 1.5f * k1 - 1.5f * k2 + 0.5f * k3
            val c2 = k0 - 2.5f * k1 + 2.0f * k2 - 0.5f * k3
            val c1 = 0.5f * (-k0 + k2)

            return ((c3 * x + c2) * x + c1) * x + k1
        }

        /**
         * Computes and applies the coefficients for a cubic spline from the
         * specified noise values and returns the interpolated result.
         *
         * @param x
         *        The starting value to interpolate from.
         * @param kns
         *        The array of noise values to use.
         * @return A spline-based interpolated value.
         */
        fun fourKnotSpline(x: Float, kns: FloatArray): Float =
            fourKnotSpline(x, kns[0], kns[1], kns[2], kns[3])

        /**
         * Determines whether or not the specified value lies on the
         * exclusive interval defined by the specified end points.
         *
         * @param x
         *        The value to check.
         * @param a
         *        The start of the interval.
         * @param b
         *        The end of the interval.
         * @return Whether or not a value is within a given interval.
         */
        fun isExclusive(x: Float, a: Float, b: Float): Boolean =
            (a < x) && (x < b)

        /**
         * Determines whether or not the specified value lies on the
         * inclusive interval defined by the specified end points.
         *
         * @param x
         *        The value to check.
         * @param a
         *        The start of the interval.
         * @param b
         *        The end of the interval.
         * @return Whether or not a value is strictly within a given interval.
         */
        fun isInclusive(x: Float, a: Float, b: Float): Boolean =
            (a <= x) && (x <= b)

        /**
         * Computes a linearly interpolated value using the specified start
         * and end points at the specified point in parametric time.
         *
         * @param a
         *        The start value to use.
         * @param b
         *        The end value to use.
         * @param t
         *        The parametric time to use.
         * @return A linearly interpolated value.
         */
        fun lerp(a: Float, b: Float, t: Float): Float = a + (b - a) * t

        /**
         * Computes the maximum of two values.
         *
         * @param a
         *        A value to compare.
         * @param b
         *        Another value to compare.
         * @return The maximum.
         */
        fun max(a: Float, b: Float): Float = kotlin.math.max(a, b)

        /**
         * Computes the minimum of two values.
         *
         * @param a
         *        A value to compare.
         * @param b
         *        Another value to compare.
         * @return The minimum.
         */
        fun min(a: Float, b: Float): Float = kotlin.math.min(a, b)

        /**
         * Raises the specified value to the specified power.
         *
         * @param x
         *        The value to raise.
         * @param exp
         *        The exponent to use.
         * @return One value raised to another.
         */
        fun pow(x: Float, exp: Float): Float = x.pow(exp)

        /**
         * Computes the inverse of the specified value.
         *
         * This method differentiates between positive and negative zero, which
         * allows other algorithms to correctly handle a "divide by zero"
         * inverse attempt while still preserving the sign of the result.
         *
         * @param x
         *        The value to use.
         * @return The inverse of a value.
         */
        fun safeInverse(x: Float): Float =
            when(x == 0f) {
                false -> 1f / x
                true  -> when(floatToIntBits(x) == NegativeZeroBits) {
                    false -> Float.POSITIVE_INFINITY
                    true  -> Float.NEGATIVE_INFINITY
                }
            }

        /**
         * Computes the secant of the specified angle in radians.
         *
         * @param angle
         *        The angle to use in radians.
         * @return The secant of an angle.
         */
        fun sec(angle: Float): Float = 1f / kotlin.math.cos(angle)

        /**
         * Computes the sine of the specified angle in radians.
         *
         * @param angle
         *        The angle to use in radians.
         * @return The sine of an angle.
         */
        fun sin(angle: Float): Float = kotlin.math.sin(angle)

        /**
         * Computes the square root of the specified value.
         *
         * @param x
         *        The value to use.
         * @return The square root.
         */
        fun sqrt(x: Float): Float = kotlin.math.sqrt(x)

        /**
         * Computes the tangent of the specified angle in radians.
         *
         * @param angle
         *        The angle to use in radians.
         * @return The tangent of an angle.
         */
        fun tan(angle: Float): Float = kotlin.math.tan(angle)

        /**
         * Converts the specified angle in radians to one in degrees.
         *
         * @param radians
         *        The angle in radians to convert.
         * @return An angle in degrees from radians.
         */
        fun toDegrees(radians: Float): Float = radians * 180.0f / Pi

        /**
         * Converts the specified angle in degrees to one in radians.
         *
         * @param degrees
         *        The angle in degrees to convert.
         * @return An angle in radians from degrees.
         */
        fun toRadians(degrees: Float): Float = degrees / 180.0f * Pi
    }
}
