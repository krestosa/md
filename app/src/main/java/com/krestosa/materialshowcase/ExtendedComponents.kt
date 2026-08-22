package com.krestosa.materialshowcase

internal fun extendedCatalogSections(onSnackbar: (String) -> Unit): List<CatalogSection> =
    extendedNavigationSections(onSnackbar) +
        extendedDisplaySections() +
        stableCompletionSections(onSnackbar)
