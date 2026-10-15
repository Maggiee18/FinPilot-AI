package com.example.finpilotai.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SpaceDashboard
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

/*
 * Type safe Navigation Compose routes (navigation 2.8+). Routes are @Serializable
 * objects or classes instead of raw strings, so arguments are checked at compile time.
 */

@Serializable data object DashboardRoute
@Serializable data object TransactionsRoute
@Serializable data object BudgetsRoute
@Serializable data object ReportsRoute
@Serializable data object SettingsRoute
@Serializable data object AddTransactionRoute

/** Destinations shown in the bottom navigation bar. */
enum class TopLevelDestination(
    val route: Any,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    DASHBOARD(DashboardRoute, "Home", Icons.Filled.SpaceDashboard, Icons.Outlined.SpaceDashboard),
    TRANSACTIONS(TransactionsRoute, "Transactions", Icons.AutoMirrored.Filled.ReceiptLong, Icons.AutoMirrored.Outlined.ReceiptLong),
    BUDGETS(BudgetsRoute, "Budgets", Icons.Filled.AccountBalanceWallet, Icons.Outlined.AccountBalanceWallet),
    REPORTS(ReportsRoute, "Reports", Icons.Filled.PieChart, Icons.Outlined.PieChart),
    SETTINGS(SettingsRoute, "Settings", Icons.Filled.Settings, Icons.Outlined.Settings),
}
