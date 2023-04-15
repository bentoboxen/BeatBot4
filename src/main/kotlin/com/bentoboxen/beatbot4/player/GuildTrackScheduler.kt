package com.bentoboxen.beatbot4.player

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import io.github.oshai.KLogging

class GuildTrackScheduler: AudioEventAdapter() {
    companion object : KLogging()

    override fun onPlayerPause(player: AudioPlayer?) {
        logger.info("pause")
        super.onPlayerPause(player)
    }

    override fun onPlayerResume(player: AudioPlayer?) {
        logger.info("resume")
        super.onPlayerResume(player)
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        logger.info("start ${track?.identifier ?: "unknown"}")
        super.onTrackStart(player, track)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        logger.info("end ${track?.identifier ?: "unknown"}")
        if(endReason == AudioTrackEndReason.FINISHED) {
            player?.playTrack(track?.makeClone())
        } else super.onTrackEnd(player, track, endReason)
    }

    override fun onTrackException(player: AudioPlayer?, track: AudioTrack?, exception: FriendlyException?) {
        logger.error("error", exception)
        super.onTrackException(player, track, exception)
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        logger.info("stuck $thresholdMs ms")
        super.onTrackStuck(player, track, thresholdMs)
    }

    override fun onTrackStuck(
        player: AudioPlayer?,
        track: AudioTrack?,
        thresholdMs: Long,
        stackTrace: Array<out StackTraceElement>?
    ) {
        logger.info("stuck $thresholdMs ms")
        super.onTrackStuck(player, track, thresholdMs, stackTrace)
    }
}