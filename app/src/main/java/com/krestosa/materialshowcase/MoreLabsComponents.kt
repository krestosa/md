@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun moreLabsSections(): List<CatalogSection> = listOf(
    CatalogSection("Tooltips", "Plain and rich tooltip surfaces using the current prerelease positioning API.") { TooltipsDemo() },
    CatalogSection("Pull to refresh", "Gesture-driven refresh container and Material refresh indicator.") { PullToRefreshDemo() },
    CatalogSection("Standard bottom sheet", "Persistent bottom-sheet scaffold with a visible peek state.") { StandardBottomSheetDemo() },
    CatalogSection("Carousel", "Material multi-browse carousel with adaptive item sizing and masking behavior.") { CarouselDemo() },
) + newLabsSections()

@Composable
private fun TooltipsDemo() {
    val plainState = rememberTooltipState()
    val richState = rememberTooltipState(isPersistent = true)
    val provider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above)

    Row(horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
        TooltipBox(
            positionProvider = provider,
            state = plainState,
            tooltip = { PlainTooltip(caretShape = TooltipDefaults.caretShape()) { Text("Plain tooltip") } },
        ) {
            IconButton(onClick = {}) { Icon(Icons.Default.Info, contentDescription = "Plain tooltip anchor") }
        }

        TooltipBox(
            positionProvider = provider,
            state = richState,
            hasAction = true,
            tooltip = {
                RichTooltip(
                    title = { Text("Rich tooltip") },
                    action = { Text("Action") },
                    caretShape = TooltipDefaults.caretShape(),
                    text = { Text("Supports title, descriptive text and an action area.") },
                )
            },
        ) {
            Button(onClick = {}) { Text("Rich tooltip") }
        }
    }
}

@Composable
private fun PullToRefreshDemo() {
    var refreshing by remember { mutableStateOf(false) }
    var refreshCount by remember { mutableIntStateOf(0) }
    val rows = remember(refreshCount) { List(6) { "Item ${it + 1} · refresh $refreshCount" } }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = {
                refreshing = true
                refreshCount += 1
            },
            modifier = Modifier.fillMaxWidth().height(220.dp),
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(rows) { row ->
                    Text(row, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp))
                }
            }
        }
        if (refreshing) {
            Button(onClick = { refreshing = false }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Text(" Finish refresh")
            }
        }
    }
}

@Composable
private fun StandardBottomSheetDemo() {
    val state = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = state,
        modifier = Modifier.fillMaxWidth().height(320.dp),
        sheetPeekHeight = 84.dp,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Standard bottom sheet", style = MaterialTheme.typography.titleMedium)
                Text("Drag the sheet to expand or collapse it.")
                Box(Modifier.height(120.dp))
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.surfaceContainerLow),
            contentAlignment = Alignment.Center,
        ) {
            Text("Persistent sheet scaffold content")
        }
    }
}

@Composable
private fun CarouselDemo() {
    val count = 8
    val state = rememberCarouselState { count }

    HorizontalMultiBrowseCarousel(
        state = state,
        preferredItemWidth = 150.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier.fillMaxWidth().height(170.dp),
    ) { index ->
        ElevatedCard(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.size(54.dp).background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.large),
                        contentAlignment = Alignment.Center,
                    ) { Text("${index + 1}") }
                    Text("Carousel item", modifier = Modifier.padding(top = 10.dp))
                }
            }
        }
    }
}
