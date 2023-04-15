package com.bentoboxen.beatbot4.player

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import dev.kord.common.entity.Snowflake
import dev.kord.voice.VoiceConnection
import io.github.oshai.KLogging

class GuildAudioPlayer (
    //private val voiceConnection: VoiceConnection,
    val player: AudioPlayer
) {
    companion object : KLogging() {
        fun create(
            manager: GuildAudioPlayerManager,
            scheduler: AudioEventAdapter
        ): GuildAudioPlayer {
            return manager.createAudioPlayer().let { player ->
                player.addListener(scheduler)
                GuildAudioPlayer(player)
            }
        }
    }

    fun data(): ByteArray? = player.provide()?.data
}