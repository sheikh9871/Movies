package com.example.buildProduct.remoteUtils

import android.util.Log
import retrofit2.Response
import java.util.regex.Pattern

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(
                error.message ?: "unknown error",
                500
            )
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        code = response.code(),
                        body = body,
                        linkHeader = response.headers()["link"]
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }

                ApiErrorResponse(errorMsg ?: "unknown error", response.code())
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class DuplicateApiErrorResponse<T>(
    val code: Int,
    val errorBody: String?,
    val errorMessage: String,
) : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val code: Int,
    val body: T,
    val links: Map<String, String>,
) : ApiResponse<T>() {

    constructor(code: Int, body: T, linkHeader: String?) : this(
        code = code,
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2) ?: ""] = matcher.group(1) ?: ""
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorMessage: String, val code: Int) : ApiResponse<T>()