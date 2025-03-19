package com.example.arcana.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object CoroutineProvider {
    val IO: CoroutineDispatcher = Dispatchers.IO
}

fun CoroutineScope.launchWithContext(
    context: CoroutineContext = CoroutineProvider.IO,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context) {
        block()
    }
}