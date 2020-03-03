package com.willoutwest.kalahari.scene.camera

/**
 * Represents a mechanism for determining how stereoscopic images should be
 * displayed on screen for viewing.
 */
enum class StereoMode {

    /**
     * Represents a stereoscopic viewing mode whereby the left-eye image is
     * on the left and the right-eye image is on the right.
     */
    Parallel,

    /**
     * Represents a stereoscopic viewing mode whereby the left-eye image is
     * on the right and the right-eye image is on the left.
     */
    Transverse
}