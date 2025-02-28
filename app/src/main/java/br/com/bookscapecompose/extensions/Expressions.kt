package br.com.bookscapecompose.extensions

import android.content.Context
import android.content.Intent

fun goToActivity(context: Context, clazz: Class<*>, intent : Intent.() -> Unit = {}) {
    Intent(context, clazz).apply {
        intent()
        context.startActivity(this)
    }
}