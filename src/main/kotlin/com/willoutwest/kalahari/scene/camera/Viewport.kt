package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ColorOperator
import com.willoutwest.kalahari.math.operators.ClampingColorOperator
import com.willoutwest.kalahari.math.sample.Sampler
import com.willoutwest.kalahari.math.sample.Sampler2
import com.willoutwest.kalahari.math.sample.generators.JitteredSampleGenerator
import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.util.hash

/**
 * Represents a single "view" of a scene given by a pixel-filled plane.
 *
 * @property bgColor
 *           The background color.
 * @property bounds
 *           The viewing bounds.
 * @property gamma
 *           The gamma correction.
 * @property gamutOp
 *           The corrective mechanism to use when a color is out of gamut.
 * @property invGamma
 *           The inverse gamma correction.
 * @property maxDepth
 *           The maximum recursive tracing depth.
 * @property pixelSize
 *           The sample area of a pixel.
 * @property sampler
 *           The sampling mechanism to reduce anti-aliasing.
 */
class Viewport(var bgColor: Color3 = Color3(0f, 0f, 0f),
               var bounds: Bounds = Bounds(
                   0, 0, 600, 400),
               gamma: Float = 1f,
               var gamutOp: ColorOperator = ClampingColorOperator(),
               var maxDepth: Int = 1,
               var pixelSize: Float = 1f,
               var sampler: Sampler2 = Sampler.squareOf(
                   1, 83, JitteredSampleGenerator()
               )) : Cloneable {

    var gamma: Float    = 1f
        set(value) {
            require(value != 0f) {
                "Gamma correction cannot be zero."
            }

            this.invGamma = 1f / value

            field = value
        }

    var invGamma: Float = 1f
        private set

    /**
     * Constructor.
     *
     * @param viewport
     *        The viewport to copy from.
     */
    constructor(viewport: Viewport?) :
        this(viewport!!.bgColor, viewport.bounds, viewport.gamma,
             viewport.gamutOp, viewport.maxDepth, viewport.pixelSize,
             viewport.sampler)

    init {
        this.gamma = gamma
    }

    public override fun clone(): Viewport =
        Viewport(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Viewport -> this.bgColor == other.bgColor &&
                           this.bounds == other.bounds &&
                           this.gamma == other.gamma &&
                           this.gamutOp == other.gamutOp &&
                           this.invGamma == other.invGamma &&
                           this.maxDepth == other.maxDepth &&
                           this.pixelSize == other.pixelSize &&
                           this.sampler == other.sampler
            else           -> false
        }

    override fun hashCode(): Int = hash(this.bgColor, this.bounds,
                                        this.gamma, this.gamutOp,
                                        this.maxDepth, this.pixelSize,
                                        this.sampler)
}