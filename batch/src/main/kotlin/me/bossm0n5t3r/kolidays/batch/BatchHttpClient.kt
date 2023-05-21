package me.bossm0n5t3r.kolidays.batch

import fuel.httpMethod
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class BatchHttpClient {
    private val baseUrl = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"

    private fun String.toUrlEncode(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.displayName())

    private fun generateUrl(
        serviceKey: String,
        pageNo: Int,
        numOfRows: Int,
        year: Int,
        month: Int? = null,
    ): String {
        return baseUrl +
            "?${"serviceKey".toUrlEncode()}=$serviceKey" +
            "&${"pageNo".toUrlEncode()}=${pageNo.toString().toUrlEncode()}" +
            "&${"numOfRows".toUrlEncode()}=${numOfRows.toString().toUrlEncode()}" +
            "&${"solYear".toUrlEncode()}=${year.toString().toUrlEncode()}" +
            if (month != null) {
                "&${"solMonth".toUrlEncode()}=${month.toString().padStart(2, '0').toUrlEncode()}"
            } else {
                ""
            }
    }

    fun getHolidaysJson(pageNo: Int, numOfRows: Int, year: Int, month: Int? = null): String {
        val url = generateUrl(
            BatchProperties.SERVICE_KEY,
            pageNo,
            numOfRows,
            year,
            month,
        )

        return runBlocking {
            url
                .httpMethod(
                    method = Constants.HttpMethod.GET,
                    headers = mapOf(
                        Constants.HttpHeaders.ACCEPT to Constants.MediaType.APPLICATION_JSON_VALUE,
                    ),
                )
                .body
        }
    }
}
