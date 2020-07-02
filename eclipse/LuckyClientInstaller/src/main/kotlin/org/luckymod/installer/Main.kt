package org.luckymod.installer

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sun.java.swing.plaf.gtk.GTKLookAndFeel
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel
import java.io.*
import java.net.URL
import java.nio.charset.Charset
import javax.swing.UIManager
import kotlin.system.exitProcess


class Main {


    companion object{


        @Throws(IOException::class)
        fun readJsonFromUrl(url: String?): JsonObject? {
            val `is`: InputStream = URL(url).openStream()
            return `is`.use { `is` ->
                val rd = BufferedReader(InputStreamReader(`is`, Charset.forName("UTF-8")))

                JsonParser.parseReader(rd).asJsonObject
            }
        }
        @JvmStatic
        fun main(args: Array<String>) {



            val versions: JsonObject = readJsonFromUrl("http://luckymod.pt/versions/versions.json")!!
            val versionsFolder: File? = if(System.getProperty("os.name").contains("Mac OS", true) || System.getProperty("os.name").contains("Linux", true)){
                UIManager.setLookAndFeel(GTKLookAndFeel())
                File(System.getProperty("user.home")+"/.minecraft/versions")
            }else if(System.getProperty("os.name").contains("Windows", true)){
                UIManager.setLookAndFeel(WindowsLookAndFeel())

                File(System.getenv("APPDATA")+"\\.minecraft\\versions")

            }else{
                null
            }
            if(versionsFolder == null){
                println("Esse sistema não é suportado pelo instalador! por favor instale manualmente!")
                exitProcess(1)
            }

            Window(versionsFolder, versions)
        }
    }
}
