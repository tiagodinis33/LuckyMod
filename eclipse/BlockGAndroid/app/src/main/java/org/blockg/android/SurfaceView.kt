package org.blockg.android

import android.content.Context
import android.opengl.GLSurfaceView

class SurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: SurfaceRenderer

    init {


        renderer = SurfaceRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}