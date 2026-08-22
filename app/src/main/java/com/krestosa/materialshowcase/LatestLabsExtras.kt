@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class,
)

package com.krestosa.materialshowcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheet
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scrim
import androidx.compose.material3.ScrollField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimeScroll
import androidx.compose.material3.TonalToggleButton
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.material3.rememberScrollFieldState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun latestLabsExtras(): List<CatalogSection> = listOf(
    CatalogSection("Standalone bottom sheet", "Static BottomSheet rendered directly in the composition hierarchy.") { StandaloneBottomSheetDemo() },
    CatalogSection("Scrim", "Standalone modal scrim with dismiss interaction and explicit alpha.") { ScrimDemo() },
    CatalogSection("Time scroll", "Expressive scroll-wheel time picker variant.") { TimeScrollDemo() },
    CatalogSection("Scroll field", "Generic scroll-wheel numeric selector used by rich pickers.") { ScrollFieldDemo() },
    CatalogSection("Tonal toggle button", "Current tonal toggle button variant alongside the other toggle families.") { TonalToggleDemo() },
    CatalogSection("Centered hero carousel", "Hero carousel with a centered focal item and masked side items.") { CenteredHeroCarouselDemo() },
    CatalogSection("Uncontained carousel", "Carousel with fixed-size items and a clipped trailing item.") { UncontainedCarouselDemo() },
)

@Composable
private fun StandaloneBottomSheetDemo() {
    var show by remember { mutableStateOf(false) }
    val state = rememberBottomSheetState(initialValue = SheetValue.Hidden)
    Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
        Button(onClick = { show = true }) { Text("Show standalone sheet") }
        if (show) {
            Scrim(contentDescription = "Dismiss bottom sheet", onClick = { show = false }, modifier = Modifier.fillMaxSize(), alpha = { 0.45f })
            BottomSheet(state = state, onDismissRequest = { show = false }) {
                Column(modifier = Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Standalone BottomSheet", style = MaterialTheme.typography.titleLarge)
                    Text("Lives in the same composition hierarchy instead of a separate dialog window.")
                    Button(onClick = { show = false }) { Text("Close") }
                }
            }
        }
    }
    LaunchedEffect(show) {
        if (show) state.show() else if (state.currentValue != SheetValue.Hidden) state.hide()
    }
}

@Composable
private fun ScrimDemo() {
    var visible by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { visible = true }) { Text("Show scrim") }
        Box(
            modifier = Modifier.fillMaxWidth().height(160.dp).background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text("Content behind scrim")
            if (visible) Scrim(contentDescription = "Dismiss scrim", modifier = Modifier.fillMaxSize(), onClick = { visible = false }, alpha = { 0.45f })
        }
    }
}

@Composable
private fun TimeScrollDemo() {
    val state = rememberTimePickerState(initialHour = 12, initialMinute = 30)
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TimeScroll(state = state, shapes = TimePickerDefaults.shapes(), modifier = Modifier.fillMaxWidth())
        Text("${state.hour.toString().padStart(2, '0')}:${state.minute.toString().padStart(2, '0')}")
    }
}

@Composable
private fun ScrollFieldDemo() {
    val state = rememberScrollFieldState(itemCount = 20, index = 5)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ScrollField(
            state = state,
            contentDescription = "Select number",
            modifier = Modifier.size(width = 180.dp, height = 160.dp),
            fieldAccessibilityDescription = { "Value ${it + 1}" },
            field = { index, selected ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "${index + 1}",
                        style = if (selected) MaterialTheme.typography.displayMedium else MaterialTheme.typography.headlineMedium,
                        color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline,
                    )
                }
            },
        )
    }
}

@Composable
private fun TonalToggleDemo() {
    var checked by remember { mutableStateOf(false) }
    TonalToggleButton(checked = checked, onCheckedChange = { checked = it }) { Text(if (checked) "Tonal on" else "Tonal off") }
}

@Composable
private fun CenteredHeroCarouselDemo() {
    val state = rememberCarouselState { 6 }
    HorizontalCenteredHeroCarousel(
        state = state,
        modifier = Modifier.fillMaxWidth().height(180.dp),
        maxItemWidth = 220.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) { index -> CarouselItemSurface(index) }
}

@Composable
private fun UncontainedCarouselDemo() {
    val state = rememberCarouselState { 6 }
    HorizontalUncontainedCarousel(
        state = state,
        itemWidth = 150.dp,
        modifier = Modifier.fillMaxWidth().height(170.dp),
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) { index -> CarouselItemSurface(index) }
}

@Composable
private fun CarouselItemSurface(index: Int) {
    androidx.compose.material3.ElevatedCard(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Item ${index + 1}", style = MaterialTheme.typography.titleLarge) }
    }
}
