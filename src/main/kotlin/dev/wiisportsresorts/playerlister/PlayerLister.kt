package dev.wiisportsresorts.playerlister

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class PlayerLister : JavaPlugin() {
    private val http = embeddedServer(Netty, port = 9000) {
        routing {
            get("/") { call.respond(HttpStatusCode.OK) }
            get("/players") {
                val players = mutableListOf<String>()

                for (world in server.worlds) {
                    players.addAll(*world.players.map { it.name })
                }

                val playerList =
                    if (players.size == 0) "" else "\"${players.joinToString("\",\"")}\""

                call.respondText(
                    "{\"players\":[$playerList]}", ContentType.Application.Json
                )
            }
        }
    }

    override fun onEnable() {
        http.start(false)
    }

    override fun onDisable() { // blocks execution for 100 ms, handle with care
        http.stop(50, 50)
    }
}