package org.blockg.android

import android.opengl.GLES20.*
import android.opengl.GLES30
import android.opengl.GLES30.glBindVertexArray
import android.opengl.GLES30.glGenVertexArrays
import org.joml.Vector3f
import java.nio.FloatBuffer

class VertexArrayObject(val VBO: VertexBufferObject, val index: Int) {
    private var vaoID = 0
    fun updateData() {
        val buffer = FloatBuffer.wrap(VBO.array)
        buffer.flip()
        glBindBuffer(GL_ARRAY_BUFFER, VBO.ID)
        glBufferData(GL_ARRAY_BUFFER, buffer.limit(), buffer, GLES30.GL_DYNAMIC_DRAW)
        glVertexAttribPointer(index, VBO.vectorCount, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    var transform = Transform(Vector3f(), Vector3f(), Vector3f(1f))
    var enable = false
        set(value) {
            if (value)
                glEnableVertexAttribArray(index)
            else
                glDisableVertexAttribArray(index)
            field = value
        }
    var bind = false
        set(value) {
            glBindVertexArray(if (value) vaoID else 0)
            field = value
        }

    fun createVAO() {
        var vao = intArrayOf()
        glGenVertexArrays(1, vao, 0)
        vaoID = vao[0]
    }

    fun createVBO() {
        if (vaoID != 0)
            bind = true

        val buffer = FloatBuffer.wrap(VBO.array)
        buffer.flip()
        VBO.ID = storeData(buffer, index, VBO.vectorCount)
        if (VBO.index != null)
            VBO.index.create()
        if (vaoID != 0)
            bind = false


    }

    companion object {
        fun storeData(buffer: FloatBuffer, index: Int, size: Int): Int {
            var buffera = intArrayOf()
            glGenBuffers(1, buffera, 0)
            val bufferID = buffera[0]
            glBindBuffer(GL_ARRAY_BUFFER, bufferID)
            glBufferData(GL_ARRAY_BUFFER, buffer.limit(), buffer, GLES30.GL_DYNAMIC_DRAW)
            glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0)
            glBindBuffer(GL_ARRAY_BUFFER, 0)
            return bufferID
        }

    }

    fun clean() {

    }
}
