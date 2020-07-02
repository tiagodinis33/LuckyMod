package org.blockg.android

import android.opengl.GLES20.*
import org.joml.Matrix4f

class Renderer(val shader: ShaderProgram, val VAOs: Array<VertexArrayObject>) {
    fun render(texture: Texture) {
        VAOs[0].bind = true
        for(VAO in VAOs) {
            VAO.enable = true
        }
        VAOs[0].VBO.index?.binded = true
        texture.binded = true
        shader.bind()
        shader.setUniform("transform", Matrix4f().translate(VAOs[0].transform.pos).rotateYXZ(VAOs[0].transform.rot).scale(VAOs[0].transform.scale))
        shader.setUniform("proj", SurfaceRenderer.projection)
        shader.setUniform("view", Camera.matrix)
        shader.setUniform("slot", texture.slot)
        glDrawElements(GL_TRIANGLES, VAOs[0].VBO.index!!.array.size, GL_UNSIGNED_INT, 0)
        shader.unbind()
        texture.binded = false
        VAOs[0].VBO.index?.binded = false
        for(VAO in VAOs) {
            VAO.enable = false
        }
        VAOs[0].bind = false
    }
}