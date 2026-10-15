package com.example.finpilotai.ui.budgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.runtime.Composable
import com.example.finpilotai.ui.components.EmptyState
import com.example.finpilotai.ui.components.FeatureScaffold

/** Budgets overview. Monthly and category budgets with alerts arrive in Milestone 3. */
@Composable
fun BudgetsScreen() {
    FeatureScaffold(title = "Budgets") {
        EmptyState(
            icon = Icons.Outlined.AccountBalanceWallet,
            title = "No budgets yet",
            message = "Set a monthly budget and per category limits, with warnings at 75%, 90% and 100%.",
        )
    }
}
