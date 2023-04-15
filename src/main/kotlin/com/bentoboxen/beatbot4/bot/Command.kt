package com.bentoboxen.beatbot4.bot

import com.bentoboxen.beatbot4.player.GuildAudioPlayerManager
import dev.kord.common.annotation.KordVoice
import dev.kord.common.entity.ChannelType
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.connect
import dev.kord.core.entity.channel.VoiceChannel
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.interaction.string
import dev.kord.voice.AudioFrame
import io.github.oshai.KotlinLogging
import kotlinx.coroutines.flow.firstOrNull

sealed interface Command {
    val name: String
    suspend fun init(kord: Kord)
    suspend fun deferResponse(interaction: GuildChatInputCommandInteraction, bot: Bot): String
    suspend fun action() {}
    companion object {
        fun listCommands() = listOf(PingCommand, PlayCommand)
        fun getCommand(name: String) = listCommands().first { it.name == name }
    }
}

object PingCommand : Command {
    override val name = "ping"
    private const val description = "Check if service is running."

    override suspend fun init(kord: Kord) {
        kord.createGlobalChatInputCommand(
            name = name,
            description = description
        ) {
            string("input", "Command input")
        }
    }

    override suspend fun deferResponse(interaction: GuildChatInputCommandInteraction, bot: Bot): String {
        return interaction.command.strings["input"]?.blankToNull() ?: "Pong"
    }
}

object PlayCommand : Command {
    private val logger = KotlinLogging.logger {}
    override val name = "play"
    private const val description = "Play a background track."

    override suspend fun init(kord: Kord) {
        kord.createGlobalChatInputCommand(
            name = name,
            description = description
        ) {
            string("title", "Song to play")
            string("channel", "channel to play in (default: General)")
        }
    }

    @OptIn(KordVoice::class)
    override suspend fun deferResponse(interaction: GuildChatInputCommandInteraction, bot: Bot): String {
        logger.info("Handle action: $name")
        logger.info("find channel")
        interaction.guild.channels.firstOrNull {
            (it.type == ChannelType.GuildVoice) &&
                    (it.name == (interaction.command.strings["channel"]?.blankToNull() ?: "General"))
        }?.let {channel ->
            logger.info("found channel ${channel.name}")
            GuildAudioPlayerManager.createPlayer(channel.id) { player ->
                interaction.kord.getChannelOf<VoiceChannel>(channel.id)?.apply {
                    this.connect {
                        audioProvider {
                            AudioFrame.fromData(player.data())
                        }
                    }
                }
            }

            (interaction.command.strings["title"]
                ?.let { bot.config.directory.findFileNameByNameParts(it)}
                ?: interaction.command.strings["title"])?.let {
                    logger.info("load track $it")
                    GuildAudioPlayerManager.loadTrack(channel.id, it)
                    return "Playing $it"
                }
        }
        return "I tried"
    }

}

internal fun String.blankToNull() = this.ifBlank { null }