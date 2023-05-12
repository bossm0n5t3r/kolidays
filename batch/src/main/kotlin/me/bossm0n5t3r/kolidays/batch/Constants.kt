package me.bossm0n5t3r.kolidays.batch

import java.nio.file.Paths

object Constants {
    const val TAB = "    "
    val PROJECT_DIR_ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString()

    object DefaultValue {
        const val SECRET_KEY = "SERVICE_KEY"
    }

    object HttpMethod {
        const val GET = "GET"
    }

    object HttpHeaders {
        const val ACCEPT = "Accept"
    }

    object MediaType {
        const val APPLICATION_JSON_VALUE = "application/json"
    }
}
