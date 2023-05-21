package me.bossm0n5t3r.kolidays

import java.time.LocalDate

fun LocalDate.isHolidayInThisYear(): Boolean = Kolidays.ALL_HOLIDAYS_IN_THIS_YEAR.contains(this)
