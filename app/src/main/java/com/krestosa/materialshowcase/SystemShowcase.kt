@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
internal fun SystemShowcaseContent(modifier: Modifier = Modifier) {
    CatalogSectionList(
        modifier = modifier,
        title = "Material system",
        description = "Adaptive layouts, theme tokens, component states, accessibility, responsive behavior, icons and applied component motion.",
        sections = systemSections(),
    )
}

private fun systemSections(): List<CatalogSection> = listOf(
    CatalogSection("Adaptive window info", "Live Material adaptive window size class and posture information.") { AdaptiveInfoDemo() },
    CatalogSection("Adaptive navigation suite", "Navigation automatically represented as the Material navigation suite for the current window.") { AdaptiveNavigationDemo() },
    CatalogSection("Responsive layout", "Compact, medium and expanded composition behavior driven by available width.") { ResponsiveLayoutDemo() },
    CatalogSection("Color roles", "Visual inventory of core Material color roles from the active dynamic/system theme.") { ColorRolesDemo() },
    CatalogSection("Typography type scale", "Complete Material type hierarchy rendered with the active typography tokens.") { TypographyDemo() },
    CatalogSection("Shape scale", "Material shape tokens from extra-small through extra-large.") { ShapeDemo() },
    CatalogSection("Surface and elevation", "Surface container hierarchy and tonal elevation levels.") { ElevationDemo() },
    CatalogSection("Component state matrix", "Enabled, disabled, selected, unselected, checked and unchecked states side by side.") { StateMatrixDemo() },
    CatalogSection("Text field state matrix", "Normal, disabled, read-only style, supporting and error-oriented field examples.") { TextFieldStatesDemo() },
    CatalogSection("Accessibility lab", "Touch targets, semantic labeling, current font scale and large-text examples.") { AccessibilityDemo() },
    CatalogSection("RTL layout", "Live mirrored row showing Material behavior in right-to-left layout direction.") { RtlDemo() },
    CatalogSection("Icon browser", "Material icon sample browser covering common action, navigation and status families.") { IconBrowserDemo() },
    CatalogSection("Component motion: press", "Button press feedback driven by interaction state and Material motion.") { PressMotionDemo() },
    CatalogSection("Component motion: FAB", "FAB reveal/hide example for context-dependent primary actions.") { FabMotionDemo() },
    CatalogSection("Component motion: navigation", "Animated destination state within a real Material navigation bar.") { NavigationMotionDemo() },
    CatalogSection("Component motion: container", "Card expansion with layout-aware size animation.") { CardMotionDemo() },
)

@Composable
private fun AdaptiveInfoDemo() {
    val info = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Window size class", style = MaterialTheme.typography.titleMedium)
        Text(info.windowSizeClass.toString())
        Text("Window posture", style = MaterialTheme.typography.titleMedium)
        Text(info.windowPosture.toString())
    }
}

@Composable
private fun AdaptiveNavigationDemo() {
    var selected by remember { mutableIntStateOf(0) }
    val items = listOf("Home" to Icons.Default.Home, "Search" to Icons.Default.Search, "Settings" to Icons.Default.Settings)
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        NavigationSuiteScaffold(
            navigationItems = {
                items.forEachIndexed { index, item ->
                    NavigationSuiteItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = { Icon(item.second, contentDescription = item.first) },
                        label = { Text(item.first) },
                    )
                }
            },
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                Text("${items[selected].first} adaptive content", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
private fun ResponsiveLayoutDemo() {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val mode = when {
            maxWidth < 420.dp -> "Compact"
            maxWidth < 720.dp -> "Medium"
            else -> "Expanded"
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("$mode · ${maxWidth.value.toInt()} dp", style = MaterialTheme.typography.titleMedium)
            if (maxWidth < 520.dp) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { ResponsivePane("Primary"); ResponsivePane("Secondary") }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.weight(1f)) { ResponsivePane("Primary") }
                    Box(Modifier.weight(1f)) { ResponsivePane("Secondary") }
                }
            }
        }
    }
}

@Composable private fun ResponsivePane(label: String) {
    Surface(modifier = Modifier.fillMaxWidth().height(90.dp), color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.large) {
        Box(contentAlignment = Alignment.Center) { Text(label) }
    }
}

@Composable
private fun ColorRolesDemo() {
    val c = MaterialTheme.colorScheme
    val roles = listOf(
        "Primary" to c.primary, "Primary container" to c.primaryContainer,
        "Secondary" to c.secondary, "Secondary container" to c.secondaryContainer,
        "Tertiary" to c.tertiary, "Tertiary container" to c.tertiaryContainer,
        "Error" to c.error, "Error container" to c.errorContainer,
        "Surface" to c.surface, "Surface variant" to c.surfaceVariant,
        "Surface container" to c.surfaceContainer, "Surface container high" to c.surfaceContainerHigh,
        "Inverse surface" to c.inverseSurface, "Outline" to c.outline,
    )
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        roles.forEach { (name, color) ->
            Surface(color = color, shape = MaterialTheme.shapes.medium) {
                Text(name, modifier = Modifier.padding(12.dp), color = readableOn(color))
            }
        }
    }
}

private fun readableOn(color: Color): Color = if ((color.red + color.green + color.blue) / 3f > .55f) Color.Black else Color.White

@Composable
private fun TypographyDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        val t = MaterialTheme.typography
        listOf(
            "Display large" to t.displayLarge, "Display medium" to t.displayMedium, "Display small" to t.displaySmall,
            "Headline large" to t.headlineLarge, "Headline medium" to t.headlineMedium, "Headline small" to t.headlineSmall,
            "Title large" to t.titleLarge, "Title medium" to t.titleMedium, "Title small" to t.titleSmall,
            "Body large" to t.bodyLarge, "Body medium" to t.bodyMedium, "Body small" to t.bodySmall,
            "Label large" to t.labelLarge, "Label medium" to t.labelMedium, "Label small" to t.labelSmall,
        ).forEach { Text(it.first, style = it.second) }
    }
}

@Composable
private fun ShapeDemo() {
    val s = MaterialTheme.shapes
    val shapes = listOf("Extra small" to s.extraSmall, "Small" to s.small, "Medium" to s.medium, "Large" to s.large, "Extra large" to s.extraLarge)
    Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        shapes.forEach { (name, shape) ->
            Surface(modifier = Modifier.size(110.dp), shape = shape, color = MaterialTheme.colorScheme.primaryContainer) {
                Box(contentAlignment = Alignment.Center) { Text(name, modifier = Modifier.padding(8.dp)) }
            }
        }
    }
}

@Composable
private fun ElevationDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf(0.dp, 1.dp, 3.dp, 6.dp, 12.dp).forEach { elevation ->
            Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = elevation, shadowElevation = if (elevation > 0.dp) 1.dp else 0.dp) {
                Text("Tonal elevation $elevation", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
private fun StateMatrixDemo() {
    var selected by remember { mutableStateOf(true) }
    var checked by remember { mutableStateOf(true) }
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = {}) { Text("Enabled") }
        Button(onClick = {}, enabled = false) { Text("Disabled") }
        ElevatedButton(onClick = {}) { Text("Elevated") }
        OutlinedButton(onClick = {}) { Text("Outlined") }
        FilterChip(selected = selected, onClick = { selected = !selected }, label = { Text(if (selected) "Selected" else "Unselected") })
        Switch(checked = checked, onCheckedChange = { checked = it })
        Switch(checked = false, onCheckedChange = null, enabled = false)
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        Checkbox(checked = false, onCheckedChange = null, enabled = false)
    }
}

@Composable
private fun TextFieldStatesDemo() {
    var text by remember { mutableStateOf("Material") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Normal") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "Disabled", onValueChange = {}, label = { Text("Disabled") }, enabled = false, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "Read only", onValueChange = {}, label = { Text("Read only") }, readOnly = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Error") }, isError = true, supportingText = { Text("Error supporting text") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun AccessibilityDemo() {
    val density = LocalDensity.current
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Current font scale: ${"%.2f".format(density.fontScale)}×")
        Text("Large text preview", fontSize = 32.sp)
        Button(onClick = {}, modifier = Modifier.height(48.dp)) { Icon(Icons.Default.Accessibility, null); Text(" 48 dp minimum target") }
        ListItem(headlineContent = { Text("Semantic label") }, supportingContent = { Text("Icons include content descriptions where meaningful.") }, leadingContent = { Icon(Icons.Default.Info, contentDescription = "Information") })
    }
}

@Composable
private fun RtlDemo() {
    androidx.compose.runtime.CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.large) {
            Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Text("RTL mirrored content")
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
        }
    }
}

@Composable
private fun IconBrowserDemo() {
    val icons = listOf(
        "Home" to Icons.Default.Home, "Search" to Icons.Default.Search, "Settings" to Icons.Default.Settings,
        "Person" to Icons.Default.Person, "Favorite" to Icons.Default.Favorite, "Notifications" to Icons.Default.Notifications,
        "Add" to Icons.Default.Add, "Edit" to Icons.Default.Edit, "Delete" to Icons.Default.Delete,
        "Share" to Icons.Default.Share, "Refresh" to Icons.Default.Refresh, "Menu" to Icons.Default.Menu,
        "Check" to Icons.Default.Check, "Close" to Icons.Default.Close, "Info" to Icons.Default.Info,
        "Animation" to Icons.Default.Animation,
    )
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        icons.forEach { (name, icon) ->
            OutlinedCard {
                Column(modifier = Modifier.width(92.dp).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(icon, contentDescription = name)
                    Spacer(Modifier.height(6.dp))
                    Text(name, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
private fun PressMotionDemo() {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) .94f else 1f, MaterialTheme.motionScheme.fastSpatialSpec(), label = "press-scale")
    Button(onClick = {}, interactionSource = interaction, modifier = Modifier.scale(scale)) { Text("Press and hold") }
}

@Composable
private fun FabMotionDemo() {
    var visible by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FilledTonalButton(onClick = { visible = !visible }) { Text(if (visible) "Hide FAB" else "Show FAB") }
        AnimatedVisibility(visible = visible, enter = fadeIn(MaterialTheme.motionScheme.defaultEffectsSpec()), exit = fadeOut(MaterialTheme.motionScheme.fastEffectsSpec())) {
            FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") }
        }
    }
}

@Composable
private fun NavigationMotionDemo() {
    var selected by remember { mutableIntStateOf(0) }
    NavigationBar {
        listOf("Home" to Icons.Default.Home, "Search" to Icons.Default.Search, "Profile" to Icons.Default.Person).forEachIndexed { index, item ->
            NavigationBarItem(selected = selected == index, onClick = { selected = index }, icon = { Icon(item.second, item.first) }, label = { Text(item.first) })
        }
    }
}

@Composable
private fun CardMotionDemo() {
    var expanded by remember { mutableStateOf(false) }
    Card(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth().animateContentSize(MaterialTheme.motionScheme.defaultSpatialSpec())) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(if (expanded) "Expanded card" else "Compact card", style = MaterialTheme.typography.titleMedium)
            if (expanded) {
                Text("Layout changes are animated with the active Material motion scheme.")
                Spacer(Modifier.height(60.dp))
            }
        }
    }
}
