package com.willoutwest.kalahari.math

import kotlin.math.acos

/**
 * Represents a collection of methods for solving second, third, and fourth
 * degree polynomials.
 *
 * The implementations for these methods are converted from C code created
 * by Eric Haines for the book "Graphics Gems".  The original implementations
 * may be found here:
 *   https://github.com/erich666/GraphicsGems/blob/master/gems/Roots3And4.c
 */
sealed class PolyUtils {
    companion object {

        /**
         * Solves the cubic equation given by the specified coefficients.
         *
         * This methods solves a third degree polynomial of the form:
         *   <code>ax<sup>3</sup> + bx<sup>2</sup> + cx + d = 0</code>
         * and always produces at least one root.
         *
         * @param a
         *        A coefficient to use.
         * @param b
         *        Another coefficient to use.
         * @param c
         *        Another coefficient to use.
         * @param d
         *        Another coefficient to use.
         * @param eps
         *        The tolerance to use.
         * @return A collection of roots.
         */
        fun cubic(a: Float, b: Float, c: Float, d: Float, eps: Float = 1e-9f):
            List<Float> {
            val A = b / a
            val B = c / a
            val C = d / a

            val ASquared = A * A
            val p        = MathUtils.OneThird * (-MathUtils.OneThird * ASquared + B)
            val q        = MathUtils.OneHalf * (2f / 27f * A * ASquared -
                                                MathUtils.OneThird * A * B + C)

            val pCubed   = p * p * p
            val D        = q * q * pCubed

            val roots    = mutableListOf<Float>()
            val comp     = when(isZero(D, eps)) {
                false -> when(D < 0f) {
                        false -> 1
                        true  -> -1
                    }
                true  -> 0
            }

            when(comp) {
                0    -> when(isZero(q, eps)) {
                    false -> {
                        val u = Math.cbrt(-q.toDouble()).toFloat()

                        roots.add(2f * u)
                        roots.add(-u)
                    }
                    true  -> roots.add(0f)
                }
                -1   -> {
                    val phi = MathUtils.OneThird * acos(-q / MathUtils.sqrt(-pCubed))
                    val t   = 2f * MathUtils.sqrt(-p)

                    roots.add(t * MathUtils.cos(phi))
                    roots.add(-t * MathUtils.cos(phi + MathUtils.PiOverThree))
                    roots.add(-t * MathUtils.cos(phi - MathUtils.PiOverThree))
                }
                else -> {
                    val Dsqrt = MathUtils.sqrt(D)
                    val u     = Math.cbrt((Dsqrt - q).toDouble()).toFloat()
                    val v     = -Math.cbrt((Dsqrt + q).toDouble()).toFloat()

                    roots.add(u + v)
                }
            }

            val sub = MathUtils.OneThird * A

            return roots.map { it - sub }
        }

        /**
         * Determines whether or not the specified value is within the
         * interval bounded by the specified epsilon.
         *
         * Mathematically, this method determines whether or not a value is
         * epsilon-close to zero.
         *
         * @param x
         *        The value to check.
         * @param eps
         *        The tolerance to use.
         * @return Whether or not a value is "close enough" zero.
         */
        fun isZero(x: Float, eps: Float): Boolean =
            x > -eps && x < eps

        /**
         * Solves the quadratic equation given by the specified coefficients.
         *
         * This methods solves a second degree polynomial of the form:
         *   <code>ax<sup>2</sup> + b + c = 0</code>
         * and may produce no roots.
         *
         * @param a
         *        A coefficient to use.
         * @param b
         *        Another coefficient to use.
         * @param c
         *        Another coefficient to use.
         * @param eps
         *        The tolerance to use.
         * @return A collection of roots.
         */
        fun quadratic(a: Float, b: Float, c: Float, eps: Float = 1e-9f):
            List<Float> {
            val p = b / (2 * a)
            val q = c / a
            val D = p * p - q

            return when(isZero(D, eps)) {
                false -> when(D > 0f) {
                    false -> listOf()
                    true  -> {
                        val Dsqrt = MathUtils.sqrt(D)

                        listOf(Dsqrt - p, -Dsqrt - p)
                    }
                }
                true -> listOf(-p)
            }
        }

        /**
         * Solves the quartic equation given by the specified coefficients.
         *
         * This methods solves a fourth degree polynomial of the form:
         *   <code>ax<sup>4</sup> + <code>bx<sup>3</sup> + cx<sup>2</sup> + dx
         *   + e = 0</code>
         * and may produce no roots.
         *
         * @param a
         *        A coefficient to use.
         * @param b
         *        Another coefficient to use.
         * @param c
         *        Another coefficient to use.
         * @param d
         *        Another coefficient to use.
         * @param e
         *        Another coefficient to use.
         * @param eps
         *        The tolerance to use.
         * @return A collection of roots.
         */
        fun quartic(a: Float, b: Float, c: Float, d: Float, e: Float,
                    eps: Float = 1e-9f): List<Float> {
            val A = b / a
            val B = c / a
            val C = d / a
            val D = e / a

            val ASquared = A * A
            val p        = (-3f/8f) * ASquared + B
            val q        = (1f/8f) * ASquared * A - (1f/2f) * A * B + C
            val r        = (-3f/256f) * ASquared * ASquared +
                           (1f/16f) * ASquared * B - (1f/4f) * A * C + D

            val roots    = mutableListOf<Float>()

            if(isZero(r, eps)) {
                roots.addAll(cubic(1f, 0f, p, q))
            }
            else {
                val z = cubic(1f, (-1f/2f) * p, -r,
                              (1f/2f) * r * p - (1f/8f) * q * q).last()

                val u = z * z -r
                val v = 2 * z - p

                if((!isZero(u, eps) && u < 0f) ||
                   (!isZero(v, eps) && v < 0f)) {
                    return roots
                }

                val u2 = when(isZero(u, eps)) {
                    false -> MathUtils.sqrt(u)
                    true  -> 0f
                }
                val v2 = when(isZero(v, eps)) {
                    false -> MathUtils.sqrt(v)
                    true  -> 0f
                }

                val s = when(q < 0f) {
                    false -> v2
                    true  -> -v2
                }
                val t = when(q < 0f) {
                    false -> -v2
                    true  -> v2
                }

                roots.addAll(quadratic(1f, s, z - u2))
                roots.addAll(quadratic(1f, t, z + u2))
            }

            val sub = (1f/4f) * A

            return roots.map { it - sub }
        }
    }
}