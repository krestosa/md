@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class,
)

package com.krestosa.materialshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ExpandedDockedSearchBar
import androidx.compose.material3.ExpandedDockedSearchBarWithGap
import androidx.compose.material3.ExpandedFullScreenContainedSearchBar
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalWideNavigationRail
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.rememberContainedSearchBarState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberSearchBarWithGapState
import androidx.compose.material3.rememberWideNavigationRailState
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

internal fun finalLabsSections(): List<CatalogSection> = listOf(
    CatalogSection("Modal wide navigation rail", "Modal expandable rail that overlays content with a scrim.") { ModalWideRailDemo() },
    CatalogSection("Dismissible modal wide rail", "Modal wide rail variant that hides fully when collapsed.") { DismissibleModalWideRailDemo() },
    CatalogSection("Toggle floating action button", "Toggleable FAB with checked progress used by FAB menus.") { ToggleFabDemo() },
    CatalogSection("Stateful search bar", "Current state-based collapsed SearchBar and full-screen expanded view.") { StatefulFullScreenSearchDemo() },
    CatalogSection("Stateful docked search", "Current state-based SearchBar with docked popup results.") { StatefulDockedSearchDemo() },
    CatalogSection("Docked search with gap", "Expanded docked search variant with a visual gap from its collapsed field.") { SearchWithGapDemo() },
    CatalogSection("Contained full-screen search", "Full-screen search preserving the collapsed search shape without a divider.") { ContainedSearchDemo() },
    CatalogSection("App bar with search", "Top app-bar integration for the current state-based search architecture.") { AppBarWithSearchDemo() },
)

@Composable
private fun ModalWideRailDemo() {
    val state = rememberWideNavigationRailState()
    val scope = rememberCoroutineScope()
    var selected by remember { mutableIntStateOf(0) }
    val items = listOf("Home" to Icons.Default.Home, "Search" to Icons.Default.Search, "Settings" to Icons.Default.Settings)
    Row(modifier = Modifier.fillMaxWidth().height(320.dp)) {
        ModalWideNavigationRail(
            state = state,
            header = {
                IconButton(onClick = { scope.launch { if (state.targetValue == WideNavigationRailValue.Expanded) state.collapse() else state.expand() } }) {
                    Icon(Icons.Default.Menu, contentDescription = "Toggle rail")
                }
            },
        ) {
            items.forEachIndexed { index, item ->
                WideNavigationRailItem(
                    railExpanded = state.targetValue == WideNavigationRailValue.Expanded,
                    selected = selected == index,
                    onClick = { selected = index },
                    icon = { Icon(item.second, contentDescription = null) },
                    label = { Text(item.first) },
                )
            }
        }
        Box(modifier = Modifier.weight(1f).fillMaxSize(), contentAlignment = Alignment.Center) { Text(items[selected].first) }
    }
}

@Composable
private fun DismissibleModalWideRailDemo() {
    val state = rememberWideNavigationRailState()
    val scope = rememberCoroutineScope()
    var selected by remember { mutableIntStateOf(0) }
    Row(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        ModalWideNavigationRail(state = state, hideOnCollapse = true) {
            listOf("Home", "Search", "Settings").forEachIndexed { index, label ->
                WideNavigationRailItem(
                    railExpanded = true,
                    selected = selected == index,
                    onClick = { selected = index; scope.launch { state.collapse() } },
                    icon = { Icon(if (index == 0) Icons.Default.Home else if (index == 1) Icons.Default.Search else Icons.Default.Settings, null) },
                    label = { Text(label) },
                )
            }
        }
        Column(modifier = Modifier.weight(1f).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { scope.launch { state.expand() } }) { Text("Open modal rail") }
            Text("Selected: ${listOf("Home", "Search", "Settings")[selected]}")
        }
    }
}

@Composable
private fun ToggleFabDemo() {
    var checked by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
        ToggleFloatingActionButton(checked = checked, onCheckedChange = { checked = it }) {
            Icon(if (checkedProgress > 0.5f) Icons.Default.Close else Icons.Default.Add, contentDescription = null)
        }
        Text(if (checked) "Checked" else "Unchecked")
    }
}

@Composable
private fun StatefulFullScreenSearchDemo() {
    val textState = rememberTextFieldState()
    val searchState = rememberSearchBarState()
    val scope = rememberCoroutineScope()
    val input = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textState,
            searchBarState = searchState,
            onSearch = { scope.launch { searchState.animateToCollapsed() } },
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
        )
    }
    Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
        SearchBar(state = searchState, inputField = input, modifier = Modifier.fillMaxWidth())
        ExpandedFullScreenSearchBar(state = searchState, inputField = input) { SearchResults() }
    }
}

@Composable
private fun StatefulDockedSearchDemo() {
    val textState = rememberTextFieldState()
    val searchState = rememberSearchBarState()
    val scope = rememberCoroutineScope()
    val input = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textState,
            searchBarState = searchState,
            onSearch = { scope.launch { searchState.animateToCollapsed() } },
            placeholder = { Text("Docked search") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
        )
    }
    Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
        SearchBar(state = searchState, inputField = input, modifier = Modifier.fillMaxWidth())
        ExpandedDockedSearchBar(state = searchState, inputField = input, modifier = Modifier.fillMaxWidth()) { SearchResults() }
    }
}

@Composable
private fun SearchWithGapDemo() {
    val textState = rememberTextFieldState()
    val searchState = rememberSearchBarWithGapState()
    val scope = rememberCoroutineScope()
    val input = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textState,
            searchBarState = searchState,
            onSearch = { scope.launch { searchState.animateToCollapsed() } },
            placeholder = { Text("Search with gap") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
        )
    }
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        SearchBar(state = searchState, inputField = input, modifier = Modifier.fillMaxWidth())
        ExpandedDockedSearchBarWithGap(state = searchState, inputField = input, modifier = Modifier.fillMaxWidth()) { SearchResults() }
    }
}

@Composable
private fun ContainedSearchDemo() {
    val textState = rememberTextFieldState()
    val searchState = rememberContainedSearchBarState()
    val scope = rememberCoroutineScope()
    val input = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textState,
            searchBarState = searchState,
            onSearch = { scope.launch { searchState.animateToCollapsed() } },
            placeholder = { Text("Contained search") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
        )
    }
    Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
        SearchBar(state = searchState, inputField = input, colors = SearchBarDefaults.containedColors(searchState), modifier = Modifier.fillMaxWidth())
        ExpandedFullScreenContainedSearchBar(state = searchState, inputField = input) { SearchResults() }
    }
}

@Composable
private fun AppBarWithSearchDemo() {
    val textState = rememberTextFieldState()
    val searchState = rememberContainedSearchBarState()
    val scope = rememberCoroutineScope()
    val colors = SearchBarDefaults.appBarWithSearchColors(searchBarColors = SearchBarDefaults.containedColors(searchState))
    val input = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textState,
            searchBarState = searchState,
            colors = colors.searchBarColors.inputFieldColors,
            onSearch = { scope.launch { searchState.animateToCollapsed() } },
            placeholder = { Text("Search app bar") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
        )
    }
    Column(modifier = Modifier.fillMaxWidth().height(320.dp)) {
        AppBarWithSearch(
            state = searchState,
            inputField = input,
            colors = colors,
            navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) } },
            actions = { IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) } },
        )
        ExpandedFullScreenContainedSearchBar(state = searchState, inputField = input, colors = colors.searchBarColors) { SearchResults() }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("App content") }
    }
}

@Composable
private fun SearchResults() {
    Column(modifier = Modifier.fillMaxWidth()) {
        listOf("Result one", "Result two", "Result three").forEach { ListItem(headlineContent = { Text(it) }, leadingContent = { Icon(Icons.Default.Search, null) }) }
    }
}
