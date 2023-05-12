package me.bossm0n5t3r.kolidays.batch

import Kolidays
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.bossm0n5t3r.kolidays.batch.Constants.TAB
import me.bossm0n5t3r.kolidays.batch.Utils.fromYYYYMMDDToLocalDate
import me.bossm0n5t3r.kolidays.batch.Utils.objectMapper
import me.bossm0n5t3r.kolidays.batch.Utils.toConstructorString
import me.bossm0n5t3r.kolidays.batch.dto.HolidaysResponseWrapper
import java.io.FileOutputStream
import java.nio.file.Paths
import java.time.LocalDateTime

class Job {
    private val batchHttpClient = BatchHttpClient()

    fun batch() {
        val now = LocalDateTime.now().also { println("[BATCH][MAIN][START] time: $it") }
        val thisYear = now.year

        // Get all holidays in this year
        val allHolidaysInThisYear = getAllHolidaysInYear(thisYear)
            .also {
                println("[BATCH] Get all holidays in this year - result count: ${it.size}")
            }

        val isUpdated = Kolidays.ALL_HOLIDAYS_IN_THIS_YEAR != allHolidaysInThisYear
        println("[BATCH] isUpdated: $isUpdated")

        if (isUpdated) {
            val newFileLines = listOf(
                "import java.time.LocalDate",
                "",
                "object Kolidays {",
                "${TAB}val ALL_HOLIDAYS_IN_THIS_YEAR = setOf(",
            ) + allHolidaysInThisYear.map {
                "$TAB$TAB${it.toConstructorString()},"
            } + listOf(
                "$TAB)",
                "}",
            )

            // Get Kolidays File
            val kolidaysFile = Paths.get(
                Constants.PROJECT_DIR_ABSOLUTE_PATH,
                "/core/src/main/kotlin/Kolidays.kt",
            ).toFile()
                .also {
                    println("[BATCH] Get Kolidays File")
                }

            // Clear Kolidays File
            FileOutputStream(kolidaysFile).close()
                .also {
                    println("[BATCH] Clear Kolidays File")
                }

            // Write Kolidays File
            kolidaysFile.bufferedWriter().use {
                newFileLines.forEach { line ->
                    it.appendLine(line)
                }
            }
                .also {
                    println("[BATCH] Write Kolidays File")
                }
        }

        println("[BATCH][MAIN][FINISH] time: ${LocalDateTime.now()}")
    }

    private fun getAllHolidaysInYear(year: Int) = runBlocking {
        (1..12).flatMap { month ->
            delay(100L)

            val json = batchHttpClient.getHolidaysJson(
                pageNo = 1,
                numOfRows = 10,
                year = year,
                month = month,
            )

            objectMapper.readValue<HolidaysResponseWrapper>(json)
                .response
                .body
                .items
                ?.item
                ?.map { it.locdate.toString().fromYYYYMMDDToLocalDate() }
                ?: emptyList()
        }.toSet()
    }
}
