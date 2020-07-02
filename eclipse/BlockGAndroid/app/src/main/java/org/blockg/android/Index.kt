package org.blockg.android

import android.opengl.GLES20.*
import java.nio.IntBuffer

class Index(val array: IntArray) {
    var ID = 0
    var binded = false
        set(value) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, if(value) ID else 0)
            field = value
        }
    fun create(){
        val buffer = IntBuffer.wrap(array)
        buffer.flip()
       run {
            val buffera = intArrayOf()
            glGenBuffers(1, buffera, 0)
            ID = buffera[0]
        }
        binded = true
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer.limit(), buffer, GL_DYNAMIC_DRAW)
        binded = false
    }
    fun clean(){

    }
}
