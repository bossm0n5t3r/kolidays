package me.bossm0n5t3r.kolidays.batch

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utils {
    val objectMapper: ObjectMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)

    fun String.fromYYYYMMDDToLocalDate(): LocalDate {
        return LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    fun LocalDate.toConstructorString() = "LocalDate.of(${this.year}, ${this.monthValue}, ${this.dayOfMonth})"
}
