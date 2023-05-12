package me.bossm0n5t3r.kolidays.batch.dto

data class HolidaysResponseWrapper(
    val response: HolidaysResponse,
)

data class HolidaysResponse(
    val header: HolidaysHeader,
    val body: HolidaysBody,
)

data class HolidaysHeader(
    val resultCode: String,
    val resultMsg: String,
)

data class HolidaysBody(
    val items: HolidaysItemWrapper? = null,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
)

data class HolidaysItemWrapper(
    val item: List<HolidaysItem>,
)

data class HolidaysItem(
    val dateKind: String,
    val dateName: String,
    val isHoliday: String,
    val locdate: Long,
    val seq: Int,
)
