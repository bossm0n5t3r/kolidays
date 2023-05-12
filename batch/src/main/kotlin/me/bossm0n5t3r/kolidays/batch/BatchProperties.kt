package me.bossm0n5t3r.kolidays.batch

import me.bossm0n5t3r.kolidays.batch.Constants.PROJECT_DIR_ABSOLUTE_PATH
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.Properties

object BatchProperties {
    private val resourcesPath = Paths.get(PROJECT_DIR_ABSOLUTE_PATH, "/batch/src/main/resources/application.conf")
    private val prop = Properties().apply {
        load(FileInputStream(resourcesPath.toFile()))
    }

    private fun Properties.getPropertyWithReplaceValue(key: String, defaultValue: String): String {
        val value = this.getProperty(key, defaultValue)
        if (value == defaultValue) throw Exception("Not found key: $key")
        return value
    }

    val SERVICE_KEY = prop.getPropertyWithReplaceValue(
        "serviceKey",
        Constants.DefaultValue.SECRET_KEY,
    )
}
