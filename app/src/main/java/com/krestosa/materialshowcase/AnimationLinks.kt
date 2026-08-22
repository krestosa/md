package com.krestosa.materialshowcase

internal fun animationUrlFor(title: String): String {
    val key = title.lowercase()
    return when {
        "motion" in key || "spring" in key || "tween" in key || "keyframe" in key || "crossfade" in key || "animated" in key || "shared axis" in key || "fade" in key || "transition" in key || "animatable" in key || "repeat" in key || "snap" in key || "pressed" in key || "reorder" in key ->
            "https://m3.material.io/styles/motion/overview/how-it-works"
        "button" in key || "fab" in key || "floating action" in key ->
            "https://github.com/androidx/androidx/tree/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3"
        "navigation" in key || "drawer" in key || "tab" in key || "app bar" in key || "toolbar" in key ->
            "https://github.com/androidx/androidx/tree/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3"
        "search" in key ->
            "https://github.com/androidx/androidx/blob/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3/SearchBar.kt"
        "sheet" in key || "bottom sheet" in key ->
            "https://github.com/androidx/androidx/blob/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3/SheetDefaults.kt"
        "carousel" in key ->
            "https://github.com/androidx/androidx/tree/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3/carousel"
        "progress" in key || "loading" in key ->
            "https://github.com/androidx/androidx/tree/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3"
        "tooltip" in key || "menu" in key || "dropdown" in key || "dialog" in key || "snackbar" in key ->
            "https://github.com/androidx/androidx/tree/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3"
        "chip" in key || "selection" in key || "checkbox" in key || "radio" in key || "switch" in key || "slider" in key ->
            "https://github.com/androidx/androidx/tree/androidx-main/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3"
        "card" in key || "surface" in key || "elevation" in key || "state" in key ->
            "https://m3.material.io/styles/motion/overview/how-it-works"
        "adaptive" in key || "responsive" in key || "pane" in key || "window" in key ->
            "https://developer.android.com/develop/ui/compose/layouts/adaptive/animated-pane"
        "color" in key || "typography" in key || "shape" in key || "icon" in key || "accessibility" in key || "rtl" in key ->
            "https://m3.material.io/styles/motion/overview/how-it-works"
        else -> "https://m3.material.io/styles/motion/overview/how-it-works"
    }
}
