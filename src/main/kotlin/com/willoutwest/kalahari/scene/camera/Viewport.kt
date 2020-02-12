package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ColorOperator
import com.willoutwest.kalahari.math.operators.ClampingColorOperator
import com.willoutwest.kalahari.math.sample.Sampler
import com.willoutwest.kalahari.math.sample.Sampler2
import com.willoutwest.kalahari.math.sample.generators.JitteredSampleGenerator
import com.willoutwest.kalahari.render.Bounds

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
data class Viewport(@JvmField var bgColor: Color3 = Color3(0f, 0f, 0f),
                    @JvmField var bounds: Bounds = Bounds(600, 400),
                    @JvmField var gamma: Float = 1f,
                    @JvmField var gamutOp: ColorOperator =
                        ClampingColorOperator(),
                    @JvmField var invGamma: Float = 1f,
                    @JvmField var maxDepth: Int = 1,
                    @JvmField var pixelSize: Float = 1f,
                    @JvmField var sampler: Sampler2 = Sampler.squareOf(
                       1, 83, JitteredSampleGenerator()
                    )) : Cloneable {

    /**
     * Constructor.
     *
     * @param viewport
     *        The viewport to copy from.
     */
    constructor(viewport: Viewport?) :
        this(viewport!!.bgColor, viewport.bounds, viewport.gamma,
             viewport.gamutOp, viewport.invGamma, viewport.maxDepth,
             viewport.pixelSize, viewport.sampler)

    public override fun clone(): Viewport = Viewport(this)
}