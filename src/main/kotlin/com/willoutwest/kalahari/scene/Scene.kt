package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.intersect.Intersectable
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents a ray traceable scene.
 *
 * @property viewport
 *           The viewing plane.
 * @property root
 *           The root element of the scenegraph.
 */
class Scene {

    @JvmField
    var root: Group = Group("root")

    @JvmField
    val viewport: Viewport = Viewport()
}