package org.blockg.android

import android.opengl.GLES20.*
import android.opengl.GLES30.GL_RGBA8
import org.apache.logging.log4j.LogManager
import java.awt.image.BufferedImage

import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.lang.IllegalArgumentException
import javax.imageio.ImageIO

class Texture(val img: String, val mipMap: MipMapType) {
    var image: BufferedImage = ImageIO.read(javaClass.getResourceAsStream("/$img"))
    private var buffer: ByteBuffer
    init{
        val pixels = IntArray(image.width * image.height)
        image.getRGB(0, 0, image.width, image.height, pixels, 0, image.width)

         buffer = ByteBuffer.allocateDirect(image.width * image.height * 4)
        for (h in 0 until image.height) {
            for (w in 0 until image.width) {
                val pixel = pixels[h * image.width + w]
                buffer.put((pixel shr 16 and 0xFF).toByte())
                buffer.put((pixel shr 8 and 0xFF).toByte())
                buffer.put((pixel and 0xFF).toByte())
                buffer.put((pixel shr 24 and 0xFF).toByte())
            }
        }
        buffer.flip()
    }
    var ID = 0
        private set
    @Throws(IOException::class)
    fun create(){
        if(ID != 0){
            return
        }
        val id: Int
        run {
            val buffera = intArrayOf()
            glGenTextures(1, buffera, 0)
            id = buffera[0]
        }
        glBindTexture(GL_TEXTURE_2D, id)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, if(mipMap == MipMapType.PIXELATED) GL_NEAREST else GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, if(mipMap == MipMapType.PIXELATED) GL_NEAREST else GL_LINEAR)
        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGBA8, image.width, image.height,
            0, GL_RGBA, GL_UNSIGNED_BYTE, buffer
        )
        glGenerateMipmap(GL_TEXTURE_2D)
        ID = id
    }
    var slot = 0
        set(value) {
            if(value > 32){
                LogManager.getLogger().error("Não é possivel usar slots acima de 32!", IllegalArgumentException())
                return
            }
            field = value
        }
    var binded = false
        set(value) {
            glActiveTexture(GL_TEXTURE0 + slot)
            glBindTexture(GL_TEXTURE_2D, if(value) ID else 0)
            field = value
        }
    enum class MipMapType{
        PIXELATED,
        SMOOTH;
    }
}