@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

internal fun extendedNavigationSections(onSnackbar: (String) -> Unit): List<CatalogSection> = listOf(
    CatalogSection("More icon toggles", "Filled tonal and outlined toggle icon buttons.") { MoreIconTogglesDemo() },
    CatalogSection("Multi-choice segmented buttons", "Independent selections in a segmented control.") { MultiChoiceSegmentedDemo() },
    CatalogSection("Navigation drawer", "Modal drawer with selectable destinations and gesture state.") { NavigationDrawerDemo() },
    CatalogSection("Navigation rail", "Vertical destination navigation for wider layouts.") { NavigationRailDemo() },
    CatalogSection("Search", "Docked search bar with query input and dynamic results.") { SearchDemo(onSnackbar) },
    CatalogSection("Date picker", "Calendar date selection inside the Material date picker dialog.") { DatePickerDemo() },
    CatalogSection("Time picker", "Clock-face picker and keyboard-style time input sharing one state.") { TimePickerDemo() },
    CatalogSection("Swipe to dismiss", "Gesture-driven dismissible content with restoration.") { SwipeToDismissDemo() },
)

@Composable
private fun MoreIconTogglesDemo() {
    var tonalChecked by remember { mutableStateOf(true) }
    var outlinedChecked by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        FilledTonalIconToggleButton(checked = tonalChecked, onCheckedChange = { tonalChecked = it }) {
            Icon(if (tonalChecked) Icons.Default.Check else Icons.Default.Favorite, contentDescription = "Tonal toggle")
        }
        OutlinedIconToggleButton(checked = outlinedChecked, onCheckedChange = { outlinedChecked = it }) {
            Icon(if (outlinedChecked) Icons.Default.Check else Icons.Default.Settings, contentDescription = "Outlined toggle")
        }
    }
}

@Composable
private fun MultiChoiceSegmentedDemo() {
    val selected = remember { mutableStateListOf(0, 2) }
    val labels = listOf("Bold", "Italic", "Underline")
    MultiChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        labels.forEachIndexed { index, label ->
            SegmentedButton(
                checked = index in selected,
                onCheckedChange = {
                    if (index in selected) selected.remove(index) else selected.add(index)
                },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = labels.size),
            ) { Text(label) }
        }
    }
}

@Composable
private fun NavigationDrawerDemo() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selected by remember { mutableIntStateOf(0) }
    val destinations = listOf(
        "Home" to Icons.Default.Home,
        "Favorites" to Icons.Default.Favorite,
        "Settings" to Icons.Default.Settings,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth().height(260.dp),
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Destinations",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                destinations.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(item.first) },
                        selected = selected == index,
                        onClick = {
                            selected = index
                            scope.launch { drawerState.close() }
                        },
                        icon = { Icon(item.second, contentDescription = null) },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                }
            }
        },
    ) {
        Surface(modifier = Modifier.fillMaxSize(), tonalElevation = 1.dp) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Selected: ${destinations[selected].first}", style = MaterialTheme.typography.titleMedium)
                Text("The drawer is constrained to this demo surface so it can be tested inside the catalog.")
                Button(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(Icons.Default.Menu, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Open drawer")
                }
            }
        }
    }
}

@Composable
private fun NavigationRailDemo() {
    var selected by remember { mutableIntStateOf(0) }
    val destinations = listOf(
        "Home" to Icons.Default.Home,
        "Search" to Icons.Default.Search,
        "Profile" to Icons.Default.Person,
    )

    Row(modifier = Modifier.fillMaxWidth().height(260.dp)) {
        NavigationRail {
            destinations.forEachIndexed { index, item ->
                NavigationRailItem(
                    selected = selected == index,
                    onClick = { selected = index },
                    icon = { Icon(item.second, contentDescription = item.first) },
                    label = { Text(item.first) },
                )
            }
        }
        Surface(modifier = Modifier.weight(1f).fillMaxHeight(), tonalElevation = 1.dp) {
            Box(contentAlignment = Alignment.Center) {
                Text("${destinations[selected].first} content", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
private fun SearchDemo(onSnackbar: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val results = listOf("Buttons", "Cards", "Navigation", "Pickers", "Progress", "Search", "Tabs")
    val filtered = results.filter { query.isBlank() || it.contains(query, ignoreCase = true) }

    DockedSearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            active = false
            onSnackbar("Search: $it")
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search components") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
    ) {
        filtered.take(5).forEach { result ->
            ListItem(
                headlineContent = { Text(result) },
                leadingContent = { Icon(Icons.Default.Search, contentDescription = null) },
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun DatePickerDemo() {
    var showDialog by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = state.selectedDateMillis?.let { "Selected timestamp: $it" } ?: "No date selected",
            style = MaterialTheme.typography.bodyMedium,
        )
        Button(onClick = { showDialog = true }) {
            Icon(Icons.Default.Today, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Choose date")
        }
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }, enabled = state.selectedDateMillis != null) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } },
        ) {
            DatePicker(state = state)
        }
    }
}

@Composable
private fun TimePickerDemo() {
    val state = rememberTimePickerState(initialHour = 10, initialMinute = 30, is24Hour = true)
    var showClock by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Selected ${state.hour.toString().padStart(2, '0')}:${state.minute.toString().padStart(2, '0')}")
        TimeInput(state = state)
        OutlinedButton(onClick = { showClock = true }) {
            Icon(Icons.Default.AccessTime, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Open clock picker")
        }
    }

    if (showClock) {
        AlertDialog(
            onDismissRequest = { showClock = false },
            title = { Text("Choose time") },
            text = { TimePicker(state = state) },
            confirmButton = { TextButton(onClick = { showClock = false }) { Text("OK") } },
            dismissButton = { TextButton(onClick = { showClock = false }) { Text("Cancel") } },
        )
    }
}

@Composable
private fun SwipeToDismissDemo() {
    val dismissState = rememberSwipeToDismissBoxState()
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(true) }

    if (visible) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                val alignment = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                    else -> Alignment.CenterEnd
                }
                Box(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.errorContainer).padding(horizontal = 20.dp),
                    contentAlignment = alignment,
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.onErrorContainer)
                }
            },
            onDismiss = { direction ->
                if (direction == SwipeToDismissBoxValue.EndToStart) {
                    visible = false
                } else {
                    scope.launch { dismissState.reset() }
                }
            },
        ) {
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = { Text("Swipe this item") },
                    supportingContent = { Text("Swipe left to dismiss; swipe right resets") },
                    leadingContent = { Icon(Icons.Default.Favorite, contentDescription = null) },
                )
            }
        }
    } else {
        OutlinedButton(
            onClick = {
                scope.launch {
                    dismissState.reset()
                    visible = true
                }
            },
        ) { Text("Restore dismissed item") }
    }
}
