package com.example.buildProduct.extensions

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment

fun tryCatch(work: () -> Unit) {
    try {
        work.invoke()
    } catch (e: Exception) {
            e.printStackTrace()
    } catch (e: Error) {
            e.printStackTrace()
    }
}

fun throwException(message: String): Nothing = throw Exception(message)

fun <T> tryOrNull(work: () -> T): T? {
    return try {
        work.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } catch (e: Error) {
        e.printStackTrace()
        null
    }
}

fun Fragment.emptyViewWithError(): View {
    return View(requireContext()).also {
        tryCatch { activity?.finish() }
    }
}