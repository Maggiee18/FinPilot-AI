package com.example.finpilotai.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finpilotai.ui.budgets.BudgetsScreen
import com.example.finpilotai.ui.dashboard.DashboardScreen
import com.example.finpilotai.ui.reports.ReportsScreen
import com.example.finpilotai.ui.settings.SettingsScreen
import com.example.finpilotai.ui.transactions.AddTransactionScreen
import com.example.finpilotai.ui.transactions.TransactionsScreen

/** Root composable: bottom navigation plus the navigation graph. */
@Composable
fun FinPilotApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val showBottomBar = TopLevelDestination.entries.any { currentDestination.isOn(it) }

    Scaffold(
        // Each screen draws its own top bar, so the outer scaffold must not add top insets.
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    TopLevelDestination.entries.forEach { destination ->
                        val selected = currentDestination.isOn(destination)
                        NavigationBarItem(
                            selected = selected,
                            onClick = { navController.navigateToTopLevel(destination) },
                            icon = {
                                Icon(
                                    if (selected) destination.selectedIcon else destination.unselectedIcon,
                                    contentDescription = null,
                                )
                            },
                            label = { Text(destination.label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        ) {
            composable<DashboardRoute> {
                DashboardScreen(
                    onAddTransaction = { navController.navigate(AddTransactionRoute) },
                    onOpenTransactions = { navController.navigateToTopLevel(TopLevelDestination.TRANSACTIONS) },
                    onOpenBudgets = { navController.navigateToTopLevel(TopLevelDestination.BUDGETS) },
                )
            }
            composable<TransactionsRoute> {
                TransactionsScreen(onAddTransaction = { navController.navigate(AddTransactionRoute) })
            }
            composable<BudgetsRoute> { BudgetsScreen() }
            composable<ReportsRoute> { ReportsScreen() }
            composable<SettingsRoute> { SettingsScreen() }
            composable<AddTransactionRoute> {
                AddTransactionScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}

private fun NavDestination?.isOn(destination: TopLevelDestination): Boolean =
    this?.hierarchy?.any { it.hasRoute(destination.route::class) } == true

/**
 * Standard bottom navigation behaviour: one copy of each tab on the back stack,
 * tab state saved and restored, and Back from any tab returns to the dashboard.
 */
private fun NavHostController.navigateToTopLevel(destination: TopLevelDestination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
