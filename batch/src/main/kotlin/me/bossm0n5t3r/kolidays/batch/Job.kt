package me.bossm0n5t3r.kolidays.batch

import Kolidays
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.KotlinLogging
import me.bossm0n5t3r.kolidays.batch.Constants.TAB
import me.bossm0n5t3r.kolidays.batch.Utils.fromYYYYMMDDToLocalDate
import me.bossm0n5t3r.kolidays.batch.Utils.objectMapper
import me.bossm0n5t3r.kolidays.batch.Utils.toConstructorString
import me.bossm0n5t3r.kolidays.batch.dto.HolidaysResponseWrapper
import java.io.FileOutputStream
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime

class Job {
    private val logger = KotlinLogging.logger {}
    private val batchHttpClient = BatchHttpClient()

    fun batch() {
        val now = LocalDateTime.now().also { logger.info("[BATCH][MAIN][START] time: $it") }
        val thisYear = now.year

        // Get all holidays in this year
        val allHolidaysInThisYear = getAllHolidaysInYear(thisYear)
            .also {
                logger.info("[BATCH] Get all holidays in this year - result count: ${it.size}")
            }

        val isUpdated = Kolidays.ALL_HOLIDAYS_IN_THIS_YEAR != allHolidaysInThisYear
        logger.info { "[BATCH] isUpdated: $isUpdated" }

        if (isUpdated) {
            updateKolidaysFile(allHolidaysInThisYear)
        }

        logger.info { "[BATCH][MAIN][FINISH] time: ${LocalDateTime.now()}" }
    }

    private fun getAllHolidaysInYear(year: Int): Set<LocalDate> {
        logger.info { "[BATCH][START] getAllHolidaysInYear | year: $year" }
        val json = batchHttpClient.getHolidaysJson(
            pageNo = 1,
            numOfRows = 100,
            year = year,
        )

        val result = objectMapper.readValue<HolidaysResponseWrapper>(json)
            .response
            .body
            .items
            ?.item
            ?.map { it.locdate.toString().fromYYYYMMDDToLocalDate() }
            ?.toSet()
            ?: emptySet()

        logger.info { "[BATCH][DONE] getAllHolidaysInYear | year: $year" }
        return result
    }

    private fun updateKolidaysFile(allHolidaysInThisYear: Set<LocalDate>) {
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
                logger.info("[BATCH] Get Kolidays File")
            }

        // Clear Kolidays File
        FileOutputStream(kolidaysFile).close()
            .also {
                logger.info("[BATCH] Clear Kolidays File")
            }

        // Write Kolidays File
        kolidaysFile.bufferedWriter().use {
            newFileLines.forEach { line ->
                it.appendLine(line)
            }
        }
            .also {
                logger.info("[BATCH] Write Kolidays File")
            }
    }
}
