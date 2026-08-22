@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

internal fun stableCompletionSections(onSnackbar: (String) -> Unit): List<CatalogSection> = listOf(
    CatalogSection("Remaining stable chips", "Elevated filter and elevated suggestion chip variants.") { StableChipVariantsDemo() },
    CatalogSection("All icon toggle variants", "Standard, filled, tonal and outlined icon toggle controls.") { AllIconToggleVariantsDemo() },
    CatalogSection("Small FAB", "Compact floating action button variant.") { SmallFabDemo() },
    CatalogSection("Date range picker", "Two-date range selection using the Material range calendar.") { DateRangePickerDemo() },
    CatalogSection("Permanent navigation drawer", "Always-visible drawer for expanded layouts.") { PermanentDrawerDemo() },
    CatalogSection("Dismissible navigation drawer", "Drawer that shifts app content and can be dismissed.") { DismissibleDrawerDemo() },
    CatalogSection("Full search bar", "Full search surface in addition to the docked search demo.") { FullSearchBarDemo(onSnackbar) },
    CatalogSection("Primary tabs", "Primary fixed tab row with text and leading-icon examples.") { PrimaryTabsDemo() },
    CatalogSection("Scrollable tabs", "Scrollable tab row for larger destination sets.") { ScrollableTabsDemo() },
    CatalogSection("Primary and secondary tab indicators", "Both hierarchy levels shown side by side.") { TabHierarchyDemo() },
    CatalogSection("Vertical divider", "Vertical separation for adjacent content regions.") { VerticalDividerDemo() },
)

@Composable
private fun StableChipVariantsDemo() {
    var filter by remember { mutableStateOf(false) }
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ElevatedFilterChip(
            selected = filter,
            onClick = { filter = !filter },
            label = { Text("Elevated filter") },
            leadingIcon = if (filter) ({ Icon(Icons.Default.Check, contentDescription = null) }) else null,
        )
        ElevatedSuggestionChip(onClick = {}, label = { Text("Elevated suggestion") })
    }
}

@Composable
private fun AllIconToggleVariantsDemo() {
    var a by remember { mutableStateOf(false) }
    var b by remember { mutableStateOf(true) }
    var c by remember { mutableStateOf(false) }
    var d by remember { mutableStateOf(true) }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        IconToggleButton(checked = a, onCheckedChange = { a = it }) { Icon(Icons.Default.Check, contentDescription = "Standard toggle") }
        FilledIconToggleButton(checked = b, onCheckedChange = { b = it }) { Icon(Icons.Default.Check, contentDescription = "Filled toggle") }
        FilledTonalIconToggleButton(checked = c, onCheckedChange = { c = it }) { Icon(Icons.Default.Check, contentDescription = "Tonal toggle") }
        OutlinedIconToggleButton(checked = d, onCheckedChange = { d = it }) { Icon(Icons.Default.Check, contentDescription = "Outlined toggle") }
    }
}

@Composable
private fun SmallFabDemo() {
    SmallFloatingActionButton(onClick = {}) { Icon(Icons.Default.Check, contentDescription = "Small FAB") }
}

@Composable
private fun DateRangePickerDemo() {
    val state = rememberDateRangePickerState()
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            if (state.selectedStartDateMillis == null) "No range selected"
            else "Start ${state.selectedStartDateMillis} · End ${state.selectedEndDateMillis ?: "…"}",
            style = MaterialTheme.typography.bodyMedium,
        )
        Box(modifier = Modifier.fillMaxWidth().height(420.dp)) {
            DateRangePicker(state = state, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun PermanentDrawerDemo() {
    var selected by remember { mutableIntStateOf(0) }
    val items = listOf("Home" to Icons.Default.Home, "Search" to Icons.Default.Search, "Settings" to Icons.Default.Settings)
    PermanentNavigationDrawer(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        drawerContent = {
            PermanentDrawerSheet(modifier = Modifier.width(190.dp)) {
                Spacer(Modifier.height(12.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(item.first) },
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = { Icon(item.second, contentDescription = null) },
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                }
            }
        },
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("${items[selected].first} content") }
    }
}

@Composable
private fun DismissibleDrawerDemo() {
    val state = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selected by remember { mutableIntStateOf(0) }
    DismissibleNavigationDrawer(
        drawerState = state,
        modifier = Modifier.fillMaxWidth().height(300.dp),
        drawerContent = {
            DismissibleDrawerSheet {
                listOf("Home", "Profile", "Settings").forEachIndexed { index, label ->
                    NavigationDrawerItem(
                        label = { Text(label) },
                        selected = selected == index,
                        onClick = { selected = index; scope.launch { state.close() } },
                        icon = { Icon(if (index == 0) Icons.Default.Home else if (index == 1) Icons.Default.Person else Icons.Default.Settings, null) },
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                }
            }
        },
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Dismissible drawer content")
                Button(onClick = { scope.launch { state.open() } }) { Icon(Icons.Default.Menu, null); Text(" Open") }
            }
        }
    }
}

@Suppress("DEPRECATION")
@Composable
private fun FullSearchBarDemo(onSnackbar: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { active = false; onSnackbar("Search: $it") },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search the catalog") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = { IconButton(onClick = { query = "" }) { Icon(Icons.Default.MoreVert, contentDescription = null) } },
        modifier = Modifier.fillMaxWidth(),
    ) {
        listOf("Buttons", "Cards", "Motion", "Navigation", "Pickers").filter { query.isBlank() || it.contains(query, true) }.forEach {
            ListItem(headlineContent = { Text(it) }, leadingContent = { Icon(Icons.Default.Search, null) })
            HorizontalDivider()
        }
    }
}

@Composable
private fun PrimaryTabsDemo() {
    var selected by remember { mutableIntStateOf(0) }
    PrimaryTabRow(selectedTabIndex = selected) {
        listOf("Home", "Search", "Profile").forEachIndexed { index, label ->
            if (index == 0) {
                LeadingIconTab(
                    selected = selected == index,
                    onClick = { selected = index },
                    text = { Text(label) },
                    icon = { Icon(Icons.Default.Home, null) },
                )
            } else {
                Tab(selected = selected == index, onClick = { selected = index }, text = { Text(label) })
            }
        }
    }
}

@Composable
private fun ScrollableTabsDemo() {
    var selected by remember { mutableIntStateOf(0) }
    ScrollableTabRow(selectedTabIndex = selected) {
        listOf("Overview", "Activity", "Files", "Members", "Settings", "History").forEachIndexed { index, label ->
            Tab(selected = selected == index, onClick = { selected = index }, text = { Text(label) })
        }
    }
}

@Composable
private fun TabHierarchyDemo() {
    var primary by remember { mutableIntStateOf(0) }
    var secondary by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        PrimaryTabRow(selectedTabIndex = primary) {
            listOf("Primary A", "Primary B").forEachIndexed { i, label -> Tab(selected = primary == i, onClick = { primary = i }, text = { Text(label) }) }
        }
        SecondaryTabRow(selectedTabIndex = secondary) {
            listOf("Secondary A", "Secondary B").forEachIndexed { i, label -> Tab(selected = secondary == i, onClick = { secondary = i }, text = { Text(label) }) }
        }
    }
}

@Composable
private fun VerticalDividerDemo() {
    Row(modifier = Modifier.fillMaxWidth().height(90.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) { Text("Left") }
        VerticalDivider(modifier = Modifier.fillMaxHeight().padding(vertical = 12.dp))
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) { Text("Right") }
    }
}
