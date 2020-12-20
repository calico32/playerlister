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
                val players = server.onlinePlayers.toTypedArray()
                val playerNames = players.map { it.name }

                val playerList =
                    if (players.isEmpty()) "" else "\"${playerNames.joinToString("\",\"")}\""

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