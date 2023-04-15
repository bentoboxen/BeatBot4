package com.bentoboxen.beatbot4.bot

import mu.KLogging
import java.io.File

data class Config(
    val token: String,// = "MTA5MjIzODg2NTQzMzEwMDMxOA.GW7zv8.F3lor6lfqaDaorx6TX1e0NXkPoHcCEPIyPrvqc",
    val directory: File// = File("E:\\BackgroundMusic")
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