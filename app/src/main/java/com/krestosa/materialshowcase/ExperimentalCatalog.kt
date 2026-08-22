@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedToggleButton
import androidx.compose.material3.FilledTonalToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedToggleButton
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun ExperimentalCatalogContent(
    modifier: Modifier = Modifier,
    onSnackbar: (String) -> Unit,
) {
    val sections = experimentalSections(onSnackbar)
    var selectedIndex by remember { mutableIntStateOf(-1) }

    BackHandler(enabled = selectedIndex >= 0) { selectedIndex = -1 }

    if (selectedIndex < 0) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text("Material Labs", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Experimental and prerelease APIs are isolated. Opening Labs itself composes no alpha demo; each component runs only when selected.",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Runtime: Material 3 1.5.0-alpha26",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
            itemsIndexed(sections, key = { _, section -> section.title }) { index, section ->
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(section.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        Text(
                            section.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Button(onClick = { selectedIndex = index }) { Text("Open demo") }
                    }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    } else {
        val section = sections[selectedIndex.coerceIn(sections.indices)]
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Button(onClick = { selectedIndex = -1 }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                    Text(" Back to Labs")
                }
            }
            item {
                Text(section.title, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(section.description, style = MaterialTheme.typography.bodyLarge)
            }
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) { section.content() }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

private fun experimentalSections(onSnackbar: (String) -> Unit): List<CatalogSection> = listOf(
    CatalogSection("Channel status", "Current library channels used to separate production and prerelease APIs.") {
        ChannelStatusDemo()
    },
    CatalogSection("Alpha toggle buttons", "New full-size toggle button family introduced in the Material 3 1.5 prerelease line.") {
        AlphaToggleButtonsDemo()
    },
    CatalogSection("Alpha button group", "Overflow-aware grouped actions from the 1.5 prerelease API surface.") {
        AlphaButtonGroupDemo(onSnackbar)
    },
    CatalogSection("Alpha short navigation bar", "Compact primary destination navigation introduced in the 1.5 prerelease line.") {
        AlphaShortNavigationBarDemo()
    },
) + moreLabsSections()

@Composable
private fun ChannelStatusDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Stable") },
                supportingContent = { Text("Material 3 1.4.0 · production reference") },
                trailingContent = { Text("STABLE", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) },
            )
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Prerelease") },
                supportingContent = { Text("Material 3 1.5.0-alpha26 · active Labs runtime") },
                trailingContent = { Text("ALPHA", color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold) },
            )
        }
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            ListItem(
                headlineContent = { Text("Beta / RC") },
                supportingContent = { Text("No current Material 3 beta or release-candidate channel") },
                trailingContent = { Text("—") },
            )
        }
    }
}

@Composable
private fun AlphaToggleButtonsDemo() {
    var filled by remember { mutableStateOf(true) }
    var elevated by remember { mutableStateOf(false) }
    var tonal by remember { mutableStateOf(false) }
    var outlined by remember { mutableStateOf(false) }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ToggleButton(checked = filled, onCheckedChange = { filled = it }) { Text("Filled") }
        ElevatedToggleButton(checked = elevated, onCheckedChange = { elevated = it }) { Text("Elevated") }
        FilledTonalToggleButton(checked = tonal, onCheckedChange = { tonal = it }) { Text("Tonal") }
        OutlinedToggleButton(checked = outlined, onCheckedChange = { outlined = it }) { Text("Outlined") }
    }
}

@Composable
private fun AlphaButtonGroupDemo(onSnackbar: (String) -> Unit) {
    ButtonGroup(
        overflowIndicator = { menuState -> ButtonGroupDefaults.OverflowIndicator(menuState = menuState) },
        modifier = Modifier.fillMaxWidth(),
    ) {
        clickableItem(onClick = { onSnackbar("Create") }, label = "Create")
        clickableItem(onClick = { onSnackbar("Edit") }, label = "Edit")
        clickableItem(onClick = { onSnackbar("Share") }, label = "Share")
        clickableItem(onClick = { onSnackbar("Archive") }, label = "Archive")
        clickableItem(onClick = { onSnackbar("Delete") }, label = "Delete")
    }
}

@Composable
private fun AlphaShortNavigationBarDemo() {
    var selected by remember { mutableIntStateOf(0) }
    val items = listOf(
        "Home" to Icons.Default.Home,
        "Search" to Icons.Default.Search,
        "Settings" to Icons.Default.Settings,
    )

    ShortNavigationBar(modifier = Modifier.fillMaxWidth()) {
        items.forEachIndexed { index, item ->
            ShortNavigationBarItem(
                selected = selected == index,
                onClick = { selected = index },
                icon = { Icon(item.second, contentDescription = item.first) },
                label = { Text(item.first) },
            )
        }
    }
}
