package com.misenpai.arcana.utils.extensions

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

fun LazyListScope.items(
    items: List<Any>,
    itemContent: @Composable (Any) -> Unit
) {
    items(items.size) { index ->
        itemContent(items[index])
    }
}

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.then(modifier(this))
    } else {
        this
    }
}