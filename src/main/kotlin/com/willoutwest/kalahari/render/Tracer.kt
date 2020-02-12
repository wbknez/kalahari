package com.willoutwest.kalahari.render

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Ray3
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

    fun trace(ray: Ray3, scene: Scene, depth: Int): Color3 = Color3()
}