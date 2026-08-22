@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.krestosa.materialshowcase

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.animateEnterExit
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateColor
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
import androidx.compose.animation.using
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

internal fun completeMotionSections(): List<CatalogSection> = listOf(
    CatalogSection("Material motion scheme", "The active theme motion scheme drives component animation defaults.") { MaterialMotionSchemeDemo() },
    CatalogSection("Standard vs expressive", "Compare the two built-in Material motion schemes on the same spatial change.") { StandardExpressiveDemo() },
    CatalogSection("Fast spatial", "Fast spatial token for compact bounds and position changes.") { SpatialSpecDemo(MotionSpeed.Fast) },
    CatalogSection("Default spatial", "Default spatial token for common layout transitions.") { SpatialSpecDemo(MotionSpeed.Default) },
    CatalogSection("Slow spatial", "Slow spatial token for prominent or larger layout transitions.") { SpatialSpecDemo(MotionSpeed.Slow) },
    CatalogSection("Fast effects", "Fast effects token for alpha and color changes that must not overshoot.") { EffectsSpecDemo(MotionSpeed.Fast) },
    CatalogSection("Default effects", "Default effects token for common non-spatial visual changes.") { EffectsSpecDemo(MotionSpeed.Default) },
    CatalogSection("Slow effects", "Slow effects token for prominent non-spatial visual changes.") { EffectsSpecDemo(MotionSpeed.Slow) },
    CatalogSection("Fade + scale", "Opacity and scale enter/exit transition for contextual surfaces.") { FadeScaleDemo2() },
    CatalogSection("Shared axis X", "Horizontal shared-axis transition between related destinations.") { SharedAxisXDemo() },
    CatalogSection("Shared axis Y", "Vertical shared-axis transition for hierarchy changes.") { SharedAxisYDemo() },
    CatalogSection("Fade through", "Outgoing content fades before incoming content becomes dominant.") { FadeThroughDemo() },
    CatalogSection("Container transform", "A compact surface expands while maintaining spatial continuity.") { ContainerTransformDemo2() },
    CatalogSection("AnimatedContent SizeTransform", "Explicit staged size transform using keyframes.") { SizeTransformDemo() },
    CatalogSection("Crossfade", "Simple layout replacement through opacity interpolation.") { CrossfadeDemo() },
    CatalogSection("AnimatedVisibility", "Enter/exit lifecycle animation that removes hidden content from composition.") { RevealCollapseDemo2() },
    CatalogSection("Child enter/exit", "Children define independent enter/exit motion inside a parent visibility transition.") { ChildEnterExitDemo() },
    CatalogSection("animateContentSize", "Automatic layout size interpolation when content changes.") { AnimateContentSizeDemo() },
    CatalogSection("animate*AsState", "Independent state-driven property animations for size, alpha and color.") { AnimateAsStateDemo() },
    CatalogSection("updateTransition", "Coordinated multi-property animation sharing one state transition.") { UpdateTransitionDemo() },
    CatalogSection("Spring physics", "Spring-driven spatial response with damping and stiffness.") { SpringDemo2() },
    CatalogSection("Tween + easing", "Duration-based interpolation using an easing curve.") { TweenDemo() },
    CatalogSection("Keyframes", "Precisely timed intermediate values within one animation.") { KeyframesDemo() },
    CatalogSection("Repeatable", "Finite repeated animation with reverse repeat mode.") { RepeatableDemo() },
    CatalogSection("InfiniteTransition", "Continuous coordinated animation for persistent ongoing state.") { InfiniteMotionDemo() },
    CatalogSection("Snap", "Immediate target-state update through a snap animation spec.") { SnapDemo() },
    CatalogSection("Animatable", "Coroutine-driven animation object for imperative and gesture-oriented motion.") { AnimatableDemo() },
    CatalogSection("Pressed microinteraction", "A component reacts to press state with restrained scale feedback.") { PressedInteractionDemo() },
    CatalogSection("Expandable FAB", "Extended FAB label visibility responds to a state change.") { ExpandableFabDemo() },
    CatalogSection("Switch state motion", "Built-in component motion on a binary control.") { SwitchMotionDemo() },
    CatalogSection("Lazy list reordering", "Placement animation when list items change order.") { ListReorderDemo() },
)

private enum class MotionSpeed { Fast, Default, Slow }

@Composable
private fun MaterialMotionSchemeDemo() {
    var moved by remember { mutableStateOf(false) }
    val x by animateDpAsState(
        if (moved) 210.dp else 0.dp,
        animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
        label = "theme-motion",
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("MaterialTheme.motionScheme · defaultSpatialSpec")
        Button(onClick = { moved = !moved }) { Text("Animate") }
        MotionTrack(x)
    }
}

@Composable
private fun StandardExpressiveDemo() {
    var moved by remember { mutableStateOf(false) }
    val standard = MotionScheme.standard()
    val expressive = MotionScheme.expressive()
    val standardX by animateDpAsState(if (moved) 190.dp else 0.dp, standard.defaultSpatialSpec(), label = "standard")
    val expressiveX by animateDpAsState(if (moved) 190.dp else 0.dp, expressive.defaultSpatialSpec(), label = "expressive")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { moved = !moved }) { Text("Compare") }
        Text("Standard")
        MotionTrack(standardX)
        Text("Expressive")
        MotionTrack(expressiveX)
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
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { moved = !moved }) { Text("Replay ${speed.name.lowercase()}") }
        MotionTrack(x)
    }
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
    val alpha by animateFloatAsState(if (active) 1f else 0.25f, spec, label = "effects-$speed")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { active = !active }) { Text("Replay ${speed.name.lowercase()}") }
        Surface(
            modifier = Modifier.size(88.dp).alpha(alpha),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {}
    }
}

@Composable
private fun FadeScaleDemo2() {
    var visible by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { visible = !visible }) { Text(if (visible) "Hide" else "Show") }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(MaterialTheme.motionScheme.defaultEffectsSpec()) + scaleIn(MaterialTheme.motionScheme.defaultSpatialSpec(), initialScale = 0.88f),
            exit = fadeOut(MaterialTheme.motionScheme.fastEffectsSpec()) + scaleOut(MaterialTheme.motionScheme.fastSpatialSpec(), targetScale = 0.88f),
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) { Text("Contextual surface", modifier = Modifier.padding(28.dp)) }
        }
    }
}

@Composable
private fun SharedAxisXDemo() {
    var page by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FilledTonalButton(onClick = { page = (page + 1) % 3 }) { Text("Next"); Icon(Icons.Default.ArrowForward, null) }
        AnimatedContent(
            targetState = page,
            transitionSpec = {
                (slideInHorizontally(MaterialTheme.motionScheme.defaultSpatialSpec()) { it / 3 } + fadeIn(MaterialTheme.motionScheme.defaultEffectsSpec()))
                    .togetherWith(slideOutHorizontally(MaterialTheme.motionScheme.defaultSpatialSpec()) { -it / 3 } + fadeOut(MaterialTheme.motionScheme.fastEffectsSpec()))
            },
            label = "shared-x",
        ) { MotionStateCard("Page ${it + 1}") }
    }
}

@Composable
private fun SharedAxisYDemo() {
    var page by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FilledTonalButton(onClick = { page = (page + 1) % 3 }) { Text("Next vertical state") }
        AnimatedContent(
            targetState = page,
            transitionSpec = {
                (slideInVertically(MaterialTheme.motionScheme.defaultSpatialSpec()) { it / 3 } + fadeIn(MaterialTheme.motionScheme.defaultEffectsSpec()))
                    .togetherWith(slideOutVertically(MaterialTheme.motionScheme.defaultSpatialSpec()) { -it / 3 } + fadeOut(MaterialTheme.motionScheme.fastEffectsSpec()))
            },
            label = "shared-y",
        ) { MotionStateCard("Level ${it + 1}") }
    }
}

@Composable
private fun FadeThroughDemo() {
    var state by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { state = (state + 1) % 3 }) { Text("Change content") }
        AnimatedContent(
            targetState = state,
            transitionSpec = { fadeIn(tween(220, delayMillis = 90)).togetherWith(fadeOut(tween(90))) },
            label = "fade-through",
        ) { MotionStateCard(listOf("Loading", "Loaded", "Updated")[it]) }
    }
}

@Composable
private fun ContainerTransformDemo2() {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        if (expanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
        MaterialTheme.motionScheme.defaultEffectsSpec(), label = "container-color",
    )
    Surface(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().animateContentSize(MaterialTheme.motionScheme.defaultSpatialSpec()),
        shape = MaterialTheme.shapes.extraLarge,
        color = color,
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(if (expanded) "Expanded container" else "Compact container")
            Text("Tap to transform")
            if (expanded) { Spacer(Modifier.height(100.dp)); Text("Additional connected content") }
        }
    }
}

@Composable
private fun SizeTransformDemo() {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { expanded = !expanded }) { Text("Transform size") }
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(tween(150, 150)).togetherWith(fadeOut(tween(150))).using(
                    SizeTransform { initialSize, targetSize ->
                        keyframes {
                            durationMillis = 300
                            IntSize(targetSize.width, initialSize.height) at 150
                        }
                    },
                )
            }, label = "size-transform",
        ) { target ->
            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.large) {
                Text(if (target) "Expanded content with a substantially wider text block" else "Compact", modifier = Modifier.padding(if (target) 40.dp else 16.dp))
            }
        }
    }
}

@Composable
private fun CrossfadeDemo() {
    var page by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { page = !page }) { Text("Crossfade") }
        Crossfade(targetState = page, animationSpec = MaterialTheme.motionScheme.defaultEffectsSpec(), label = "crossfade") {
            MotionStateCard(if (it) "Layout B" else "Layout A")
        }
    }
}

@Composable
private fun RevealCollapseDemo2() {
    var visible by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FilledTonalButton(onClick = { visible = !visible }) { Text(if (visible) "Collapse" else "Reveal") }
        AnimatedVisibility(
            visible = visible,
            enter = expandVertically(MaterialTheme.motionScheme.defaultSpatialSpec()) + fadeIn(MaterialTheme.motionScheme.defaultEffectsSpec()),
            exit = shrinkVertically(MaterialTheme.motionScheme.fastSpatialSpec()) + fadeOut(MaterialTheme.motionScheme.fastEffectsSpec()),
        ) {
            Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.large) {
                Text("Supporting information enters and exits with layout-aware motion.", modifier = Modifier.padding(20.dp))
            }
        }
    }
}

@Composable
private fun ChildEnterExitDemo() {
    var visible by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { visible = !visible }) { Text("Toggle parent + child") }
        AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
            Row(
                modifier = Modifier.fillMaxWidth().animateEnterExit(
                    enter = slideInHorizontally { -it / 2 }, exit = slideOutHorizontally { it / 2 },
                ).padding(18.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(Icons.Default.Check, null)
                Text("Child owns a separate enter/exit transition")
            }
        }
    }
}

@Composable
private fun AnimateContentSizeDemo() {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape = MaterialTheme.shapes.large,
    ) {
        Text(
            if (expanded) "This content is expanded. animateContentSize smoothly reports intermediate layout sizes while the text and container reflow." else "Tap to expand",
            modifier = Modifier.padding(20.dp),
        )
    }
}

@Composable
private fun AnimateAsStateDemo() {
    var active by remember { mutableStateOf(false) }
    val size by animateDpAsState(if (active) 110.dp else 64.dp, label = "size")
    val alpha by animateFloatAsState(if (active) 1f else 0.45f, label = "alpha")
    val color by animateColorAsState(if (active) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.secondaryContainer, label = "color")
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { active = !active }) { Text("Animate independent values") }
        Surface(modifier = Modifier.size(size).alpha(alpha), color = color, shape = MaterialTheme.shapes.extraLarge) {}
    }
}

@Composable
private fun UpdateTransitionDemo() {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(expanded, label = "coordinated")
    val size by transition.animateDp(label = "size") { if (it) 130.dp else 70.dp }
    val color by transition.animateColor(label = "color") { if (it) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHighest }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { expanded = !expanded }) { Text("Run coordinated transition") }
        Surface(modifier = Modifier.size(size), color = color, shape = MaterialTheme.shapes.extraLarge) {}
    }
}

@Composable
private fun SpringDemo2() {
    var moved by remember { mutableStateOf(false) }
    val x by animateDpAsState(if (moved) 210.dp else 0.dp, spring(dampingRatio = 0.55f, stiffness = 260f), label = "spring")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { moved = !moved }) { Text("Spring") }
        MotionTrack(x)
    }
}

@Composable
private fun TweenDemo() {
    var moved by remember { mutableStateOf(false) }
    val x by animateDpAsState(if (moved) 210.dp else 0.dp, tween(650, easing = FastOutSlowInEasing), label = "tween")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { moved = !moved }) { Text("Tween") }; MotionTrack(x) }
}

@Composable
private fun KeyframesDemo() {
    var moved by remember { mutableStateOf(false) }
    val x by animateDpAsState(
        if (moved) 210.dp else 0.dp,
        keyframes { durationMillis = 800; 150.dp at 220; 90.dp at 430; 210.dp at 800 },
        label = "keyframes",
    )
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { moved = !moved }) { Text("Keyframes") }; MotionTrack(x) }
}

@Composable
private fun RepeatableDemo() {
    var active by remember { mutableStateOf(false) }
    val x by animateDpAsState(
        if (active) 150.dp else 0.dp,
        repeatable(iterations = 3, animation = tween(250), repeatMode = RepeatMode.Reverse),
        label = "repeatable",
    )
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { active = !active }) { Text("Run 3 repeats") }; MotionTrack(x) }
}

@Composable
private fun InfiniteMotionDemo() {
    val transition = rememberInfiniteTransition(label = "infinite")
    val phase by transition.animateFloat(0f, 1f, infiniteRepeatable(tween(1200, easing = LinearEasing), RepeatMode.Reverse), label = "phase")
    MotionTrack(190.dp * phase, alpha = 0.45f + 0.55f * phase)
}

@Composable
private fun SnapDemo() {
    var moved by remember { mutableStateOf(false) }
    val x by animateDpAsState(if (moved) 210.dp else 0.dp, snap(), label = "snap")
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { Button(onClick = { moved = !moved }) { Text("Snap") }; MotionTrack(x) }
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
private fun PressedInteractionDemo() {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, MaterialTheme.motionScheme.fastSpatialSpec(), label = "press-scale")
    Button(
        onClick = {},
        interactionSource = source,
        modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale },
    ) { Text("Press and hold") }
}

@Composable
private fun ExpandableFabDemo() {
    var expanded by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { expanded = !expanded }) { Text("Toggle FAB label") }
        ExtendedFloatingActionButton(
            text = { Text("Create") },
            icon = { Icon(Icons.Default.Add, null) },
            expanded = expanded,
            onClick = {},
        )
    }
}

@Composable
private fun SwitchMotionDemo() {
    var checked by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = checked, onCheckedChange = { checked = it })
        Text(if (checked) "On" else "Off")
    }
}

@Composable
private fun ListReorderDemo() {
    val items = remember { mutableStateListOf("Alpha", "Beta", "Gamma", "Delta") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = { if (items.size > 1) items.add(0, items.removeAt(items.lastIndex)) }) { Text("Reorder") }
        LazyColumn(modifier = Modifier.fillMaxWidth().height(210.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(items, key = { it }) { item ->
                Surface(
                    modifier = Modifier.fillMaxWidth().animateItem(),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.medium,
                ) { Text(item, modifier = Modifier.padding(16.dp)) }
            }
        }
    }
}

@Composable
private fun MotionTrack(offset: androidx.compose.ui.unit.Dp, alpha: Float = 1f) {
    Box(modifier = Modifier.fillMaxWidth().height(62.dp)) {
        Surface(
            modifier = Modifier.offset(x = offset).size(52.dp).alpha(alpha),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {}
    }
}

@Composable
private fun MotionStateCard(label: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(120.dp),
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Box(contentAlignment = Alignment.Center) { Text(label, style = MaterialTheme.typography.headlineSmall) }
    }
}
