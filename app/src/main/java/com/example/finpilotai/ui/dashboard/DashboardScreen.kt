package com.example.finpilotai.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finpilotai.domain.model.BudgetStatus
import com.example.finpilotai.domain.model.MonthlyOverview
import com.example.finpilotai.domain.model.TransactionPreview
import com.example.finpilotai.domain.model.TransactionType
import com.example.finpilotai.ui.components.CategoryBadge
import com.example.finpilotai.ui.components.DonutChart
import com.example.finpilotai.ui.components.DonutSlice
import com.example.finpilotai.ui.components.EmptyState
import com.example.finpilotai.ui.components.MonthSelector
import com.example.finpilotai.ui.components.SectionCard
import com.example.finpilotai.ui.components.color
import com.example.finpilotai.ui.theme.FinPilotTheme
import com.example.finpilotai.util.Formatters
import com.example.finpilotai.util.MoneyFormatter

/** Stateful entry point: obtains the ViewModel and forwards state to [DashboardContent]. */
@Composable
fun DashboardScreen(
    onAddTransaction: () -> Unit,
    onOpenTransactions: () -> Unit,
    onOpenBudgets: () -> Unit,
    viewModel: DashboardViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardContent(
        state = state,
        onPreviousMonth = viewModel::onPreviousMonth,
        onNextMonth = viewModel::onNextMonth,
        onAddTransaction = onAddTransaction,
        onOpenTransactions = onOpenTransactions,
        onOpenBudgets = onOpenBudgets,
    )
}

/** Stateless dashboard UI. Previewable and testable without a ViewModel. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    state: DashboardUiState,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onAddTransaction: () -> Unit,
    onOpenTransactions: () -> Unit,
    onOpenBudgets: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("FinPilot AI", style = MaterialTheme.typography.titleLarge)
                        Text(
                            "Your money, at a glance",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTransaction,
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Add") },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding() + 96.dp, // keep last card clear of the FAB
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                MonthSelector(
                    month = state.selectedMonth,
                    canGoNext = state.canGoToNextMonth,
                    onPrevious = onPreviousMonth,
                    onNext = onNextMonth,
                )
            }
            val overview = state.overview?.takeIf { it.hasAnyRecords }
            if (state.isLoading) {
                item { LoadingCard() }
            } else if (overview == null) {
                item {
                    SectionCard(title = "Overview") {
                        EmptyState(
                            icon = Icons.Outlined.AccountBalanceWallet,
                            title = "No records for ${Formatters.month(state.selectedMonth)}",
                            message = "Add your income and expenses to see totals, budget progress and " +
                                "where your money goes this month.",
                            actionLabel = "Add transaction",
                            onAction = onAddTransaction,
                        )
                    }
                }
            } else {
                item { BalanceHeroCard(overview) }
                item { BudgetCard(overview, onOpenBudgets) }
                item { CategoryCard(overview) }
                item { ComparisonCard(overview) }
                item { InsightCard() }
                item { RecentTransactionsCard(overview.recentTransactions, onOpenTransactions) }
            }
        }
    }
}

@Composable
private fun LoadingCard() {
    Box(Modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun BalanceHeroCard(overview: MonthlyOverview) {
    val colors = MaterialTheme.colorScheme
    val gradient = Brush.linearGradient(listOf(colors.primary, colors.tertiary))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(gradient)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text("Net savings", style = MaterialTheme.typography.labelLarge, color = colors.onPrimary.copy(alpha = 0.85f))
        Text(
            text = MoneyFormatter.format(overview.savingsPaise),
            style = MaterialTheme.typography.displaySmall,
            color = colors.onPrimary,
        )
        Text(
            "Recorded income minus recorded expenses",
            style = MaterialTheme.typography.bodySmall,
            color = colors.onPrimary.copy(alpha = 0.75f),
        )
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            HeroStat("Income", overview.incomePaise, Icons.Filled.ArrowDownward, Modifier.weight(1f))
            HeroStat("Expenses", overview.expensePaise, Icons.Filled.ArrowUpward, Modifier.weight(1f))
        }
    }
}

@Composable
private fun HeroStat(
    label: String,
    amountPaise: Long,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    Surface(
        modifier = modifier,
        color = onPrimary.copy(alpha = 0.14f),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = onPrimary.copy(alpha = 0.2f), modifier = Modifier.size(32.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = onPrimary, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(label, style = MaterialTheme.typography.labelMedium, color = onPrimary.copy(alpha = 0.85f))
                Text(
                    MoneyFormatter.format(amountPaise, showPaise = false),
                    style = MaterialTheme.typography.titleMedium,
                    color = onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun BudgetCard(overview: MonthlyOverview, onOpenBudgets: () -> Unit) {
    SectionCard(
        title = "Monthly budget",
        action = { TextButton(onClick = onOpenBudgets) { Text("Manage") } },
    ) {
        val budget = overview.budgetPaise
        val utilization = overview.budgetUtilizationBps
        val status = overview.budgetStatus
        if (budget == null || utilization == null || status == null) {
            Text(
                "No budget set for this month. Set one to track how much you have left.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            OutlinedButton(onClick = onOpenBudgets) { Text("Set a budget") }
            return@SectionCard
        }

        val statusColor = budgetStatusColor(status)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "${MoneyFormatter.format(overview.expensePaise, showPaise = false)} of " +
                    MoneyFormatter.format(budget, showPaise = false),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(Formatters.percentFromBps(utilization), style = MaterialTheme.typography.titleMedium, color = statusColor)
        }
        val statusLabel = budgetStatusLabel(status)
        LinearProgressIndicator(
            progress = { (utilization.coerceAtMost(10_000) / 10_000f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .semantics { stateDescription = statusLabel },
            color = statusColor,
            trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            strokeCap = StrokeCap.Round,
        )
        val remaining = overview.remainingBudgetPaise ?: 0L
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(statusLabel, style = MaterialTheme.typography.labelLarge, color = statusColor)
            Text(
                if (remaining >= 0) "${MoneyFormatter.format(remaining, showPaise = false)} left"
                else "Over by ${MoneyFormatter.format(-remaining, showPaise = false)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun budgetStatusColor(status: BudgetStatus): Color = when (status) {
    BudgetStatus.ON_TRACK -> FinPilotTheme.colors.income
    BudgetStatus.APPROACHING, BudgetStatus.NEAR_LIMIT -> FinPilotTheme.colors.warning
    BudgetStatus.EXCEEDED -> FinPilotTheme.colors.expense
}

private fun budgetStatusLabel(status: BudgetStatus): String = when (status) {
    BudgetStatus.ON_TRACK -> "On track"
    BudgetStatus.APPROACHING -> "Approaching limit (75%+)"
    BudgetStatus.NEAR_LIMIT -> "Near limit (90%+)"
    BudgetStatus.EXCEEDED -> "Budget exceeded"
}

@Composable
private fun CategoryCard(overview: MonthlyOverview) {
    SectionCard(title = "Spending by category") {
        val items = overview.categorySpending.filter { it.amountPaise > 0 }.sortedByDescending { it.amountPaise }
        if (items.isEmpty()) {
            Text("No expenses recorded this month.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            return@SectionCard
        }
        val total = items.sumOf { it.amountPaise }
        Row(verticalAlignment = Alignment.CenterVertically) {
            DonutChart(
                slices = items.map { DonutSlice(it.amountPaise, it.category.color()) },
                contentDescription = "Spending by category chart. Details listed alongside.",
                trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            )
            Spacer(Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items.take(5).forEach { item ->
                    val shareBps = (item.amountPaise * 10_000 / total).toInt()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(10.dp).clip(CircleShape).background(item.category.color()))
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(item.category.displayName, style = MaterialTheme.typography.labelLarge)
                            Text(
                                "${MoneyFormatter.format(item.amountPaise, showPaise = false)} · " +
                                    Formatters.percentFromBps(shareBps),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComparisonCard(overview: MonthlyOverview) {
    val previousMonth = overview.month.minusMonths(1)
    SectionCard(title = "Compared with ${Formatters.month(previousMonth)}") {
        val change = overview.expenseChangeBps
        if (change == null) {
            Text(
                "No expenses recorded last month, so there is nothing to compare yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            return@SectionCard
        }
        val colors = FinPilotTheme.colors
        val (tint, verb) = when {
            change > 0 -> colors.expense to "more"
            change < 0 -> colors.income to "less"
            else -> MaterialTheme.colorScheme.onSurfaceVariant to "the same as"
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(Formatters.percentFromBps(change, showPlusSign = true), style = MaterialTheme.typography.headlineSmall, color = tint)
            Spacer(Modifier.width(12.dp))
            Text(
                if (change == 0) "Spending is the same as last month"
                else "You spent $verb than last month",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Text(
            "Last month: ${MoneyFormatter.format(overview.previousMonthExpensePaise ?: 0L, showPaise = false)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun InsightCard() {
    SectionCard(title = "AI summary") {
        Row(verticalAlignment = Alignment.Top) {
            Icon(Icons.Outlined.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
            Spacer(Modifier.width(12.dp))
            Text(
                "AI summaries are not set up yet. When they are, you will be asked for consent " +
                    "before any summary of your spending leaves this device.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun RecentTransactionsCard(transactions: List<TransactionPreview>, onSeeAll: () -> Unit) {
    SectionCard(
        title = "Recent transactions",
        action = { TextButton(onClick = onSeeAll) { Text("See all") } },
    ) {
        transactions.take(5).forEachIndexed { index, tx ->
            if (index > 0) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            TransactionRow(tx)
        }
    }
}

@Composable
private fun TransactionRow(tx: TransactionPreview) {
    val isIncome = tx.type == TransactionType.INCOME
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        CategoryBadge(tx.category)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(tx.description, style = MaterialTheme.typography.bodyLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                "${tx.category.displayName} · ${Formatters.dayMonth(tx.date)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = MoneyFormatter.format(if (isIncome) tx.amountPaise else -tx.amountPaise, showPlusSign = true),
            style = MaterialTheme.typography.titleSmall,
            color = if (isIncome) FinPilotTheme.colors.income else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(name = "Dashboard with data", showBackground = true, heightDp = 1600)
@Composable
private fun DashboardWithDataPreview() {
    FinPilotTheme {
        DashboardContent(DashboardPreviewData.stateWithData, {}, {}, {}, {}, {})
    }
}

@Preview(name = "Dashboard dark", showBackground = true, heightDp = 1600, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DashboardDarkPreview() {
    FinPilotTheme(darkTheme = true) {
        DashboardContent(DashboardPreviewData.stateWithData, {}, {}, {}, {}, {})
    }
}

@Preview(name = "Dashboard empty", showBackground = true)
@Composable
private fun DashboardEmptyPreview() {
    FinPilotTheme {
        DashboardContent(DashboardPreviewData.emptyState, {}, {}, {}, {}, {})
    }
}
