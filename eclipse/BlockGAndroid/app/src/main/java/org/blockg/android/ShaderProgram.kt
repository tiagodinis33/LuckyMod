package org.blockg.android

import android.opengl.GLES20.*
import org.apache.logging.log4j.LogManager
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import java.io.BufferedReader
import java.io.InputStreamReader



open class ShaderProgram(val vertexPath: String, val fragPath: String) {
    var programId = 0
    private var vertexShaderId = 0
    private var fragmentShaderId = 0

    @Throws(Exception::class)
    private fun createVertexShader(shaderCode: String) {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }
    fun setUniformArray(loc: String,  arr: IntArray){
        var locID: Int
        if (glGetUniformLocation(programId, loc).also { locID = it } == -1) {
            LogManager.getLogger()
                .error("Não foi possivel localizar o uniform \"$loc\" no programa GLSL!", IllegalArgumentException())
        }
        glUniform1iv(locID,1, arr,0);
    }
    fun setUniform(loc: String, m: Matrix4f) {
        val v = floatArrayOf(
            m[0,0], m[1,0], m[2,0], m[3,0],
            m[0,1], m[1,1], m[2,1], m[3,1],
            m[0,2], m[1,2], m[2,2], m[3,2],
            m[0,3], m[1,3], m[2,3], m[3,3]
        )
        var locID: Int
        if (glGetUniformLocation(programId, loc).also { locID = it } == -1) {
            LogManager.getLogger()
                .error("Não foi possivel localizar o uniform \"$loc\" no programa GLSL!", IllegalArgumentException())
        }
        glUniformMatrix4fv(locID,1, true, v,0)
    }

    fun setUniform(loc: String, value: Int) {
        var locID: Int
        if (glGetUniformLocation(programId, loc).also { locID = it } == 0) {
            LogManager.getLogger()
                .error("Não foi possivel localizar o uniform \"$loc\" no programa GLSL!", IllegalArgumentException())
        }
        glUniform1i(locID, value)
    }

    fun setUniform(loc: String, value: Float) {
        var locID : Int
        if (glGetUniformLocation(programId, loc).also { locID = it } == 0) {
            LogManager.getLogger()
                .error("Não foi possivel localizar o uniform \"$loc\" no programa GLSL!", IllegalArgumentException())
        }
        glUniform1f(locID, value)
    }

    fun setUniform(loc: String, value: Vector3f) {
        var locID : Int
        if (glGetUniformLocation(programId, loc).also { locID = it } == 0) {
            LogManager.getLogger()
                .error("Não foi possivel localizar o uniform \"$loc\" no programa GLSL!", IllegalArgumentException())
        }
        glUniform3f(locID, value.x, value.y, value.z)
    }

    fun setUniform(loc: String, value: Vector2f) {
        var locID : Int
        if (glGetUniformLocation(programId, loc).also { locID = it } == 0) {
            LogManager.getLogger()
                .error("Não foi possivel localizar o uniform \"$loc\" no programa GLSL!", IllegalArgumentException())
        }
        glUniform2f(locID, value.x, value.y)
    }

    fun setUniform(loc: String, value1: Int, value2: Int) {
        var locID : Int
        if (glGetUniformLocation(programId, loc).also { locID = it } == 0) {
            LogManager.getLogger()
                .error("Não foi possivel localizar o uniform \"$loc\" no programa GLSL!", IllegalArgumentException())
        }
        glUniform2i(locID, value1, value2)
    }

    private fun createFragmentShader(shaderCode: String) {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
    }

    fun createProgram() {
        programId = glCreateProgram()
        if (programId == 0) {
            LogManager.getLogger().error("Could not create Shader!", UnsupportedOperationException())
        }
        val fragStream = javaClass.getResourceAsStream("/$fragPath")
        val fragReader = BufferedReader(InputStreamReader(fragStream))
        val vertexStream = javaClass.getResourceAsStream("/$vertexPath")
        val vertexReader = BufferedReader(InputStreamReader(vertexStream))
        createVertexShader(vertexReader.readText())
        createFragmentShader(fragReader.readText())
        link()
    }

    private fun createShader(shaderCode: String, shaderType: Int): Int {
        val shaderId = glCreateShader(shaderType)
        if (shaderId == 0) {
            LogManager.getLogger().error("Error creating shader. Type: $shaderType")
        }
        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        LogManager.getLogger().error("compiling status of ${if(shaderType == 35632) "Fragment" else "Vertex"} Shader code: " + glGetShaderInfoLog(shaderId))

        glAttachShader(programId, shaderId)
        return shaderId
    }

    @Throws(Exception::class)
    fun link() {
        glLinkProgram(programId)
        LogManager.getLogger().error("LNK status: " + glGetProgramInfoLog(programId))

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId)
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId)
        }
        glValidateProgram(programId)
        LogManager.getLogger().error("VAL Status: " + glGetProgramInfoLog(programId))

    }

    fun bind() {
        glUseProgram(programId)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun cleanup() {
        unbind()
        if (programId != 0) {
            glDeleteProgram(programId)
        }
    }


}