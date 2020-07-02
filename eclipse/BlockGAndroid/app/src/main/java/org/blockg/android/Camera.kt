package org.blockg.android

import org.joml.Math
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin


object Camera {
    private var mouseSensitivity: Float = 0.5f
    val transform = Transform(Vector3f(), Vector3f(), Vector3f())
    val matrix: Matrix4f
        get() {
            var matrixRot: Matrix4f
            var matrixRotX = Matrix4f().rotateX(Math.toRadians(-transform.rot.x))
            var matrixRotY = Matrix4f().rotateY(Math.toRadians(-transform.rot.y))
            var matrixRotZ = Matrix4f().rotateZ(Math.toRadians(-transform.rot.z))
            matrixRot = matrixRotX.mul(matrixRotY).mul(matrixRotZ)
            return matrixRot.mul(Matrix4f().translate(Vector3f(-transform.pos.x,-transform.pos.y,-transform.pos.z)))
        }
    private var newMouse = Pair(0.0,0.0)
    private var oldMouse = Pair(0.0,0.0)
    fun onTick(){

    }
}