package com.bentoboxen.beatbot4.bot

import mu.KLogging
import java.io.File

data class Config(
    val token: String,
    val directory: File
) {
    companion object : KLogging() {
        fun config(vararg params: String): Config =
            params.toList().map {
                it.split("=")
            }.associate {
                it[0] to it[1]
            }.let {
                it.forEach { e ->
                    logger.debug("config ${e.key} = ${e.value}")
                }
                Config(
                    token = it["token"] ?: "",
                    directory = File(it["directory"] ?: "")
                )
            }
    }
}