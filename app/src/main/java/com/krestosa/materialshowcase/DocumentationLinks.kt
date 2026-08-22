package com.krestosa.materialshowcase

internal fun documentationUrlFor(title: String): String {
    val key = title.lowercase()
    return when {
        "motion" in key || "spring" in key || "tween" in key || "keyframe" in key || "crossfade" in key || "animated" in key || "shared axis" in key || "fade" in key || "transition" in key || "animatable" in key || "repeat" in key || "snap" in key || "pressed" in key || "reorder" in key ->
            "https://m3.material.io/styles/motion/overview/how-it-works"
        "adaptive" in key || "navigation suite" in key || "pane" in key || "responsive" in key || "window size" in key ->
            "https://developer.android.com/develop/ui/compose/layouts/adaptive"
        "button group" in key -> "https://developer.android.com/reference/kotlin/androidx/compose/material3/ButtonGroup"
        "split button" in key -> "https://m3.material.io/components/split-buttons/overview"
        "button" in key -> "https://m3.material.io/components/buttons/overview"
        "fab" in key || "floating action" in key -> "https://m3.material.io/components/floating-action-button/overview"
        "chip" in key -> "https://m3.material.io/components/chips/overview"
        "card" in key -> "https://m3.material.io/components/cards/overview"
        "checkbox" in key || "radio" in key || "switch" in key || "selection" in key || "toggle" in key -> "https://m3.material.io/components/selection-controls/overview"
        "slider" in key -> "https://m3.material.io/components/sliders/overview"
        "text field" in key || "secure" in key -> "https://m3.material.io/components/text-fields/overview"
        "progress" in key || "loading" in key -> "https://m3.material.io/components/progress-indicators/overview"
        "badge" in key -> "https://m3.material.io/components/badges/overview"
        "tab" in key -> "https://m3.material.io/components/tabs/overview"
        "list" in key -> "https://m3.material.io/components/lists/overview"
        "divider" in key -> "https://m3.material.io/components/divider/overview"
        "top app bar" in key || "app bar" in key || "toolbar" in key -> "https://m3.material.io/components/top-app-bar/overview"
        "bottom app bar" in key -> "https://m3.material.io/components/bottom-app-bar/overview"
        "navigation bar" in key || "short navigation" in key -> "https://m3.material.io/components/navigation-bar/overview"
        "navigation rail" in key || "wide rail" in key -> "https://m3.material.io/components/navigation-rail/overview"
        "drawer" in key -> "https://m3.material.io/components/navigation-drawer/overview"
        "search" in key -> "https://m3.material.io/components/search/overview"
        "date picker" in key || "date range" in key -> "https://m3.material.io/components/date-pickers/overview"
        "time" in key -> "https://m3.material.io/components/time-pickers/overview"
        "menu" in key || "dropdown" in key -> "https://m3.material.io/components/menus/overview"
        "tooltip" in key -> "https://m3.material.io/components/tooltips/overview"
        "bottom sheet" in key || "sheet" in key -> "https://m3.material.io/components/bottom-sheets/overview"
        "dialog" in key -> "https://m3.material.io/components/dialogs/overview"
        "snackbar" in key -> "https://m3.material.io/components/snackbar/overview"
        "carousel" in key -> "https://m3.material.io/components/carousel/overview"
        "pull to refresh" in key -> "https://developer.android.com/reference/kotlin/androidx/compose/material3/pulltorefresh/package-summary"
        "color" in key || "theme" in key || "surface" in key || "elevation" in key -> "https://m3.material.io/styles/color/overview"
        "typography" in key || "type scale" in key -> "https://m3.material.io/styles/typography/overview"
        "shape" in key -> "https://m3.material.io/styles/shape/overview"
        "icon" in key -> "https://fonts.google.com/icons"
        "accessibility" in key || "contrast" in key || "rtl" in key || "font scaling" in key || "touch target" in key -> "https://m3.material.io/foundations/accessible-design/overview"
        "state" in key -> "https://m3.material.io/foundations/interaction/states/overview"
        "scrim" in key || "scroll field" in key || "drag handle" in key || "segmented" in key ->
            "https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary"
        else -> "https://m3.material.io/components"
    }
}
