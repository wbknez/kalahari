package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents a ray traceable scene.
 *
 * @property viewport
 *           The viewing plane.
 */
class Scene {

    @JvmField
    val viewport: Viewport = Viewport()
}