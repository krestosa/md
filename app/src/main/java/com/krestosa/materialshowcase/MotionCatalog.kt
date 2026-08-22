package com.krestosa.materialshowcase

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun MotionCatalogContent(modifier: Modifier = Modifier) {
    CatalogSectionList(
        modifier = modifier,
        title = "Material Motion",
        description = "Complete interactive motion catalog covering Material motion schemes, spatial and effects specs, transition patterns, Compose animation APIs, physics, repeated motion and component microinteractions.",
        sections = completeMotionSections(),
    )
}
