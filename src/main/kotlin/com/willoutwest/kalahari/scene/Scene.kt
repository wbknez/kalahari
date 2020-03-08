package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.intersect.Intersectable
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents a ray traceable scene.
 *
 * @property camera
 *           The camera.
 * @property root
 *           The root element of the scenegraph.
 * @property viewport
 *           The viewing plane.
 */
class Scene {

    @JvmField
    var camera: Camera = Camera("camera")

    @JvmField
    var root: Group = Group("root")

    @JvmField
    val viewport: Viewport = Viewport()
}