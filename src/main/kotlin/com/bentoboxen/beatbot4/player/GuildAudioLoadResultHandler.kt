package com.bentoboxen.beatbot4.player

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.kord.common.entity.Snowflake
import io.github.oshai.KLogging

class GuildAudioLoadResultHandler(
    private val channelId: Snowflake
) : AudioLoadResultHandler {
    companion object : KLogging()

    override fun trackLoaded(track: AudioTrack?) {
        track?.apply {
            logger.info("Track loaded: play track ${track.identifier}")
            GuildAudioPlayerManager.getPlayer(channelId)?.player?.also {
                logger.info("Got player for guild $channelId")
            }?.startTrack(track, false).also {
                logger.info("track ${track.identifier} on Guild $channelId was started")
            }
        }
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        logger.info("Play list loaded")
    }

    override fun noMatches() {
        logger.info("No match!")
    }

    override fun loadFailed(exception: FriendlyException?) {
        logger.error("Load failed!", exception)
    }


}