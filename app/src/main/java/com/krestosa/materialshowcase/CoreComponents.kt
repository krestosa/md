@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

internal fun coreCatalogSections(
    onDialog: () -> Unit,
    onSheet: () -> Unit,
    onSnackbar: (String) -> Unit,
): List<CatalogSection> = listOf(
    CatalogSection("Buttons", "Filled, tonal, elevated, outlined and text actions.") { ButtonsDemo(onSnackbar) },
    CatalogSection("Icon buttons", "Standard, filled, tonal, outlined and toggle variants.") { IconButtonsDemo() },
    CatalogSection("Floating action buttons", "FAB sizes and extended action presentation.") { FloatingActionButtonsDemo() },
    CatalogSection("Segmented buttons", "Single-choice segmented control.") { SingleSegmentedButtonsDemo() },
    CatalogSection("Chips", "Assist, elevated assist, filter, input and suggestion chips.") { ChipsDemo() },
    CatalogSection("Cards", "Filled, elevated and outlined containers.") { CardsDemo() },
    CatalogSection("Selection controls", "Checkbox, tri-state checkbox, radio buttons and switch.") { SelectionControlsDemo() },
    CatalogSection("Sliders", "Continuous value and range selection.") { SlidersDemo() },
    CatalogSection("Text fields", "Filled and outlined text input.") { TextFieldsDemo() },
    CatalogSection("Progress indicators", "Determinate linear and circular progress.") { ProgressIndicatorsDemo() },
    CatalogSection("Badges", "Status and count badges attached to icons.") { BadgesDemo() },
    CatalogSection("Tabs", "Primary tab navigation.") { TabsDemo() },
    CatalogSection("Lists and dividers", "List items with leading/trailing content and dividers.") { ListsDemo() },
    CatalogSection("Top app bars", "Small and center-aligned app bar variants.") { TopAppBarsDemo() },
    CatalogSection("Bottom app bar", "Bottom actions with a floating primary action.") { BottomAppBarDemo() },
    CatalogSection("Navigation bar", "Bottom destination navigation with selectable items.") { NavigationBarDemo() },
    CatalogSection("Overlays", "Dialogs, modal bottom sheets and snackbar feedback.") { OverlayActionsDemo(onDialog, onSheet, onSnackbar) },
)

@Composable
private fun ButtonsDemo(onSnackbar: (String) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { onSnackbar("Filled button") }) { Text("Filled") }
        FilledTonalButton(onClick = { onSnackbar("Tonal button") }) { Text("Tonal") }
        ElevatedButton(onClick = { onSnackbar("Elevated button") }) { Text("Elevated") }
        OutlinedButton(onClick = { onSnackbar("Outlined button") }) { Text("Outlined") }
        TextButton(onClick = { onSnackbar("Text button") }) { Text("Text") }
    }
}

@Composable
private fun IconButtonsDemo() {
    var favorite by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {}) { Icon(Icons.Default.Favorite, "Standard") }
        FilledIconButton(onClick = {}) { Icon(Icons.Default.Add, "Filled") }
        FilledTonalIconButton(onClick = {}) { Icon(Icons.Default.Settings, "Tonal") }
        OutlinedIconButton(onClick = {}) { Icon(Icons.Default.Search, "Outlined") }
        FilledIconToggleButton(checked = favorite, onCheckedChange = { favorite = it }) {
            Icon(if (favorite) Icons.Default.Check else Icons.Default.Favorite, "Toggle")
        }
    }
}

@Composable
private fun FloatingActionButtonsDemo() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, "FAB") }
        LargeFloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, "Large FAB") }
        ExtendedFloatingActionButton(onClick = {}, icon = { Icon(Icons.Default.Add, null) }, text = { Text("Create") })
    }
}

@Composable
private fun SingleSegmentedButtonsDemo() {
    var selected by remember { mutableIntStateOf(0) }
    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        listOf("Day", "Week", "Month").forEachIndexed { index, label ->
            SegmentedButton(
                selected = selected == index,
                onClick = { selected = index },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = 3),
            ) { Text(label) }
        }
    }
}

@Composable
private fun ChipsDemo() {
    var filter by remember { mutableStateOf(false) }
    var inputChip by remember { mutableStateOf(true) }
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AssistChip(onClick = {}, label = { Text("Assist") })
        ElevatedAssistChip(onClick = {}, label = { Text("Elevated") })
        FilterChip(
            selected = filter,
            onClick = { filter = !filter },
            label = { Text("Filter") },
            leadingIcon = if (filter) ({ Icon(Icons.Default.Check, null) }) else null,
        )
        if (inputChip) {
            InputChip(
                selected = true,
                onClick = { inputChip = false },
                label = { Text("Input") },
                trailingIcon = { Icon(Icons.Default.Close, null) },
            )
        }
        SuggestionChip(onClick = {}, label = { Text("Suggestion") })
    }
}

@Composable
private fun CardsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) { DemoCardContent("Filled card") }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) { DemoCardContent("Elevated card") }
        OutlinedCard(modifier = Modifier.fillMaxWidth()) { DemoCardContent("Outlined card") }
    }
}

@Composable
private fun SelectionControlsDemo() {
    var checked by remember { mutableStateOf(true) }
    var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }
    var radio by remember { mutableIntStateOf(0) }
    var switched by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        LabeledControl("Checkbox") { Checkbox(checked = checked, onCheckedChange = { checked = it }) }
        LabeledControl("Tri-state") {
            TriStateCheckbox(
                state = triState,
                onClick = {
                    triState = when (triState) {
                        ToggleableState.On -> ToggleableState.Off
                        ToggleableState.Off -> ToggleableState.Indeterminate
                        ToggleableState.Indeterminate -> ToggleableState.On
                    }
                },
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = radio == 0, onClick = { radio = 0 })
            Text("Option A")
            Spacer(Modifier.width(16.dp))
            RadioButton(selected = radio == 1, onClick = { radio = 1 })
            Text("Option B")
        }
        LabeledControl("Switch") { Switch(checked = switched, onCheckedChange = { switched = it }) }
    }
}

@Composable
private fun SlidersDemo() {
    var slider by remember { mutableFloatStateOf(0.42f) }
    var range by remember { mutableStateOf(0.2f..0.8f) }
    Column {
        Text("Value ${(slider * 100).toInt()}%", style = MaterialTheme.typography.labelMedium)
        Slider(value = slider, onValueChange = { slider = it })
        Text("Range ${(range.start * 100).toInt()}–${(range.endInclusive * 100).toInt()}%", style = MaterialTheme.typography.labelMedium)
        RangeSlider(value = range, onValueChange = { range = it })
    }
}

@Composable
private fun TextFieldsDemo() {
    var text by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Text field") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Outlined field") },
            supportingText = { Text("Supporting text") },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ProgressIndicatorsDemo() {
    var progress by remember { mutableFloatStateOf(0.42f) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(progress = { progress })
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.weight(1f))
        }
        Slider(value = progress, onValueChange = { progress = it })
    }
}

@Composable
private fun BadgesDemo() {
    Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
        BadgedBox(badge = { Badge() }) { Icon(Icons.Default.Notifications, null) }
        BadgedBox(badge = { Badge { Text("8") } }) { Icon(Icons.Default.Notifications, null) }
        BadgedBox(badge = { Badge { Text("99+") } }) { Icon(Icons.Default.Notifications, null) }
    }
}

@Composable
private fun TabsDemo() {
    var selectedTab by remember { mutableIntStateOf(0) }
    TabRow(selectedTabIndex = selectedTab) {
        listOf("Overview", "Usage", "Specs").forEachIndexed { index, label ->
            Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(label) })
        }
    }
}

@Composable
private fun ListsDemo() {
    Column {
        ListItem(
            headlineContent = { Text("Headline") },
            supportingContent = { Text("Supporting text") },
            leadingContent = { Icon(Icons.Default.Person, null) },
            trailingContent = { Text("Meta") },
        )
        HorizontalDivider()
        ListItem(
            headlineContent = { Text("Settings") },
            supportingContent = { Text("Secondary information") },
            leadingContent = { Icon(Icons.Default.Settings, null) },
        )
    }
}

@Composable
private fun TopAppBarsDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TopAppBar(
            title = { Text("Small top app bar") },
            navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) } },
        )
        CenterAlignedTopAppBar(
            title = { Text("Centered app bar") },
            actions = { IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) } },
        )
    }
}

@Composable
private fun BottomAppBarDemo() {
    BottomAppBar(
        actions = {
            IconButton(onClick = {}) { Icon(Icons.Default.Home, null) }
            IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
            IconButton(onClick = {}) { Icon(Icons.Default.Settings, null) }
        },
        floatingActionButton = { FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, null) } },
    )
}

@Composable
private fun NavigationBarDemo() {
    var selected by remember { mutableIntStateOf(0) }
    NavigationBar {
        listOf(
            "Home" to Icons.Default.Home,
            "Search" to Icons.Default.Search,
            "Profile" to Icons.Default.Person,
        ).forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { selected = index },
                icon = { Icon(item.second, contentDescription = item.first) },
                label = { Text(item.first) },
            )
        }
    }
}

@Composable
private fun OverlayActionsDemo(
    onDialog: () -> Unit,
    onSheet: () -> Unit,
    onSnackbar: (String) -> Unit,
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = onDialog) { Text("Dialog") }
        FilledTonalButton(onClick = onSheet) { Text("Bottom sheet") }
        OutlinedButton(onClick = { onSnackbar("Snackbar message") }) { Text("Snackbar") }
    }
}

@Composable
private fun DemoCardContent(title: String) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
        Text("Surface hierarchy, content and supporting copy.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun LabeledControl(label: String, control: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label)
        control()
    }
}
