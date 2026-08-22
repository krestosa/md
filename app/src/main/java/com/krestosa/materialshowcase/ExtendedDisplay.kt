@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun extendedDisplaySections(): List<CatalogSection> = listOf(
    CatalogSection("Exposed dropdown menu", "Anchored menu paired with a read-only text field.") { ExposedDropdownDemo() },
    CatalogSection("Medium and large app bars", "Additional top app bar hierarchy variants.") { LargerAppBarsDemo() },
    CatalogSection("Secondary tabs", "Secondary fixed tabs for hierarchy within content.") { SecondaryTabsDemo() },
    CatalogSection("Indeterminate progress", "Circular and linear indicators for unknown-duration work.") { IndeterminateProgressDemo() },
    CatalogSection("Surfaces", "Tonal elevation and grouped content containers.") { SurfacesDemo() },
)

@Composable
private fun ExposedDropdownDemo() {
    val options = listOf("Default", "Comfortable", "Compact")
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(options.first()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text("Density") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected = option
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun LargerAppBarsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MediumTopAppBar(
            title = { Text("Medium top app bar") },
            navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = null) } },
        )
        LargeTopAppBar(
            title = { Text("Large top app bar") },
            navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = null) } },
            actions = { IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = null) } },
        )
    }
}

@Composable
private fun SecondaryTabsDemo() {
    var selected by remember { mutableIntStateOf(0) }
    SecondaryTabRow(selectedTabIndex = selected) {
        listOf("Details", "Activity", "Members").forEachIndexed { index, label ->
            Tab(
                selected = selected == index,
                onClick = { selected = index },
                text = { Text(label) },
            )
        }
    }
}

@Composable
private fun IndeterminateProgressDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            CircularProgressIndicator()
            Text("Circular progress", style = MaterialTheme.typography.bodyMedium)
        }
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun SurfacesDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = 0.dp) {
            ListItem(
                headlineContent = { Text("Base surface") },
                supportingContent = { Text("No tonal elevation") },
                leadingContent = { Icon(Icons.Default.Home, contentDescription = null) },
            )
        }
        HorizontalDivider()
        Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = 3.dp) {
            ListItem(
                headlineContent = { Text("Elevated tonal surface") },
                supportingContent = { Text("3 dp tonal elevation") },
                leadingContent = { Icon(Icons.Default.Settings, contentDescription = null) },
            )
        }
        Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = 6.dp, shadowElevation = 2.dp) {
            ListItem(
                headlineContent = { Text("Tonal + shadow surface") },
                supportingContent = { Text("Demonstrates combined elevation") },
                leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
            )
        }
    }
}
