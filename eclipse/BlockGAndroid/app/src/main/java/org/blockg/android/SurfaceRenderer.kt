package org.blockg.android

import android.opengl.*
import android.opengl.GLES20.*
import org.joml.Matrix4f
import javax.microedition.khronos.opengles.GL10

class SurfaceRenderer : GLSurfaceView.Renderer {
    private var shader: ShaderProgram = ShaderProgram("vertex.glsl", "frag.glsl")
    private val index = Index(
        intArrayOf(
            0,1,3,
            3,1,2,
            4,5,7,
            7,5,6,
            8,9,11,
            11,9,10,
            12,13,15,
            15,13,14,
            16,17,19,
            19,17,18,
            20,21,23,
            23,21,22
        )
    )
    private val VBO = VertexBufferObject(
        index, floatArrayOf(
            -0.5f,0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,0.5f,-0.5f,

            -0.5f,0.5f,0.5f,
            -0.5f,-0.5f,0.5f,
            0.5f,-0.5f,0.5f,
            0.5f,0.5f,0.5f,

            0.5f,0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f,
            0.5f,0.5f,0.5f,

            -0.5f,0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            -0.5f,-0.5f,0.5f,
            -0.5f,0.5f,0.5f,

            -0.5f,0.5f,0.5f,
            -0.5f,0.5f,-0.5f,
            0.5f,0.5f,-0.5f,
            0.5f,0.5f,0.5f,

            -0.5f,-0.5f,0.5f,
            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f
        ), 3
    )
    /*
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f
    */
    private val TBO = VertexBufferObject(
        null, floatArrayOf(
            0F,0F,
            0F,1F,
            1F,1F,
            1F,0F,
            0F,0F,
            0F,1F,
            1F,1F,
            1F,0F,
            0F,0F,
            0F,1F,
            1F,1F,
            1F,0F,
            0F,0F,
            0F,1F,
            1F,1F,
            1F,0F,
            0F,0F,
            0F,1F,
            1F,1F,
            1F,0F,
            0F,0F,
            0F,1F,
            1F,1F,
            1F,0F
        ), 2
    )

    private val VAO = VertexArrayObject(VBO, 0)
    private val TAO = VertexArrayObject(TBO, 1)
    private val texture = Texture("teste.png", Texture.MipMapType.PIXELATED)
    private val renderer = Renderer(shader, arrayOf(VAO, TAO))
    override fun onSurfaceCreated(unused: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_TEXTURE_2D)
        texture.create()
        VAO.createVAO()
        VAO.createVBO()
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)
        renderer.render(texture)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)

    }
    companion object{
        var width: Float = 0f
            private set
        var height: Float = 0f
            private set
        val projection: Matrix4f
            get() {
                return Matrix4f().perspective(
                    70f,
                    width / height,
                    0.1f,
                    1000f
                )
            }
    }
}
