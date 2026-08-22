@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AppBarColumn
import androidx.compose.material3.AppBarRow
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeExtendedFloatingActionButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumExtendedFloatingActionButton
import androidx.compose.material3.MediumFloatingActionButton
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SmallExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.VerticalSlider
import androidx.compose.material3.rememberSliderState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun expressiveCompletionSections(): List<CatalogSection> = listOf(
    CatalogSection("Flexible bottom app bar", "Expressive bottom app bar with flexible spacing and height behavior.") { FlexibleBottomBarDemo() },
    CatalogSection("Flexible top app bars", "Medium and large flexible top app bars with subtitle support.") { FlexibleTopBarsDemo() },
    CatalogSection("Medium and extended FAB sizes", "Medium FAB plus small, medium and large extended FAB families.") { ExpressiveFabSizesDemo() },
    CatalogSection("Loading indicators", "Standard and contained Material loading indicators.") { LoadingIndicatorsDemo() },
    CatalogSection("Wavy progress indicators", "Circular and linear expressive wavy progress indicators.") { WavyProgressDemo() },
    CatalogSection("Vertical slider", "Vertical value control from the current Material surface.") { VerticalSliderDemo() },
    CatalogSection("Secure text field", "Password-oriented secure text input using state-based text fields.") { SecureTextFieldDemo() },
    CatalogSection("Segmented list", "Connected list items with first, middle and last segmented shapes.") { SegmentedListDemo() },
    CatalogSection("App bar overflow row", "Horizontal app-bar DSL with automatic overflow menu.") { AppBarRowDemo() },
    CatalogSection("App bar overflow column", "Vertical app-bar DSL with automatic overflow menu.") { AppBarColumnDemo() },
    CatalogSection("Time picker dialog", "Dedicated Material time-picker dialog component.") { TimePickerDialogDemo() },
)

@Composable
private fun FlexibleBottomBarDemo() {
    FlexibleBottomAppBar(modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {}) { Icon(Icons.Default.Home, contentDescription = "Home") }
        IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = "Search") }
        IconButton(onClick = {}) { Icon(Icons.Default.Favorite, contentDescription = "Favorite") }
        IconButton(onClick = {}) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
    }
}

@Composable
private fun FlexibleTopBarsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        MediumFlexibleTopAppBar(
            title = { Text("Medium flexible") },
            subtitle = { Text("Supporting subtitle") },
            navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Home, null) } },
            actions = { IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) } },
        )
        LargeFlexibleTopAppBar(
            title = { Text("Large flexible") },
            subtitle = { Text("Supporting subtitle") },
            navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Home, null) } },
            actions = { IconButton(onClick = {}) { Icon(Icons.Default.Search, null) } },
        )
    }
}

@Composable
private fun ExpressiveFabSizesDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            MediumFloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Medium FAB") }
            SmallExtendedFloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, null); Text(" Small") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            MediumExtendedFloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, null); Text(" Medium") }
            LargeExtendedFloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, null); Text(" Large") }
        }
    }
}

@Composable
private fun LoadingIndicatorsDemo() {
    Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
        LoadingIndicator()
        ContainedLoadingIndicator()
        Text("Loading")
    }
}

@Composable
private fun WavyProgressDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp), verticalAlignment = Alignment.CenterVertically) {
            CircularWavyProgressIndicator()
            Text("Circular wavy")
        }
        LinearWavyProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun VerticalSliderDemo() {
    val state = rememberSliderState(value = 0.55f)
    Row(modifier = Modifier.fillMaxWidth().height(240.dp), horizontalArrangement = Arrangement.Center) {
        VerticalSlider(state = state, modifier = Modifier.height(220.dp))
    }
}

@Composable
private fun SecureTextFieldDemo() {
    val state = rememberTextFieldState()
    SecureTextField(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
    )
}

@Composable
private fun SegmentedListDemo() {
    val labels = listOf("First item", "Middle item", "Last item")
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        labels.forEachIndexed { index, label ->
            SegmentedListItem(
                shapes = ListItemDefaults.segmentedShapes(index = index, count = labels.size),
                headlineContent = { Text(label) },
                supportingContent = { Text("Connected segmented surface") },
                leadingContent = { Icon(Icons.Default.Settings, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun AppBarRowDemo() {
    var favorite by remember { mutableStateOf(false) }
    AppBarRow(modifier = Modifier.fillMaxWidth(), maxItemCount = 3) {
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = "Home")
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.Search, null) }, label = "Search")
        toggleableItem(checked = favorite, onCheckedChange = { favorite = it }, icon = { Icon(Icons.Default.Favorite, null) }, label = "Favorite")
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.Settings, null) }, label = "Settings")
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.MoreVert, null) }, label = "More")
    }
}

@Composable
private fun AppBarColumnDemo() {
    AppBarColumn(modifier = Modifier.height(180.dp), maxItemCount = 3) {
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = "Home")
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.Search, null) }, label = "Search")
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.Settings, null) }, label = "Settings")
        clickableItem(onClick = {}, icon = { Icon(Icons.Default.MoreVert, null) }, label = "More")
    }
}

@Composable
private fun TimePickerDialogDemo() {
    var open by remember { mutableStateOf(false) }
    val state = rememberTimePickerState(initialHour = 12, initialMinute = 30)
    androidx.compose.material3.Button(onClick = { open = true }) { Text("Open time picker dialog") }
    if (open) {
        TimePickerDialog(
            onDismissRequest = { open = false },
            confirmButton = { androidx.compose.material3.TextButton(onClick = { open = false }) { Text("OK") } },
            dismissButton = { androidx.compose.material3.TextButton(onClick = { open = false }) { Text("Cancel") } },
            title = { Text("Select time") },
        ) {
            TimePicker(state = state)
        }
    }
}
