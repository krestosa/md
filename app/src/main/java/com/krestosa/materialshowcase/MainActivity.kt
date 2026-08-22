package com.krestosa.materialshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MaterialShowcaseApp() }
    }
}

private data class CatalogSection(
    val title: String,
    val description: String,
    val content: @Composable () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialShowcaseApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    MaterialTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Material Components", fontWeight = FontWeight.SemiBold)
                            Text("Interactive showcase", style = MaterialTheme.typography.labelSmall)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Navigation icon") } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        BadgedBox(badge = { Badge { Text("3") } }) {
                            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Notifications") } }) {
                                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                            }
                        }
                        Box {
                            IconButton(onClick = { menuExpanded = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More")
                            }
                            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                                DropdownMenuItem(
                                    text = { Text("About") },
                                    leadingIcon = { Icon(Icons.Default.Info, null) },
                                    onClick = {
                                        menuExpanded = false
                                        showDialog = true
                                    },
                                )
                                DropdownMenuItem(
                                    text = { Text("Settings") },
                                    leadingIcon = { Icon(Icons.Default.Settings, null) },
                                    onClick = { menuExpanded = false },
                                )
                            }
                        }
                    },
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Catalog") })
                    NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Favorite, null) }, label = { Text("Favorites") })
                    NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
                }
            },
        ) { innerPadding ->
            CatalogContent(
                modifier = Modifier.padding(innerPadding),
                onDialog = { showDialog = true },
                onSheet = { showSheet = true },
                onSnackbar = { message -> scope.launch { snackbarHostState.showSnackbar(message) } },
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                icon = { Icon(Icons.Default.Info, null) },
                title = { Text("Alert dialog") },
                text = { Text("Dialogs interrupt the current task to communicate information or request a decision.") },
                confirmButton = { TextButton(onClick = { showDialog = false }) { Text("Confirm") } },
                dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } },
            )
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("Modal bottom sheet", style = MaterialTheme.typography.headlineSmall)
                    Text("A modal surface for secondary content and focused actions.")
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion { showSheet = false }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) { Text("Close") }
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogContent(
    modifier: Modifier = Modifier,
    onDialog: () -> Unit,
    onSheet: () -> Unit,
    onSnackbar: (String) -> Unit,
) {
    var checked by remember { mutableStateOf(true) }
    var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }
    var radio by remember { mutableIntStateOf(0) }
    var switched by remember { mutableStateOf(true) }
    var slider by remember { mutableFloatStateOf(0.42f) }
    var range by remember { mutableStateOf(0.2f..0.8f) }
    var text by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedSegment by remember { mutableIntStateOf(0) }
    var favorite by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf(false) }
    var inputChip by remember { mutableStateOf(true) }

    val sections = listOf(
        CatalogSection("Buttons", "Filled, tonal, elevated, outlined and text actions.") {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onSnackbar("Filled button") }) { Text("Filled") }
                FilledTonalButton(onClick = { onSnackbar("Tonal button") }) { Text("Tonal") }
                ElevatedButton(onClick = { onSnackbar("Elevated button") }) { Text("Elevated") }
                OutlinedButton(onClick = { onSnackbar("Outlined button") }) { Text("Outlined") }
                TextButton(onClick = { onSnackbar("Text button") }) { Text("Text") }
            }
        },
        CatalogSection("Icon buttons", "Standard, filled, tonal, outlined and toggle variants.") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) { Icon(Icons.Default.Favorite, "Standard") }
                FilledIconButton(onClick = {}) { Icon(Icons.Default.Add, "Filled") }
                FilledTonalIconButton(onClick = {}) { Icon(Icons.Default.Settings, "Tonal") }
                OutlinedIconButton(onClick = {}) { Icon(Icons.Default.Search, "Outlined") }
                FilledIconToggleButton(checked = favorite, onCheckedChange = { favorite = it }) {
                    Icon(if (favorite) Icons.Default.Check else Icons.Default.Favorite, "Toggle")
                }
            }
        },
        CatalogSection("Floating action buttons", "FAB sizes and extended action presentation.") {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, "FAB") }
                LargeFloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, "Large FAB") }
                ExtendedFloatingActionButton(onClick = {}, icon = { Icon(Icons.Default.Add, null) }, text = { Text("Create") })
            }
        },
        CatalogSection("Segmented buttons", "Single-choice segmented control.") {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                listOf("Day", "Week", "Month").forEachIndexed { index, label ->
                    SegmentedButton(
                        selected = selectedSegment == index,
                        onClick = { selectedSegment = index },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = 3),
                    ) { Text(label) }
                }
            }
        },
        CatalogSection("Chips", "Assist, elevated assist, filter, input and suggestion chips.") {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text("Assist") })
                ElevatedAssistChip(onClick = {}, label = { Text("Elevated") })
                FilterChip(selected = filter, onClick = { filter = !filter }, label = { Text("Filter") }, leadingIcon = if (filter) ({ Icon(Icons.Default.Check, null) }) else null)
                if (inputChip) InputChip(selected = true, onClick = { inputChip = false }, label = { Text("Input") }, trailingIcon = { Icon(Icons.Default.Close, null) })
                SuggestionChip(onClick = {}, label = { Text("Suggestion") })
            }
        },
        CatalogSection("Cards", "Filled, elevated and outlined containers.") {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Card(modifier = Modifier.fillMaxWidth()) { DemoCardContent("Filled card") }
                ElevatedCard(modifier = Modifier.fillMaxWidth()) { DemoCardContent("Elevated card") }
                OutlinedCard(modifier = Modifier.fillMaxWidth()) { DemoCardContent("Outlined card") }
            }
        },
        CatalogSection("Selection controls", "Checkbox, tri-state checkbox, radio buttons and switch.") {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                LabeledControl("Checkbox") { Checkbox(checked = checked, onCheckedChange = { checked = it }) }
                LabeledControl("Tri-state") {
                    TriStateCheckbox(state = triState, onClick = {
                        triState = when (triState) {
                            ToggleableState.On -> ToggleableState.Off
                            ToggleableState.Off -> ToggleableState.Indeterminate
                            ToggleableState.Indeterminate -> ToggleableState.On
                        }
                    })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = radio == 0, onClick = { radio = 0 }); Text("Option A")
                    Spacer(Modifier.width(16.dp))
                    RadioButton(selected = radio == 1, onClick = { radio = 1 }); Text("Option B")
                }
                LabeledControl("Switch") { Switch(checked = switched, onCheckedChange = { switched = it }) }
            }
        },
        CatalogSection("Sliders", "Continuous value and range selection.") {
            Column {
                Text("Value ${(slider * 100).toInt()}%", style = MaterialTheme.typography.labelMedium)
                Slider(value = slider, onValueChange = { slider = it })
                Text("Range ${(range.start * 100).toInt()}–${(range.endInclusive * 100).toInt()}%", style = MaterialTheme.typography.labelMedium)
                RangeSlider(value = range, onValueChange = { range = it })
            }
        },
        CatalogSection("Text fields", "Filled and outlined text input.") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TextField(value = text, onValueChange = { text = it }, label = { Text("Text field") }, leadingIcon = { Icon(Icons.Default.Search, null) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Outlined field") }, supportingText = { Text("Supporting text") }, modifier = Modifier.fillMaxWidth())
            }
        },
        CatalogSection("Progress indicators", "Determinate linear and circular progress.") {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(progress = { slider })
                LinearProgressIndicator(progress = { slider }, modifier = Modifier.weight(1f))
            }
        },
        CatalogSection("Badges", "Status and count badges attached to icons.") {
            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                BadgedBox(badge = { Badge() }) { Icon(Icons.Default.Notifications, null) }
                BadgedBox(badge = { Badge { Text("8") } }) { Icon(Icons.Default.Notifications, null) }
                BadgedBox(badge = { Badge { Text("99+") } }) { Icon(Icons.Default.Notifications, null) }
            }
        },
        CatalogSection("Tabs", "Primary tab navigation.") {
            TabRow(selectedTabIndex = selectedTab) {
                listOf("Overview", "Usage", "Specs").forEachIndexed { index, label ->
                    Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(label) })
                }
            }
        },
        CatalogSection("Lists and dividers", "List items with leading/trailing content and dividers.") {
            Column {
                ListItem(headlineContent = { Text("Headline") }, supportingContent = { Text("Supporting text") }, leadingContent = { Icon(Icons.Default.Person, null) }, trailingContent = { Text("Meta") })
                HorizontalDivider()
                ListItem(headlineContent = { Text("Settings") }, supportingContent = { Text("Secondary information") }, leadingContent = { Icon(Icons.Default.Settings, null) })
            }
        },
        CatalogSection("Top app bars", "Small and center-aligned app bar variants.") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TopAppBar(title = { Text("Small top app bar") }, navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) } })
                CenterAlignedTopAppBar(title = { Text("Centered app bar") }, actions = { IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) } })
            }
        },
        CatalogSection("Bottom app bar", "Bottom actions with a floating primary action.") {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Home, null) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Settings, null) }
                },
                floatingActionButton = { FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, null) } },
            )
        },
        CatalogSection("Overlays", "Dialogs, modal bottom sheets and snackbar feedback.") {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onDialog) { Text("Dialog") }
                FilledTonalButton(onClick = onSheet) { Text("Bottom sheet") }
                OutlinedButton(onClick = { onSnackbar("Snackbar message") }) { Text("Snackbar") }
            }
        },
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text("Component catalog", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("A functional reference surface for Material components, states, hierarchy and interaction patterns.", style = MaterialTheme.typography.bodyLarge)
        }
        items(sections) { section ->
            ComponentSection(section)
        }
        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun ComponentSection(section: CatalogSection) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(section.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(section.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            section.content()
        }
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
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label)
        control()
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun MaterialShowcasePreview() {
    MaterialShowcaseApp()
}
