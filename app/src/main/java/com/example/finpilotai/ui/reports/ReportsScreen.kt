package com.example.finpilotai.ui.reports

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.runtime.Composable
import com.example.finpilotai.ui.components.EmptyState
import com.example.finpilotai.ui.components.FeatureScaffold

/** Monthly reports and charts. Implemented in Milestone 3. */
@Composable
fun ReportsScreen() {
    FeatureScaffold(title = "Reports") {
        EmptyState(
            icon = Icons.Outlined.PieChart,
            title = "No reports yet",
            message = "Category breakdowns, income versus expenses and monthly trends will appear " +
                "here once you have recorded transactions.",
        )
    }
}
