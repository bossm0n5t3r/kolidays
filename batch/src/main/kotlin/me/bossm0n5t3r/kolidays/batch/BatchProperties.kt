package me.bossm0n5t3r.kolidays.batch

import java.io.FileInputStream
import java.nio.file.Paths
import java.util.Properties

object BatchProperties {
    private val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
    private val resourcesPath = Paths.get(projectDirAbsolutePath, "/batch/src/main/resources/application.conf")
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
