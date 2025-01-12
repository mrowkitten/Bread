package net.dasdarklord.bread.dfk.template

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class CodeClient {

    companion object {
        fun buildTemplate(templates: List<DFTemplate>) {
            val client = CCBuildClient(URI("ws://localhost:31375"), templates.map { it.compressed() })
            client.connectionLostTimeout = 5000
            client.connect()
            Thread {
                Thread.sleep(10000)
                if (!client.isClosed && !client.isClosing) client.close()
            }.start()
        }

        fun giveTemplate(templates: List<DFTemplate>) {
            val client = CCSetInvClient(URI("ws://localhost:31375"), templates.map { it.compressed() })
            client.connectionLostTimeout = 5000
            client.connect()
            Thread {
                Thread.sleep(10000)
                if (!client.isClosed && !client.isClosing) client.close()
            }.start()
        }
    }

    class CCSetInvClient(uri: URI, private val templates: List<String>) : WebSocketClient(uri) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            send("scopes inventory")
        }

        override fun onMessage(message: String?) {
            println(message)
            if (message == "auth") {
                for (template in templates) {
                    send("give {Count:1b,id:\"minecraft:ender_chest\",components:{\"minecraft:custom_data\":{PublicBukkitValues:{\"hypercube:codetemplatedata\":'{\"code\":\"$template\",\"version\":\"1\",\"name\":\"template\",\"author\":\"Bread Template Generator\"}}}}\"}'}}}}")
                }
                close()
            }
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) { }

        override fun onError(ex: java.lang.Exception?) { }

    }

    class CCBuildClient(uri: URI, private val templates: List<String>) : WebSocketClient(uri) {

        override fun onMessage(p0: String?) {
            if (p0 == "auth") {
                send("clear")
                send("place")
                for (template in templates) send("place $template")
                send("place go")
            }
            if (p0 == "place done") close()
        }

        override fun onOpen(p0: ServerHandshake?) {
            send("scopes write_code clear_plot")
        }

        override fun onClose(p0: Int, p1: String?, p2: Boolean) { }

        override fun onError(p0: Exception?) { }
    }

}