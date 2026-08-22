package com.krestosa.materialshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

internal data class CatalogSection(
    val title: String,
    val description: String,
    val content: @Composable () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialShowcaseApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var showDialog by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    var selectedDestination by remember { mutableIntStateOf(0) }

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
                        androidx.compose.foundation.layout.Box {
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
                                    onClick = {
                                        menuExpanded = false
                                        scope.launch { snackbarHostState.showSnackbar("Settings") }
                                    },
                                )
                            }
                        }
                    },
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                NavigationBar {
                    val destinations = listOf(
                        Triple("Catalog", Icons.Default.Home, "Catalog"),
                        Triple("Favorites", Icons.Default.Favorite, "Favorites"),
                        Triple("Profile", Icons.Default.Person, "Profile"),
                    )
                    destinations.forEachIndexed { index, destination ->
                        NavigationBarItem(
                            selected = selectedDestination == index,
                            onClick = {
                                selectedDestination = index
                                if (index != 0) scope.launch { snackbarHostState.showSnackbar("${destination.first} demo") }
                            },
                            icon = { Icon(destination.second, contentDescription = destination.third) },
                            label = { Text(destination.first) },
                        )
                    }
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
            ModalBottomSheet(onDismissRequest = { showSheet = false }, sheetState = sheetState) {
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

@Composable
private fun CatalogContent(
    modifier: Modifier = Modifier,
    onDialog: () -> Unit,
    onSheet: () -> Unit,
    onSnackbar: (String) -> Unit,
) {
    val sections = coreCatalogSections(onDialog, onSheet, onSnackbar) + extendedCatalogSections(onSnackbar)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text("Component catalog", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "A functional reference surface for Material components, states, hierarchy and interaction patterns.",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        items(sections, key = { it.title }) { section ->
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
                Text(
                    section.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            section.content()
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun MaterialShowcasePreview() {
    MaterialShowcaseApp()
}
