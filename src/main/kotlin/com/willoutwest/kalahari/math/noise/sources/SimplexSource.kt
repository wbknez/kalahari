package com.willoutwest.kalahari.math.noise.sources

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.noise.NoiseSource

/**
 * Represents an implementation of [NoiseSource] that uses a simplex grid (a
 * tetrahedron) to map random noise to an integer lattice.
 *
 * This implementation is taken directly from the Java-specific gist of
 * <code>OpenSimplexNoise.java</code>
 * found
 * here:
 *  https://gist.github.com/KdotJPG/b1270127455a94ac5d19
 *
 * No modifications were made except necessary Kotlin-specific conversions.
 */
class SimplexSource(seed: Long) : Cloneable, NoiseSource {

    companion object {

        /**
         * Represents a magic number (multiplicative).
         */
        const val A       = 6364136223846793005L

        /**
         * Represents another magic number (additive).
         */
        const val B       = 1442695040888963407L

        val Gradients3D   = byteArrayOf(
            -11,  4,  4,     -4,  11,  4,    -4,  4,  11,
            11,  4,  4,      4,  11,  4,     4,  4,  11,
            -11, -4,  4,     -4, -11,  4,    -4, -4,  11,
            11, -4,  4,      4, -11,  4,     4, -4,  11,
            -11,  4, -4,     -4,  11, -4,    -4,  4, -11,
            11,  4, -4,      4,  11, -4,     4,  4, -11,
            -11, -4, -4,     -4, -11, -4,    -4, -4, -11,
            11, -4, -4,      4, -11, -4,     4, -4, -11
        )

        /**
         * Represents a normalization constant.
         */
        const val Norm    = 103f

        /**
         * Represents a constriction constant.
         */
        const val Squish  = 1f / 3f

        /**
         * Represents an expansion constant.
         */
        const val Stretch = -1f / 6f
    }

    private val perm: ShortArray = ShortArray(256)
    
    private val gradPerm: ShortArray = ShortArray(256)

    /**
     * Constructor.
     * 
     * @param source
     *        The simplex source to copy from.
     */
    constructor(source: SimplexSource?) : this(0) {
        source!!.perm.copyInto(this.perm)
        source.gradPerm.copyInto(this.gradPerm)
    }
    
    init {
        val source     = ShortArray(256) { it.toShort() }
        var aSeed      = ((seed * A + B) * A + B) * A + B
        val gradLength = Gradients3D.size / 3

        for(i in this.perm.indices.reversed()) {
            aSeed = aSeed * A + B

            val v = ((seed + 31) % (i + 1)).toInt()
            val r = when(v < 0) {
                false -> v
                true  -> v + (i + 1)
            }
            
            this.perm[i]     = source[r]
            this.gradPerm[i] = ((this.perm[i] % gradLength) * 3).toShort()
            source[r]        = source[i]
        }
    }
    
    override fun clone(): SimplexSource = SimplexSource(this)

    private fun extrapolate(xsb: Int, ysb: Int, zsb: Int,
                            dx: Float, dy: Float,
                            dz: Float): Float {
        val index = this.gradPerm[
            this.perm[this.perm[xsb and 0xFF] + ysb and 0xFF] + zsb and 0xFF
        ].toInt()

        return Gradients3D[index] * dx + Gradients3D[index + 1] * dy +
               Gradients3D[index + 2] * dz
    }

    override fun output(x: Float, y: Float, z: Float): Float {
        val stretchOffset: Float = (x + y + z) * Stretch
        val xs = x + stretchOffset
        val ys = y + stretchOffset
        val zs = z + stretchOffset

        //Floor to get simplectic honeycomb coordinates of rhombohedron (stretched cube) super-cell origin.

        //Floor to get simplectic honeycomb coordinates of rhombohedron (stretched cube) super-cell origin.
        val xsb: Int = MathUtils.floori(xs)
        val ysb: Int = MathUtils.floori(ys)
        val zsb: Int = MathUtils.floori(zs)

        //Skew out to get actual coordinates of rhombohedron origin. We'll need these later.

        //Skew out to get actual coordinates of rhombohedron origin. We'll need these later.
        val squishOffset: Float = (xsb + ysb + zsb) * Squish
        val xb = xsb + squishOffset
        val yb = ysb + squishOffset
        val zb = zsb + squishOffset

        //Compute simplectic honeycomb coordinates relative to rhombohedral origin.

        //Compute simplectic honeycomb coordinates relative to rhombohedral origin.
        val xins = xs - xsb
        val yins = ys - ysb
        val zins = zs - zsb

        //Sum those together to get a value that determines which region we're in.

        //Sum those together to get a value that determines which region we're in.
        val inSum = xins + yins + zins

        //Positions relative to origin point.

        //Positions relative to origin point.
        var dx0 = x - xb
        var dy0 = y - yb
        var dz0 = z - zb

        //We'll be defining these inside the next block and using them afterwards.

        //We'll be defining these inside the next block and using them afterwards.
        val dx_ext0: Float
        var dy_ext0: Float
        val dz_ext0: Float
        var dx_ext1: Float
        var dy_ext1: Float
        var dz_ext1: Float
        val xsv_ext0: Int
        var ysv_ext0: Int
        val zsv_ext0: Int
        var xsv_ext1: Int
        var ysv_ext1: Int
        var zsv_ext1: Int

        var value = 0.0
        if (inSum <= 1) { //We're inside the tetrahedron (3-Simplex) at (0,0,0)

            //Determine which two of (0,0,1), (0,1,0), (1,0,0) are closest.
            var aPoint: Byte = 0x01
            var aScore = xins
            var bPoint: Byte = 0x02
            var bScore = yins
            if (aScore >= bScore && zins > bScore) {
                bScore = zins
                bPoint = 0x04
            }
            else if (aScore < bScore && zins > aScore) {
                aScore = zins
                aPoint = 0x04
            }

            //Now we determine the two lattice points not part of the tetrahedron that may contribute.
            //This depends on the closest two tetrahedral vertices, including (0,0,0)
            val wins = 1 - inSum
            if (wins > aScore || wins > bScore) { //(0,0,0) is one of the closest two tetrahedral vertices.
                val c =
                    if (bScore > aScore) bPoint.toInt() else aPoint.toInt()
                //Our other
                        // closest vertex is the closest out of a and b.
                if (c and 0x01 == 0) {
                    xsv_ext0 = xsb - 1
                    xsv_ext1 = xsb
                    dx_ext0 = dx0 + 1
                    dx_ext1 = dx0
                }
                else {
                    xsv_ext1 = xsb + 1
                    xsv_ext0 = xsv_ext1
                    dx_ext1 = dx0 - 1
                    dx_ext0 = dx_ext1
                }
                if (c and 0x02 == 0) {
                    ysv_ext1 = ysb
                    ysv_ext0 = ysv_ext1
                    dy_ext1 = dy0
                    dy_ext0 = dy_ext1
                    if (c and 0x01 == 0) {
                        ysv_ext1 -= 1
                        dy_ext1 += 1f
                    }
                    else {
                        ysv_ext0 -= 1
                        dy_ext0 += 1f
                    }
                }
                else {
                    ysv_ext1 = ysb + 1
                    ysv_ext0 = ysv_ext1
                    dy_ext1 = dy0 - 1
                    dy_ext0 = dy_ext1
                }
                if (c and 0x04 == 0) {
                    zsv_ext0 = zsb
                    zsv_ext1 = zsb - 1
                    dz_ext0 = dz0
                    dz_ext1 = dz0 + 1
                }
                else {
                    zsv_ext1 = zsb + 1
                    zsv_ext0 = zsv_ext1
                    dz_ext1 = dz0 - 1
                    dz_ext0 = dz_ext1
                }
            }
            else { //(0,0,0) is not one of the closest two tetrahedral vertices.
                val c = aPoint.toInt() or bPoint.toInt() //Our two extra
                // vertices
                // are
                // determined by the closest two.
                if (c and 0x01 == 0) {
                    xsv_ext0 = xsb
                    xsv_ext1 = xsb - 1
                    dx_ext0 = dx0 - 2 * Squish
                    dx_ext1 = dx0 + 1 - Squish
                }
                else {
                    xsv_ext1 = xsb + 1
                    xsv_ext0 = xsv_ext1
                    dx_ext0 = dx0 - 1 - 2 * Squish
                    dx_ext1 = dx0 - 1 - Squish
                }
                if (c and 0x02 == 0) {
                    ysv_ext0 = ysb
                    ysv_ext1 = ysb - 1
                    dy_ext0 = dy0 - 2 * Squish
                    dy_ext1 = dy0 + 1 - Squish
                }
                else {
                    ysv_ext1 = ysb + 1
                    ysv_ext0 = ysv_ext1
                    dy_ext0 = dy0 - 1 - 2 * Squish
                    dy_ext1 = dy0 - 1 - Squish
                }
                if (c and 0x04 == 0) {
                    zsv_ext0 = zsb
                    zsv_ext1 = zsb - 1
                    dz_ext0 = dz0 - 2 * Squish
                    dz_ext1 = dz0 + 1 - Squish
                }
                else {
                    zsv_ext1 = zsb + 1
                    zsv_ext0 = zsv_ext1
                    dz_ext0 = dz0 - 1 - 2 * Squish
                    dz_ext1 = dz0 - 1 - Squish
                }
            }

            //Contribution (0,0,0)
            var attn0 =
                2 - dx0 * dx0 - dy0 * dy0 - dz0 * dz0
            if (attn0 > 0) {
                attn0 *= attn0
                value += attn0 * attn0 * extrapolate(xsb + 0, ysb + 0, zsb + 0,
                                                     dx0, dy0, dz0)
            }

            //Contribution (1,0,0)
            val dx1: Float = dx0 - 1 - Squish
            val dy1: Float = dy0 - 0 - Squish
            val dz1: Float = dz0 - 0 - Squish
            var attn1 =
                2 - dx1 * dx1 - dy1 * dy1 - dz1 * dz1
            if (attn1 > 0) {
                attn1 *= attn1
                value += attn1 * attn1 * extrapolate(xsb + 1, ysb + 0, zsb + 0,
                                                     dx1, dy1, dz1)
            }

            //Contribution (0,1,0)
            val dx2: Float = dx0 - 0 - Squish
            val dy2: Float = dy0 - 1 - Squish
            var attn2 =
                2 - dx2 * dx2 - dy2 * dy2 - dz1 * dz1
            if (attn2 > 0) {
                attn2 *= attn2
                value += attn2 * attn2 * extrapolate(xsb + 0, ysb + 1, zsb + 0,
                                                     dx2, dy2, dz1)
            }

            //Contribution (0,0,1)
            val dz3: Float = dz0 - 1 - Squish
            var attn3 =
                2 - dx2 * dx2 - dy1 * dy1 - dz3 * dz3
            if (attn3 > 0) {
                attn3 *= attn3
                value += attn3 * attn3 * extrapolate(xsb + 0, ysb + 0, zsb + 1,
                                                     dx2, dy1, dz3)
            }
        }
        else if (inSum >= 2) { //We're inside the tetrahedron (3-Simplex) at (1,1,1)

            //Determine which two tetrahedral vertices are the closest, out of (1,1,0), (1,0,1), (0,1,1) but not (1,1,1).
            var aPoint: Byte = 0x06
            var aScore = xins
            var bPoint: Byte = 0x05
            var bScore = yins
            if (aScore <= bScore && zins < bScore) {
                bScore = zins
                bPoint = 0x03
            }
            else if (aScore > bScore && zins < aScore) {
                aScore = zins
                aPoint = 0x03
            }

            //Now we determine the two lattice points not part of the tetrahedron that may contribute.
            //This depends on the closest two tetrahedral vertices, including (1,1,1)
            val wins = 3 - inSum
            if (wins < aScore || wins < bScore) { //(1,1,1) is one of the closest two tetrahedral vertices.
                val c =
                    if (bScore < aScore) bPoint.toInt() else aPoint.toInt()
                //Our other
                        // closest vertex is the closest out of a and b.
                if (c and 0x01 != 0) {
                    xsv_ext0 = xsb + 2
                    xsv_ext1 = xsb + 1
                    dx_ext0 = dx0 - 2 - 3 * Squish
                    dx_ext1 = dx0 - 1 - 3 * Squish
                }
                else {
                    xsv_ext1 = xsb
                    xsv_ext0 = xsv_ext1
                    dx_ext1 = dx0 - 3 * Squish
                    dx_ext0 = dx_ext1
                }
                if (c and 0x02 != 0) {
                    ysv_ext1 = ysb + 1
                    ysv_ext0 = ysv_ext1
                    dy_ext1 = dy0 - 1 - 3 * Squish
                    dy_ext0 = dy_ext1
                    if (c and 0x01 != 0) {
                        ysv_ext1 += 1
                        dy_ext1 -= 1f
                    }
                    else {
                        ysv_ext0 += 1
                        dy_ext0 -= 1f
                    }
                }
                else {
                    ysv_ext1 = ysb
                    ysv_ext0 = ysv_ext1
                    dy_ext1 = dy0 - 3 * Squish
                    dy_ext0 = dy_ext1
                }
                if (c and 0x04 != 0) {
                    zsv_ext0 = zsb + 1
                    zsv_ext1 = zsb + 2
                    dz_ext0 = dz0 - 1 - 3 * Squish
                    dz_ext1 = dz0 - 2 - 3 * Squish
                }
                else {
                    zsv_ext1 = zsb
                    zsv_ext0 = zsv_ext1
                    dz_ext1 = dz0 - 3 * Squish
                    dz_ext0 = dz_ext1
                }
            }
            else { //(1,1,1) is not one of the closest two tetrahedral vertices.
                val c =
                    aPoint.toInt() and bPoint.toInt() //Our two extra
                // vertices are determined by the closest two.
                if (c and 0x01 != 0) {
                    xsv_ext0 = xsb + 1
                    xsv_ext1 = xsb + 2
                    dx_ext0 = dx0 - 1 - Squish
                    dx_ext1 = dx0 - 2 - 2 * Squish
                }
                else {
                    xsv_ext1 = xsb
                    xsv_ext0 = xsv_ext1
                    dx_ext0 = dx0 - Squish
                    dx_ext1 = dx0 - 2 * Squish
                }
                if (c and 0x02 != 0) {
                    ysv_ext0 = ysb + 1
                    ysv_ext1 = ysb + 2
                    dy_ext0 = dy0 - 1 - Squish
                    dy_ext1 = dy0 - 2 - 2 * Squish
                }
                else {
                    ysv_ext1 = ysb
                    ysv_ext0 = ysv_ext1
                    dy_ext0 = dy0 - Squish
                    dy_ext1 = dy0 - 2 * Squish
                }
                if (c and 0x04 != 0) {
                    zsv_ext0 = zsb + 1
                    zsv_ext1 = zsb + 2
                    dz_ext0 = dz0 - 1 - Squish
                    dz_ext1 = dz0 - 2 - 2 * Squish
                }
                else {
                    zsv_ext1 = zsb
                    zsv_ext0 = zsv_ext1
                    dz_ext0 = dz0 - Squish
                    dz_ext1 = dz0 - 2 * Squish
                }
            }

            //Contribution (1,1,0)
            val dx3: Float = dx0 - 1 - 2 * Squish
            val dy3: Float = dy0 - 1 - 2 * Squish
            val dz3: Float = dz0 - 0 - 2 * Squish
            var attn3 =
                2 - dx3 * dx3 - dy3 * dy3 - dz3 * dz3
            if (attn3 > 0) {
                attn3 *= attn3
                value += attn3 * attn3 * extrapolate(xsb + 1, ysb + 1, zsb + 0,
                                                     dx3, dy3, dz3)
            }

            //Contribution (1,0,1)
            val dy2: Float = dy0 - 0 - 2 * Squish
            val dz2: Float = dz0 - 1 - 2 * Squish
            var attn2 =
                2 - dx3 * dx3 - dy2 * dy2 - dz2 * dz2
            if (attn2 > 0) {
                attn2 *= attn2
                value += attn2 * attn2 * extrapolate(xsb + 1, ysb + 0, zsb + 1,
                                                     dx3, dy2, dz2)
            }

            //Contribution (0,1,1)
            val dx1: Float = dx0 - 0 - 2 * Squish
            var attn1 =
                2 - dx1 * dx1 - dy3 * dy3 - dz2 * dz2
            if (attn1 > 0) {
                attn1 *= attn1
                value += attn1 * attn1 * extrapolate(xsb + 0, ysb + 1, zsb + 1,
                                                     dx1, dy3, dz2)
            }

            //Contribution (1,1,1)
            dx0 = dx0 - 1 - 3 * Squish
            dy0 = dy0 - 1 - 3 * Squish
            dz0 = dz0 - 1 - 3 * Squish
            var attn0 =
                2 - dx0 * dx0 - dy0 * dy0 - dz0 * dz0
            if (attn0 > 0) {
                attn0 *= attn0
                value += attn0 * attn0 * extrapolate(xsb + 1, ysb + 1, zsb + 1,
                                                     dx0, dy0, dz0)
            }
        }
        else { //We're inside the octahedron (Rectified 3-Simplex) in between.
            val aScore: Float
            var aPoint: Byte
            var aIsFurtherSide: Boolean
            val bScore: Float
            var bPoint: Byte
            var bIsFurtherSide: Boolean

            //Decide between point (0,0,1) and (1,1,0) as closest
            val p1 = xins + yins
            if (p1 > 1) {
                aScore = p1 - 1
                aPoint = 0x03
                aIsFurtherSide = true
            }
            else {
                aScore = 1 - p1
                aPoint = 0x04
                aIsFurtherSide = false
            }

            //Decide between point (0,1,0) and (1,0,1) as closest
            val p2 = xins + zins
            if (p2 > 1) {
                bScore = p2 - 1
                bPoint = 0x05
                bIsFurtherSide = true
            }
            else {
                bScore = 1 - p2
                bPoint = 0x02
                bIsFurtherSide = false
            }

            //The closest out of the two (1,0,0) and (0,1,1) will replace the furthest out of the two decided above, if closer.
            val p3 = yins + zins
            if (p3 > 1) {
                val score = p3 - 1
                if (aScore <= bScore && aScore < score) {
                    aPoint = 0x06
                    aIsFurtherSide = true
                }
                else if (aScore > bScore && bScore < score) {
                    bPoint = 0x06
                    bIsFurtherSide = true
                }
            }
            else {
                val score = 1 - p3
                if (aScore <= bScore && aScore < score) {
                    aPoint = 0x01
                    aIsFurtherSide = false
                }
                else if (aScore > bScore && bScore < score) {
                    bPoint = 0x01
                    bIsFurtherSide = false
                }
            }

            //Where each of the two closest points are determines how the extra two vertices are calculated.
            if (aIsFurtherSide == bIsFurtherSide) {
                if (aIsFurtherSide) { //Both closest points on (1,1,1) side

                    //One of the two extra points is (1,1,1)
                    dx_ext0 = dx0 - 1 - 3 * Squish
                    dy_ext0 = dy0 - 1 - 3 * Squish
                    dz_ext0 = dz0 - 1 - 3 * Squish
                    xsv_ext0 = xsb + 1
                    ysv_ext0 = ysb + 1
                    zsv_ext0 = zsb + 1

                    //Other extra point is based on the shared axis.
                    val c = aPoint.toInt() and bPoint.toInt()
                    if (c and 0x01 != 0) {
                        dx_ext1 = dx0 - 2 - 2 * Squish
                        dy_ext1 = dy0 - 2 * Squish
                        dz_ext1 = dz0 - 2 * Squish
                        xsv_ext1 = xsb + 2
                        ysv_ext1 = ysb
                        zsv_ext1 = zsb
                    }
                    else if (c and 0x02 != 0) {
                        dx_ext1 = dx0 - 2 * Squish
                        dy_ext1 = dy0 - 2 - 2 * Squish
                        dz_ext1 = dz0 - 2 * Squish
                        xsv_ext1 = xsb
                        ysv_ext1 = ysb + 2
                        zsv_ext1 = zsb
                    }
                    else {
                        dx_ext1 = dx0 - 2 * Squish
                        dy_ext1 = dy0 - 2 * Squish
                        dz_ext1 = dz0 - 2 - 2 * Squish
                        xsv_ext1 = xsb
                        ysv_ext1 = ysb
                        zsv_ext1 = zsb + 2
                    }
                }
                else { //Both closest points on (0,0,0) side

                    //One of the two extra points is (0,0,0)
                    dx_ext0 = dx0
                    dy_ext0 = dy0
                    dz_ext0 = dz0
                    xsv_ext0 = xsb
                    ysv_ext0 = ysb
                    zsv_ext0 = zsb

                    //Other extra point is based on the omitted axis.
                    val c = aPoint.toInt() or bPoint.toInt()
                    if (c and 0x01 == 0) {
                        dx_ext1 = dx0 + 1 - Squish
                        dy_ext1 = dy0 - 1 - Squish
                        dz_ext1 = dz0 - 1 - Squish
                        xsv_ext1 = xsb - 1
                        ysv_ext1 = ysb + 1
                        zsv_ext1 = zsb + 1
                    }
                    else if (c and 0x02 == 0) {
                        dx_ext1 = dx0 - 1 - Squish
                        dy_ext1 = dy0 + 1 - Squish
                        dz_ext1 = dz0 - 1 - Squish
                        xsv_ext1 = xsb + 1
                        ysv_ext1 = ysb - 1
                        zsv_ext1 = zsb + 1
                    }
                    else {
                        dx_ext1 = dx0 - 1 - Squish
                        dy_ext1 = dy0 - 1 - Squish
                        dz_ext1 = dz0 + 1 - Squish
                        xsv_ext1 = xsb + 1
                        ysv_ext1 = ysb + 1
                        zsv_ext1 = zsb - 1
                    }
                }
            }
            else { //One point on (0,0,0) side, one point on (1,1,1) side
                val c1: Int
                val c2: Int
                if (aIsFurtherSide) {
                    c1 = aPoint.toInt()
                    c2 = bPoint.toInt()
                }
                else {
                    c1 = bPoint.toInt()
                    c2 = aPoint.toInt()
                }

                //One contribution is a permutation of (1,1,-1)
                if (c1 and 0x01 == 0) {
                    dx_ext0 = dx0 + 1 - Squish
                    dy_ext0 = dy0 - 1 - Squish
                    dz_ext0 = dz0 - 1 - Squish
                    xsv_ext0 = xsb - 1
                    ysv_ext0 = ysb + 1
                    zsv_ext0 = zsb + 1
                }
                else if (c1 and 0x02 == 0) {
                    dx_ext0 = dx0 - 1 - Squish
                    dy_ext0 = dy0 + 1 - Squish
                    dz_ext0 = dz0 - 1 - Squish
                    xsv_ext0 = xsb + 1
                    ysv_ext0 = ysb - 1
                    zsv_ext0 = zsb + 1
                }
                else {
                    dx_ext0 = dx0 - 1 - Squish
                    dy_ext0 = dy0 - 1 - Squish
                    dz_ext0 = dz0 + 1 - Squish
                    xsv_ext0 = xsb + 1
                    ysv_ext0 = ysb + 1
                    zsv_ext0 = zsb - 1
                }

                //One contribution is a permutation of (0,0,2)
                dx_ext1 = dx0 - 2 * Squish
                dy_ext1 = dy0 - 2 * Squish
                dz_ext1 = dz0 - 2 * Squish
                xsv_ext1 = xsb
                ysv_ext1 = ysb
                zsv_ext1 = zsb
                if (c2 and 0x01 != 0) {
                    dx_ext1 -= 2f
                    xsv_ext1 += 2
                }
                else if (c2 and 0x02 != 0) {
                    dy_ext1 -= 2f
                    ysv_ext1 += 2
                }
                else {
                    dz_ext1 -= 2f
                    zsv_ext1 += 2
                }
            }

            //Contribution (1,0,0)
            val dx1: Float = dx0 - 1 - Squish
            val dy1: Float = dy0 - 0 - Squish
            val dz1: Float = dz0 - 0 - Squish
            var attn1 =
                2 - dx1 * dx1 - dy1 * dy1 - dz1 * dz1
            if (attn1 > 0) {
                attn1 *= attn1
                value += attn1 * attn1 * extrapolate(xsb + 1, ysb + 0, zsb + 0,
                                                     dx1, dy1, dz1)
            }

            //Contribution (0,1,0)
            val dx2: Float = dx0 - 0 - Squish
            val dy2: Float = dy0 - 1 - Squish
            var attn2 =
                2 - dx2 * dx2 - dy2 * dy2 - dz1 * dz1
            if (attn2 > 0) {
                attn2 *= attn2
                value += attn2 * attn2 * extrapolate(xsb + 0, ysb + 1, zsb + 0,
                                                     dx2, dy2, dz1)
            }

            //Contribution (0,0,1)
            val dz3: Float = dz0 - 1 - Squish
            var attn3 =
                2 - dx2 * dx2 - dy1 * dy1 - dz3 * dz3
            if (attn3 > 0) {
                attn3 *= attn3
                value += attn3 * attn3 * extrapolate(xsb + 0, ysb + 0, zsb + 1,
                                                     dx2, dy1, dz3)
            }

            //Contribution (1,1,0)
            val dx4: Float = dx0 - 1 - 2 * Squish
            val dy4: Float = dy0 - 1 - 2 * Squish
            val dz4: Float = dz0 - 0 - 2 * Squish
            var attn4 =
                2 - dx4 * dx4 - dy4 * dy4 - dz4 * dz4
            if (attn4 > 0) {
                attn4 *= attn4
                value += attn4 * attn4 * extrapolate(xsb + 1, ysb + 1, zsb + 0,
                                                     dx4, dy4, dz4)
            }

            //Contribution (1,0,1)
            val dy5: Float = dy0 - 0 - 2 * Squish
            val dz5: Float = dz0 - 1 - 2 * Squish
            var attn5 =
                2 - dx4 * dx4 - dy5 * dy5 - dz5 * dz5
            if (attn5 > 0) {
                attn5 *= attn5
                value += attn5 * attn5 * extrapolate(xsb + 1, ysb + 0, zsb + 1,
                                                     dx4, dy5, dz5)
            }

            //Contribution (0,1,1)
            val dx6: Float = dx0 - 0 - 2 * Squish
            var attn6 =
                2 - dx6 * dx6 - dy4 * dy4 - dz5 * dz5
            if (attn6 > 0) {
                attn6 *= attn6
                value += attn6 * attn6 * extrapolate(xsb + 0, ysb + 1, zsb + 1,
                                                     dx6, dy4, dz5)
            }
        }

        //First extra vertex

        //First extra vertex
        var attn_ext0 =
            2 - dx_ext0 * dx_ext0 - dy_ext0 * dy_ext0 - dz_ext0 * dz_ext0
        if (attn_ext0 > 0) {
            attn_ext0 *= attn_ext0
            value += attn_ext0 * attn_ext0 * extrapolate(xsv_ext0, ysv_ext0,
                                                         zsv_ext0, dx_ext0,
                                                         dy_ext0, dz_ext0)
        }

        //Second extra vertex

        //Second extra vertex
        var attn_ext1 =
            2 - dx_ext1 * dx_ext1 - dy_ext1 * dy_ext1 - dz_ext1 * dz_ext1
        if (attn_ext1 > 0) {
            attn_ext1 *= attn_ext1
            value += attn_ext1 * attn_ext1 * extrapolate(xsv_ext1, ysv_ext1,
                                                         zsv_ext1, dx_ext1,
                                                         dy_ext1, dz_ext1)
        }

        return (value / Norm).toFloat()
    }
}