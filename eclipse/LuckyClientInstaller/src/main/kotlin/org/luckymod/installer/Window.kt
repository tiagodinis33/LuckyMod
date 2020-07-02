package org.luckymod.installer

import com.google.gson.JsonObject
import org.apache.commons.io.FileUtils
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver
import org.codehaus.plexus.logging.console.ConsoleLoggerManager
import java.awt.Font
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLConnection
import java.net.UnknownHostException
import javax.swing.*


class Window(private val versionsDir: File, private val versionsJson: JsonObject): JFrame("LuckyMod Installer") {
    private var installThread: Thread? = null
    val installingTxt = JLabel("Instalando, por favor aguarde...")
    fun consegueConectar(address: String?): Boolean {
        try {
            val url = URL(address)
            val connection: URLConnection = url.openConnection()
            connection.connect()
        } catch (e: IOException ) {
            return false
        } catch (e: UnknownHostException) {
            return false
        }
        return true

    }
    var canClose = false;
    init{
        setBounds(0,0,500,500)
        isVisible = true
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                if(!canClose){
                    val confirmed = JOptionPane.showConfirmDialog(this@Window,
                            "Tem certeza que quer cancelar a instalação?", "Cancelar instalação",
                            JOptionPane.YES_NO_OPTION)
                    if (confirmed == JOptionPane.YES_OPTION) {
                        dispose()
                        installThread?.interrupt()
                        installThread?.join()
                    } else
                        defaultCloseOperation = DO_NOTHING_ON_CLOSE

                }
            }

            override fun windowOpened(e: WindowEvent?) {
                if(!consegueConectar("http://www.luckymod.pt")){
                    JOptionPane.showMessageDialog(this@Window, "Nao foi possivel conectar ao servidor, por favor, verifique sua conexão!")
                    e?.window?.dispose()

                }
            }
        })

        installingTxt.isVisible = false;
        installingTxt.font = Font("Arial",Font.PLAIN,20)
        isResizable = false
        layout = null
        val options = arrayListOf<String>()

        for(version in versionsJson["versions"].asJsonArray){
            val obj = version.asJsonObject
            options.add(obj["name"].asString)
        }
        val optionsArray = arrayOfNulls<String>(options.size)
        for(option in options){
            optionsArray[options.indexOf(option)] = option
        }
        val verList = JComboBox<String?>(optionsArray)
        verList.selectedIndex = options.size - 1
        verList.setBounds((500/2)-(311/2), (500/2)-(24/2), 311, 24)
        verList.isEditable = true
        update(graphics)
        add(verList)
        val installButton = JButton("Instalar")
        installButton.isVisible = true
        verList.isVisible = true

        var selectedVersion: Int
        installButton.addActionListener {
            update(graphics)
            installingTxt.isVisible = true;
            installButton.isEnabled = false
                installThread = Thread{
                    selectedVersion = verList.selectedIndex;
                    val downloadURL = URL("http://luckymod.pt" + versionsJson["versions"].asJsonArray[selectedVersion].asJsonObject["download"].asString);
                    val tarFile = File(versionsDir.path + "/luckymod.tar.gz")
                    FileUtils.copyURLToFile(downloadURL, tarFile)
                    val ua = TarGZipUnArchiver()
    // Logging - as @Akom noted, logging is mandatory in newer versions, so you can use a code like this to configure it:
                    val manager = ConsoleLoggerManager()
                    manager.initialize()
                    ua.enableLogging(manager.getLoggerForComponent("main"))
    // -- end of logging part
                    ua.sourceFile = tarFile
                    ua.destDirectory = versionsDir
                    ua.extract()
                    tarFile.delete()
                    canClose = true;
                    installButton.isEnabled = true
                    installingTxt.text = versionsJson["versions"].asJsonArray[selectedVersion].asJsonObject["name"].asString + " instalado com sucesso!"
                    installingTxt.setBounds((500/2)-(311/2), ((500/4)*3)-(100/2)-27,((versionsJson["versions"].asJsonArray[selectedVersion].asJsonObject["name"].asString + " instalado com sucesso!").length * 14)+5, 25)
                    update(graphics)

                }
            installThread!!.start()
        }
        installButton.setBounds((500/2)-(311/2), ((500/4)*3)-(100/2),311, 100)
        add(installButton)
        add(installingTxt)
        installingTxt.setBounds((500/2)-(311/2), ((500/4)*3)-(100/2)-27,"Instalando, por favor aguarde...".length * 14+5, 25)
        update(graphics)

    }
}
