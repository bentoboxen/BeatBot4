package com.bentoboxen.beatbot4.player

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import dev.kord.common.entity.Snowflake
import io.github.oshai.KotlinLogging

object GuildAudioPlayerManager {
    private val manager: AudioPlayerManager = DefaultAudioPlayerManager()
    private val guildPlayers: MutableMap<Snowflake, GuildAudioPlayer> = HashMap()
    private val logger = KotlinLogging.logger {}

    init {
        AudioSourceManagers.registerRemoteSources(manager)
        AudioSourceManagers.registerLocalSource(manager)
    }

    fun loadTrack(channelId: Snowflake, track: String) {
        logger.info("load track $track for channel $channelId")
        manager.loadItem(track, GuildAudioLoadResultHandler(channelId))
    }

    suspend fun createPlayer(guildId: Snowflake, init: suspend (player: GuildAudioPlayer) -> Unit): GuildAudioPlayer {
        return guildPlayers[guildId] ?: initPlayer(guildId, init)
    }

    private suspend fun initPlayer(guildId: Snowflake, init: suspend (player: GuildAudioPlayer) -> Unit): GuildAudioPlayer {
        return GuildAudioPlayer.create(this, GuildTrackScheduler()).also {
            guildPlayers[guildId] = it
            init(it)
        }
    }

    fun createAudioPlayer(): AudioPlayer = manager.createPlayer()

    fun getPlayer(guildId: Snowflake): GuildAudioPlayer? = guildPlayers[guildId]

}