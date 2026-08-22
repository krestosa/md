@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SplitButton
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun newLabsSections(): List<CatalogSection> = listOf(
    CatalogSection("Split button", "Two-part primary action with an independent trailing action.") { SplitButtonDemo() },
    CatalogSection("Floating toolbars", "Horizontal and vertical floating action toolbars from the expressive prerelease surface.") { FloatingToolbarsDemo() },
    CatalogSection("FAB menu", "Expandable floating action button menu with staggered menu items.") { FabMenuDemo() },
    CatalogSection("Wide navigation rail", "Wide-screen navigation rail using the new navigation item layout.") { WideNavigationRailDemo() },
) + finalLabsSections() + latestLabsExtras()

@Composable
private fun SplitButtonDemo() {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SplitButton(
            leadingButton = {
                SplitButtonDefaults.LeadingButton(onClick = {}) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Text("Edit")
                }
            },
            trailingButton = {
                SplitButtonDefaults.TrailingButton(
                    checked = expanded,
                    onCheckedChange = { expanded = it },
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = if (expanded) "Collapse" else "Expand")
                }
            },
        )
        if (expanded) {
            Text("Secondary actions can be attached to the trailing segment.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun FloatingToolbarsDemo() {
    var expanded by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(onClick = { expanded = !expanded }) { Text(if (expanded) "Collapse toolbars" else "Expand toolbars") }
        HorizontalFloatingToolbar(expanded = expanded) {
            IconButton(onClick = {}) { Icon(Icons.Default.Home, contentDescription = "Home") }
            IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = "Search") }
            IconButton(onClick = {}) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            VerticalFloatingToolbar(expanded = expanded) {
                IconButton(onClick = {}) { Icon(Icons.Default.Home, contentDescription = "Home") }
                IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = "Search") }
                IconButton(onClick = {}) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
            }
        }
    }
}

@Composable
private fun FabMenuDemo() {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth().height(250.dp), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButtonMenu(
            expanded = expanded,
            button = {
                FloatingActionButton(onClick = { expanded = !expanded }) {
                    Icon(if (expanded) Icons.Default.MoreVert else Icons.Default.Add, contentDescription = "Toggle FAB menu")
                }
            },
        ) {
            FloatingActionButtonMenuItem(
                onClick = { expanded = false },
                text = { Text("Create") },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
            )
            FloatingActionButtonMenuItem(
                onClick = { expanded = false },
                text = { Text("Edit") },
                icon = { Icon(Icons.Default.Edit, contentDescription = null) },
            )
            FloatingActionButtonMenuItem(
                onClick = { expanded = false },
                text = { Text("Settings") },
                icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            )
        }
    }
}

@Composable
private fun WideNavigationRailDemo() {
    var selected by remember { mutableIntStateOf(0) }
    val items = listOf(
        "Home" to Icons.Default.Home,
        "Search" to Icons.Default.Search,
        "Settings" to Icons.Default.Settings,
    )
    Row(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        WideNavigationRail(modifier = Modifier.height(300.dp)) {
            items.forEachIndexed { index, item ->
                WideNavigationRailItem(
                    selected = selected == index,
                    onClick = { selected = index },
                    icon = { Icon(item.second, contentDescription = null) },
                    label = { Text(item.first) },
                    railExpanded = false,
                )
            }
        }
        Box(modifier = Modifier.weight(1f).padding(20.dp), contentAlignment = Alignment.Center) {
            Text("${items[selected].first} content", style = MaterialTheme.typography.titleMedium)
        }
    }
}
