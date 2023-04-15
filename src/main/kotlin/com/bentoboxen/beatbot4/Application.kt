package com.bentoboxen.beatbot4

import com.bentoboxen.beatbot4.bot.Bot
import com.bentoboxen.beatbot4.bot.Config
import io.github.oshai.KotlinLogging

suspend fun main(vararg params: String) {
    val logger = KotlinLogging.logger {  }
    logger.info("startup")
    Bot.create(Config.config(*params))
}