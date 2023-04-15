package com.bentoboxen.beatbot4.bot

import java.io.File

fun File.findFileNameByNameParts(namePart: String): String? =
    with(namePart.split("\\s")) {
        walkTopDown().map { it.name }.find { fileName ->
            all { namePart ->
                fileName.lowercase().contains(namePart.lowercase())

            }
        }
    }?.let { "$absolutePath${File.separator}$it" }