package com.krestosa.materialshowcase

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun MotionCatalogContent(modifier: Modifier = Modifier) {
    CatalogSectionList(
        modifier = modifier,
        title = "Motion",
        description = "Interactive examples for hierarchy, state change, spatial continuity and responsive motion. Each sample can be replayed and inspected independently.",
        sections = motionSections(),
    )
}

private fun motionSections(): List<CatalogSection> = listOf(
    CatalogSection("Fade + scale", "Visibility transition combining opacity and scale for contextual surfaces.") { FadeScaleDemo() },
    CatalogSection("Shared axis", "Directional content change using horizontal travel and cross-fade.") { SharedAxisDemo() },
    CatalogSection("Container transform", "A surface grows between compact and expanded states while preserving continuity.") { ContainerTransformDemo() },
    CatalogSection("Spring response", "Position changes driven by a spring rather than a fixed-duration tween.") { SpringResponseDemo() },
    CatalogSection("Emphasized easing", "A longer state transition showing eased acceleration and deceleration.") { EmphasizedEasingDemo() },
    CatalogSection("Reveal / collapse", "Vertical reveal with simultaneous size and opacity changes.") { RevealCollapseDemo() },
    CatalogSection("Continuous motion", "A restrained looping motion example for ongoing activity or attention.") { ContinuousMotionDemo() },
)

@Composable
private fun FadeScaleDemo() {
    var visible by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { visible = !visible }) {
            Icon(Icons.Default.PlayArrow, contentDescription = null)
            Text(if (visible) " Hide" else " Show")
        }
        Box(modifier = Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(220)) + scaleIn(tween(220), initialScale = 0.92f),
                exit = fadeOut(tween(160)) + scaleOut(tween(160), targetScale = 0.92f),
            ) {
                ElevatedCard {
                    Text("Contextual surface", modifier = Modifier.padding(24.dp), style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
private fun SharedAxisDemo() {
    var page by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FilledTonalButton(onClick = { page = (page + 1) % 3 }) {
            Text("Next state")
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
        AnimatedContent(
            targetState = page,
            transitionSpec = {
                (slideInHorizontally(tween(300, easing = FastOutSlowInEasing)) { it / 3 } + fadeIn(tween(220)))
                    .togetherWith(slideOutHorizontally(tween(260, easing = FastOutSlowInEasing)) { -it / 3 } + fadeOut(tween(180)))
            },
            label = "shared-axis",
        ) { state ->
            Surface(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("State ${state + 1}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun ContainerTransformDemo() {
    var expanded by remember { mutableStateOf(false) }
    val containerColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "container-color",
    )
    Surface(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().animateContentSize(tween(400, easing = FastOutSlowInEasing)),
        shape = MaterialTheme.shapes.extraLarge,
        color = containerColor,
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(if (expanded) "Expanded container" else "Compact container", style = MaterialTheme.typography.titleLarge)
            Text("Tap the surface to transform it.")
            if (expanded) {
                Spacer(Modifier.height(72.dp))
                Text("Additional content remains spatially connected to the originating surface.")
            }
        }
    }
}

@Composable
private fun SpringResponseDemo() {
    var moved by remember { mutableStateOf(false) }
    val offset by animateDpAsState(
        targetValue = if (moved) 180.dp else 0.dp,
        animationSpec = spring(dampingRatio = 0.72f, stiffness = 340f),
        label = "spring-offset",
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedButton(onClick = { moved = !moved }) { Text("Move with spring") }
        Box(modifier = Modifier.fillMaxWidth().height(72.dp)) {
            Box(
                modifier = Modifier.offset(x = offset).size(56.dp).background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.shapes.large,
                ),
            )
        }
    }
}

@Composable
private fun EmphasizedEasingDemo() {
    var active by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "emphasized-progress",
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(onClick = { active = !active }) { Text("Replay transition") }
        Box(modifier = Modifier.fillMaxWidth().height(84.dp)) {
            Surface(
                modifier = Modifier
                    .size(width = 72.dp + 96.dp * progress, height = 56.dp)
                    .graphicsLayer {
                        translationX = 120f * progress
                        rotationZ = 4f * progress
                    },
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.secondaryContainer,
            ) {}
        }
        Text("Progress ${(progress * 100).toInt()}%", style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun RevealCollapseDemo() {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FilledTonalButton(onClick = { expanded = !expanded }) { Text(if (expanded) "Collapse" else "Reveal") }
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(tween(300, easing = FastOutSlowInEasing)) + fadeIn(tween(220)),
            exit = shrinkVertically(tween(240, easing = FastOutSlowInEasing)) + fadeOut(tween(160)),
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                Text(
                    "Supporting information appears without abruptly reflowing the surrounding hierarchy.",
                    modifier = Modifier.padding(18.dp),
                )
            }
        }
    }
}

@Composable
private fun ContinuousMotionDemo() {
    val transition = rememberInfiniteTransition(label = "continuous-motion")
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(1400, easing = LinearEasing), repeatMode = RepeatMode.Reverse),
        label = "continuous-phase",
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(modifier = Modifier.fillMaxWidth().height(80.dp), contentAlignment = Alignment.CenterStart) {
            Surface(
                modifier = Modifier
                    .padding(start = 12.dp + 180.dp * phase)
                    .size(52.dp)
                    .alpha(0.55f + 0.45f * phase)
                    .graphicsLayer { rotationZ = 10f * phase },
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {}
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Text("Continuous motion should communicate ongoing state, not decorate static content.")
        }
    }
}
