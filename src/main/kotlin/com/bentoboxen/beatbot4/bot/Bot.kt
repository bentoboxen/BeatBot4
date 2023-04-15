package com.bentoboxen.beatbot4.bot

import com.bentoboxen.beatbot4.player.GuildAudioPlayerManager
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import io.github.oshai.KotlinLogging

class Bot(val kord: Kord, val config: Config) {
    private val logger = KotlinLogging.logger {}
    companion object {
        suspend fun create(config: Config) = Bot(Kord(config.token), config)
            .apply { initCommands() }
            .apply { kord.on<GuildChatInputCommandInteractionCreateEvent> { handle() } }
            .apply { login() }
    }

    private suspend fun initCommands() {
        logger.info("Initialize Bot Commands")
        Command.listCommands().forEach {
            logger.info("Init command: ${it.name}")
            it.init(kord)
        }
    }

    private suspend fun GuildChatInputCommandInteractionCreateEvent.handle() {
        logger.info("Determine chat command event")
        with(Command.getCommand(interaction.command.rootName)) {
            logger.info("Handle chat command event: $name")

            interaction.deferPublicResponse().respond {
                content = deferResponse(interaction, this@Bot)
            }
        }
    }

    private suspend fun login() {
        logger.info("Login bot to discord")
        kord.login()
    }

}