@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

internal fun completeMotionSections(): List<CatalogSection> = listOf(
    CatalogSection("Material motion scheme", "Active MaterialTheme motion scheme using its default spatial spec.") { ThemeMotionDemo() },
    CatalogSection("Standard vs expressive", "Direct comparison of Material's standard and expressive motion schemes.") { StandardExpressiveDemo() },
    CatalogSection("Fast spatial", "Fast spatial token for compact bounds and position changes.") { SpatialSpecDemo(MotionSpeed.Fast) },
    CatalogSection("Default spatial", "Default spatial token for common bounds and position changes.") { SpatialSpecDemo(MotionSpeed.Default) },
    CatalogSection("Slow spatial", "Slow spatial token for prominent bounds and position changes.") { SpatialSpecDemo(MotionSpeed.Slow) },
    CatalogSection("Fast effects", "Fast effects token for alpha and other non-spatial changes.") { EffectsSpecDemo(MotionSpeed.Fast) },
    CatalogSection("Default effects", "Default effects token for common non-spatial changes.") { EffectsSpecDemo(MotionSpeed.Default) },
    CatalogSection("Slow effects", "Slow effects token for prominent non-spatial changes.") { EffectsSpecDemo(MotionSpeed.Slow) },
    CatalogSection("Fade + scale", "Combined effects and spatial enter/exit transition.") { FadeScaleDemo() },
    CatalogSection("Shared axis X", "Related content moves along the horizontal axis with cross-fade.") { SharedAxisXDemo() },
    CatalogSection("Shared axis Y", "Related content moves along the vertical axis with cross-fade.") { SharedAxisYDemo() },
    CatalogSection("Fade through", "Outgoing content fades before incoming content becomes dominant.") { FadeThroughDemo() },
    CatalogSection("Container transform", "A compact surface expands while retaining spatial continuity.") { ContainerTransformDemo() },
    CatalogSection("AnimatedContent SizeTransform", "Staged width and height interpolation with keyframes.") { SizeTransformDemo() },
    CatalogSection("Crossfade", "Simple replacement of one layout with another through opacity.") { CrossfadeDemo() },
    CatalogSection("AnimatedVisibility", "Lifecycle-aware appearance and disappearance.") { VisibilityDemo() },
    CatalogSection("Nested enter/exit", "Parent and child use distinct enter and exit transitions.") { NestedEnterExitDemo() },
    CatalogSection("animateContentSize", "Automatic interpolation when a composable changes measured size.") { ContentSizeDemo() },
    CatalogSection("animate*AsState", "Independent state-driven size, alpha and color animations.") { AsStateDemo() },
    CatalogSection("updateTransition", "Multiple values coordinated from a single target state.") { TransitionDemo() },
    CatalogSection("Spring physics", "Physics-based spatial movement using damping and stiffness.") { SpringDemo() },
    CatalogSection("Tween + easing", "Duration-based movement with easing.") { TweenDemo() },
    CatalogSection("Keyframes", "Precisely timed intermediate values.") { KeyframesDemo() },
    CatalogSection("Repeatable", "Finite repeated animation with reverse repeat mode.") { RepeatableDemo() },
    CatalogSection("InfiniteTransition", "Continuous coordinated motion for ongoing state.") { InfiniteDemo() },
    CatalogSection("Snap", "Immediate target-state transition.") { SnapDemo() },
    CatalogSection("Animatable", "Imperative coroutine-driven motion object.") { AnimatableDemo() },
    CatalogSection("Pressed microinteraction", "Press-state scale feedback driven by interaction state.") { PressDemo() },
    CatalogSection("Expandable FAB", "Built-in Extended FAB expansion and collapse motion.") { FabMotionDemo() },
    CatalogSection("Switch state motion", "Built-in binary-control state motion.") { SwitchMotionDemo() },
    CatalogSection("Lazy list reordering", "Placement animation when list items reorder.") { ReorderDemo() },
)

private enum class MotionSpeed { Fast, Default, Slow }

@Composable
private fun ThemeMotionDemo() {
    var moved by remember { mutableStateOf(false) }
    val spec = MaterialTheme.motionScheme.defaultSpatialSpec<androidx.compose.ui.unit.Dp>()
    val x by animateDpAsState(if (moved) 210.dp else 0.dp, spec, label = "theme-motion")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { moved = !moved }) { Text("Animate") }
        MotionTrack(x)
    }
}

@Composable
private fun StandardExpressiveDemo() {
    var moved by remember { mutableStateOf(false) }
    val standard = MotionScheme.standard()
    val expressive = MotionScheme.expressive()
    val a by animateDpAsState(if (moved) 190.dp else 0.dp, standard.defaultSpatialSpec(), label = "standard")
    val b by animateDpAsState(if (moved) 190.dp else 0.dp, expressive.defaultSpatialSpec(), label = "expressive")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { moved = !moved }) { Text("Compare") }
        Text("Standard"); MotionTrack(a)
        Text("Expressive"); MotionTrack(b)
    }
}

@Composable
private fun SpatialSpecDemo(speed: MotionSpeed) {
    var moved by remember { mutableStateOf(false) }
    val scheme = MaterialTheme.motionScheme
    val spec = when (speed) {
        MotionSpeed.Fast -> scheme.fastSpatialSpec<androidx.compose.ui.unit.Dp>()
        MotionSpeed.Default -> scheme.defaultSpatialSpec()
        MotionSpeed.Slow -> scheme.slowSpatialSpec()
    }
    val x by animateDpAsState(if (moved) 210.dp else 0.dp, spec, label = "spatial-$speed")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { moved = !moved }) { Text("Replay ${speed.name.lowercase()}") }; MotionTrack(x) }
}

@Composable
private fun EffectsSpecDemo(speed: MotionSpeed) {
    var active by remember { mutableStateOf(false) }
    val scheme = MaterialTheme.motionScheme
    val spec = when (speed) {
        MotionSpeed.Fast -> scheme.fastEffectsSpec<Float>()
        MotionSpeed.Default -> scheme.defaultEffectsSpec()
        MotionSpeed.Slow -> scheme.slowEffectsSpec()
    }
    val value by animateFloatAsState(if (active) 1f else 0.2f, spec, label = "effects-$speed")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { active = !active }) { Text("Replay ${speed.name.lowercase()}") }
        Surface(modifier = Modifier.size(88.dp).alpha(value), shape = MaterialTheme.shapes.extraLarge, color = MaterialTheme.colorScheme.primaryContainer) {}
    }
}

@Composable
private fun FadeScaleDemo() {
    var visible by remember { mutableStateOf(true) }
    val scheme = MaterialTheme.motionScheme
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { visible = !visible }) { Text(if (visible) "Hide" else "Show") }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(scheme.defaultEffectsSpec()) + scaleIn(scheme.defaultSpatialSpec(), initialScale = 0.88f),
            exit = fadeOut(scheme.fastEffectsSpec()) + scaleOut(scheme.fastSpatialSpec(), targetScale = 0.88f),
        ) { ElevatedCard(modifier = Modifier.fillMaxWidth()) { Text("Contextual surface", modifier = Modifier.padding(28.dp)) } }
    }
}

@Composable
private fun SharedAxisXDemo() {
    var page by remember { mutableIntStateOf(0) }
    val scheme = MaterialTheme.motionScheme
    val spatial = scheme.defaultSpatialSpec<androidx.compose.ui.unit.IntOffset>()
    val effectsIn = scheme.defaultEffectsSpec<Float>()
    val effectsOut = scheme.fastEffectsSpec<Float>()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        FilledTonalButton(onClick = { page = (page + 1) % 3 }) { Text("Next"); Icon(Icons.Default.ArrowForward, null) }
        AnimatedContent(
            targetState = page,
            transitionSpec = {
                (slideInHorizontally(spatial) { it / 3 } + fadeIn(effectsIn))
                    .togetherWith(slideOutHorizontally(spatial) { -it / 3 } + fadeOut(effectsOut))
            }, label = "shared-x",
        ) { MotionStateCard("Page ${it + 1}") }
    }
}

@Composable
private fun SharedAxisYDemo() {
    var page by remember { mutableIntStateOf(0) }
    val scheme = MaterialTheme.motionScheme
    val spatial = scheme.defaultSpatialSpec<androidx.compose.ui.unit.IntOffset>()
    val effectsIn = scheme.defaultEffectsSpec<Float>()
    val effectsOut = scheme.fastEffectsSpec<Float>()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { page = (page + 1) % 3 }) { Text("Next vertical state") }
        AnimatedContent(
            targetState = page,
            transitionSpec = {
                (slideInVertically(spatial) { it / 3 } + fadeIn(effectsIn))
                    .togetherWith(slideOutVertically(spatial) { -it / 3 } + fadeOut(effectsOut))
            }, label = "shared-y",
        ) { MotionStateCard("Level ${it + 1}") }
    }
}

@Composable
private fun FadeThroughDemo() {
    var state by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { state = (state + 1) % 3 }) { Text("Change content") }
        AnimatedContent(targetState = state, transitionSpec = { fadeIn(tween(220, delayMillis = 90)).togetherWith(fadeOut(tween(90))) }, label = "fade-through") {
            MotionStateCard(listOf("Loading", "Loaded", "Updated")[it])
        }
    }
}

@Composable
private fun ContainerTransformDemo() {
    var expanded by remember { mutableStateOf(false) }
    val scheme = MaterialTheme.motionScheme
    val color by animateColorAsState(if (expanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh, scheme.defaultEffectsSpec(), label = "container-color")
    Surface(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth().animateContentSize(scheme.defaultSpatialSpec()), color = color, shape = MaterialTheme.shapes.extraLarge) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(if (expanded) "Expanded container" else "Compact container"); Text("Tap to transform")
            if (expanded) { Spacer(Modifier.height(90.dp)); Text("Additional connected content") }
        }
    }
}

@Composable
private fun SizeTransformDemo() {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { expanded = !expanded }) { Text("Transform size") }
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = fadeIn(tween(150, 150)),
                    initialContentExit = fadeOut(tween(150)),
                    sizeTransform = SizeTransform { initialSize, targetSize -> keyframes { durationMillis = 300; IntSize(targetSize.width, initialSize.height) at 150 } },
                )
            }, label = "size-transform",
        ) { target ->
            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.large) {
                Text(if (target) "Expanded content with a wider text block" else "Compact", modifier = Modifier.padding(if (target) 40.dp else 16.dp))
            }
        }
    }
}

@Composable
private fun CrossfadeDemo() {
    var page by remember { mutableStateOf(false) }
    val spec = MaterialTheme.motionScheme.defaultEffectsSpec<Float>()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { page = !page }) { Text("Crossfade") }
        Crossfade(targetState = page, animationSpec = spec, label = "crossfade") { MotionStateCard(if (it) "Layout B" else "Layout A") }
    }
}

@Composable
private fun VisibilityDemo() {
    var visible by remember { mutableStateOf(false) }
    val scheme = MaterialTheme.motionScheme
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { visible = !visible }) { Text(if (visible) "Collapse" else "Reveal") }
        AnimatedVisibility(visible = visible, enter = expandVertically(scheme.defaultSpatialSpec()) + fadeIn(scheme.defaultEffectsSpec()), exit = shrinkVertically(scheme.fastSpatialSpec()) + fadeOut(scheme.fastEffectsSpec())) {
            MotionStateCard("Lifecycle-aware content")
        }
    }
}

@Composable
private fun NestedEnterExitDemo() {
    var visible by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { visible = !visible }) { Text("Toggle nested motion") }
        AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
            AnimatedVisibility(visible = visible, enter = slideInHorizontally { -it / 2 }, exit = slideOutHorizontally { it / 2 }) { MotionStateCard("Nested slide inside parent fade") }
        }
    }
}

@Composable
private fun ContentSizeDemo() {
    var expanded by remember { mutableStateOf(false) }
    Surface(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth().animateContentSize(), color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = MaterialTheme.shapes.large) {
        Text(if (expanded) "Expanded text demonstrates automatic measured-size interpolation across multiple lines of content." else "Tap to expand", modifier = Modifier.padding(20.dp))
    }
}

@Composable
private fun AsStateDemo() {
    var active by remember { mutableStateOf(false) }
    val size by animateDpAsState(if (active) 110.dp else 64.dp, label = "size")
    val a by animateFloatAsState(if (active) 1f else 0.45f, label = "alpha")
    val c by animateColorAsState(if (active) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.secondaryContainer, label = "color")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { active = !active }) { Text("Animate values") }; Surface(modifier = Modifier.size(size).alpha(a), color = c, shape = MaterialTheme.shapes.extraLarge) {} }
}

@Composable
private fun TransitionDemo() {
    var expanded by remember { mutableStateOf(false) }
    val primary = MaterialTheme.colorScheme.primaryContainer
    val neutral = MaterialTheme.colorScheme.surfaceContainerHighest
    val transition = updateTransition(expanded, label = "coordinated")
    val size by transition.animateDp(label = "size") { if (it) 130.dp else 70.dp }
    val color by transition.animateColor(label = "color") { if (it) primary else neutral }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { expanded = !expanded }) { Text("Run transition") }; Surface(modifier = Modifier.size(size), color = color, shape = MaterialTheme.shapes.extraLarge) {} }
}

@Composable private fun SpringDemo() = OneDimensionalSpecDemo("Spring", spring(dampingRatio = 0.55f, stiffness = 260f))
@Composable private fun TweenDemo() = OneDimensionalSpecDemo("Tween", tween(650, easing = FastOutSlowInEasing))
@Composable private fun KeyframesDemo() = OneDimensionalSpecDemo("Keyframes", keyframes { durationMillis = 800; 150.dp at 220; 90.dp at 430; 210.dp at 800 })
@Composable private fun RepeatableDemo() = OneDimensionalSpecDemo("Run 3 repeats", repeatable(iterations = 3, animation = tween(250), repeatMode = RepeatMode.Reverse))
@Composable private fun SnapDemo() = OneDimensionalSpecDemo("Snap", snap())

@Composable
private fun OneDimensionalSpecDemo(label: String, spec: androidx.compose.animation.core.FiniteAnimationSpec<androidx.compose.ui.unit.Dp>) {
    var moved by remember { mutableStateOf(false) }
    val x by animateDpAsState(if (moved) 210.dp else 0.dp, spec, label = label)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { moved = !moved }) { Text(label) }; MotionTrack(x) }
}

@Composable
private fun InfiniteDemo() {
    val transition = rememberInfiniteTransition(label = "infinite")
    val phase by transition.animateFloat(0f, 1f, infiniteRepeatable(tween(1200, easing = LinearEasing), RepeatMode.Reverse), label = "phase")
    MotionTrack(190.dp * phase, 0.45f + 0.55f * phase)
}

@Composable
private fun AnimatableDemo() {
    val anim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { scope.launch { anim.animateTo(if (anim.targetValue < 0.5f) 1f else 0f, spring()) } }) { Text("Animate imperatively") }
        MotionTrack(210.dp * anim.value)
    }
}

@Composable
private fun PressDemo() {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val spec = MaterialTheme.motionScheme.fastSpatialSpec<Float>()
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, spec, label = "press")
    Button(onClick = {}, interactionSource = source, modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale }) { Text("Press and hold") }
}

@Composable
private fun FabMotionDemo() {
    var expanded by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { expanded = !expanded }) { Text("Toggle FAB label") }; ExtendedFloatingActionButton(text = { Text("Create") }, icon = { Icon(Icons.Default.Add, null) }, expanded = expanded, onClick = {}) }
}

@Composable
private fun SwitchMotionDemo() {
    var checked by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) { Switch(checked = checked, onCheckedChange = { checked = it }); Text(if (checked) "On" else "Off") }
}

@Composable
private fun ReorderDemo() {
    val data = remember { mutableStateListOf("Alpha", "Beta", "Gamma", "Delta") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { if (data.size > 1) data.add(0, data.removeAt(data.lastIndex)) }) { Text("Reorder") }
        LazyColumn(modifier = Modifier.fillMaxWidth().height(210.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(data, key = { it }) { item -> Surface(modifier = Modifier.fillMaxWidth().animateItem(), color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.medium) { Text(item, modifier = Modifier.padding(16.dp)) } }
        }
    }
}

@Composable
private fun MotionTrack(offset: androidx.compose.ui.unit.Dp, alpha: Float = 1f) {
    Box(modifier = Modifier.fillMaxWidth().height(62.dp)) { Surface(modifier = Modifier.offset(x = offset).size(52.dp).alpha(alpha), shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.primaryContainer) {} }
}

@Composable
private fun MotionStateCard(label: String) {
    Surface(modifier = Modifier.fillMaxWidth().height(120.dp), shape = MaterialTheme.shapes.extraLarge, color = MaterialTheme.colorScheme.surfaceContainerHigh) { Box(contentAlignment = Alignment.Center) { Text(label, style = MaterialTheme.typography.headlineSmall) } }
}
