package com.example.buildProduct.remoteUtils

import android.os.Build
import okhttp3.Headers

object Headers {
    fun headers(prevHeaders: Headers, endPoint: String): Headers {
        return Headers.Builder()
            .addAll(prevHeaders)
            .set("content-type", "application/json")
            .set("cache-control", "no-cache")
            .set("User-Agent", System.getProperty("http.agent") ?: "")

            .build()
    }
}