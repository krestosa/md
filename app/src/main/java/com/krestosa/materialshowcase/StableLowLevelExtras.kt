@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDragHandle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal fun stableLowLevelExtras(): List<CatalogSection> = listOf(
    CatalogSection("Basic alert dialog", "Low-level dialog container for fully custom Material dialog content.") { BasicAlertDialogDemo() },
    CatalogSection("Snackbar surface", "Direct Snackbar composable in addition to SnackbarHost-driven messages.") { DirectSnackbarDemo() },
    CatalogSection("Outlined secure text field", "Outlined password-oriented state-based secure input.") { OutlinedSecureFieldDemo() },
    CatalogSection("Vertical drag handle", "Material drag handle for horizontally resizing adjacent panes.") { VerticalDragHandleDemo() },
)

@Composable
private fun BasicAlertDialogDemo() {
    var open by remember { mutableStateOf(false) }
    Button(onClick = { open = true }) { Text("Open custom dialog") }
    if (open) {
        BasicAlertDialog(onDismissRequest = { open = false }) {
            ElevatedCard {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Custom dialog", style = MaterialTheme.typography.headlineSmall)
                    Text("BasicAlertDialog provides the modal behavior while the content remains fully custom.")
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = { open = false }) { Text("Close") }
                    }
                }
            }
        }
    }
}

@Composable
private fun DirectSnackbarDemo() {
    Snackbar(
        action = { TextButton(onClick = {}) { Text("Undo") } },
        dismissAction = { TextButton(onClick = {}) { Text("Dismiss") } },
    ) { Text("Direct Material snackbar surface") }
}

@Composable
private fun OutlinedSecureFieldDemo() {
    val state = rememberTextFieldState()
    OutlinedSecureTextField(state = state, modifier = Modifier.fillMaxWidth(), label = { Text("Secure password") })
}

@Composable
private fun VerticalDragHandleDemo() {
    Row(modifier = Modifier.fillMaxWidth().height(120.dp), verticalAlignment = Alignment.CenterVertically) {
        Surface(modifier = Modifier.weight(1f).height(100.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
            Box(contentAlignment = Alignment.Center) { Text("Pane A") }
        }
        VerticalDragHandle(modifier = Modifier.padding(horizontal = 12.dp))
        Surface(modifier = Modifier.weight(1f).height(100.dp), color = MaterialTheme.colorScheme.surfaceContainer) {
            Box(contentAlignment = Alignment.Center) { Text("Pane B") }
        }
    }
}
