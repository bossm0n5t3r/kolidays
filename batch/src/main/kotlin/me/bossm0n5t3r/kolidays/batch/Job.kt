package me.bossm0n5t3r.kolidays.batch

import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.bossm0n5t3r.kolidays.batch.Utils.objectMapper
import me.bossm0n5t3r.kolidays.batch.dto.HolidaysResponseWrapper

class Job {
    private val batchHttpClient = BatchHttpClient()

    fun getAllHolidaysInYear(year: Int) = runBlocking {
        (1..12).flatMap { month ->
            delay(1000L)

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
                ?: emptyList()
        }.toSet()
    }
}
