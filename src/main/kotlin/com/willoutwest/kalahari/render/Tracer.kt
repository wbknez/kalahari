package com.willoutwest.kalahari.render

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.intersect.BoundingSphere
import com.willoutwest.kalahari.render.orders.NaturalDrawingOrder
import com.willoutwest.kalahari.scene.Scene

/**
 * Represents a mechanism for computing the reflected color of light based on
 * the path of a single ray cast into a scene.
 *
 * The sole tracing mechanism implemented in this project is the Whitted
 * tracer, which allows the eventual determiniation of the color of reflective
 * and transparent surfaces through the use of recursive ray generation.
 *
 * @property drawOrder
 *           The ordering of pixels for drawing.
 * @property hEps
 *           The collection of per-geometry epsilon values that denote the
 *           parametric minimum for a valid ray-to-surface intersection.
 * @property sEps
 *           The collection of per-geometry epsilon values that denote the
 *           parametric minimum for a valid shadow ray-to-surface intersection.
 */
class Tracer(@JvmField var drawOrder: DrawingOrder = NaturalDrawingOrder(),
             @JvmField val hEps: EpsilonTable = EpsilonTable(0.0001f),
             @JvmField val sEps: EpsilonTable = EpsilonTable(0.0001f)) {

    private val red    = Color3(1f, 0f, 0f)
    private val orange = Color3(1f, 0.7f, 0f)
    private val yellow = Color3(1f, 1f, 0f)
    private val green  = Color3(0f, 0.7f, 0f)
    private val blue   = Color3(0f, 0f, 1f)
    private val white  = Color3(1f, 1f, 1f)


    private val bsRed    = BoundingSphere(101f, 150f, 150f, 0f)
    private val bsYellow = BoundingSphere(101f, 250f, 250f, 0f)
    private val bsBlue   = BoundingSphere(101f, 350f, 350f, 0f)

    /**
     *
     *
     * @param ray
     *
     * @param scene
     *
     * @return
     */
    fun trace(coords: Coords, scene: Scene): Color3 {
        val point = Point3(coords.x.toFloat(), coords.y.toFloat(), 0f)

        val inRed    = this.bsRed.contains(point)
        val inYellow = this.bsYellow.contains(point)
        val inBlue   = this.bsBlue.contains(point)

        val inOrange = inRed && inYellow
        val inGreen  = inYellow && inBlue

        if(inOrange) {
            return this.orange
        }
        else if(inGreen) {
            return this.green
        }
        else if(inRed) {
            return this.red
        }
        else if(inYellow) {
            return this.yellow
        }
        else if(inBlue) {
            return this.blue
        }
        else {
            return this.white
        }
    }
}