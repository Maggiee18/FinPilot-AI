package com.example.finpilotai.domain.model

import java.time.LocalDate
import java.time.YearMonth

/**
 * Everything the dashboard needs for one month. All derived values are computed here,
 * in plain Kotlin, so they can be unit tested without Android or Compose.
 *
 * Money is in paise. Ratios are in basis points (1% = 100 bps) to stay in integer math.
 */
data class MonthlyOverview(
    val month: YearMonth,
    val incomePaise: Long,
    val expensePaise: Long,
    /** Overall monthly budget, or null when the user has not set one. */
    val budgetPaise: Long?,
    /** Total expenses of the previous month, or null when there were no records. */
    val previousMonthExpensePaise: Long?,
    val categorySpending: List<CategorySpending>,
    val recentTransactions: List<TransactionPreview>,
) {
    init {
        require(incomePaise >= 0) { "Income total cannot be negative" }
        require(expensePaise >= 0) { "Expense total cannot be negative" }
        require(budgetPaise == null || budgetPaise >= 0) { "Budget cannot be negative" }
    }

    /** Net savings: recorded income minus recorded expenses. Can be negative. */
    val savingsPaise: Long get() = incomePaise - expensePaise

    /** Budget left; negative means the budget is exceeded. Null without a budget. */
    val remainingBudgetPaise: Long? get() = budgetPaise?.let { it - expensePaise }

    /** Share of budget used in basis points, null if no budget or a zero budget. */
    val budgetUtilizationBps: Int?
        get() = budgetPaise
            ?.takeIf { it > 0 }
            ?.let { (expensePaise * BPS_SCALE / it).coerceAtMost(Int.MAX_VALUE.toLong()).toInt() }

    val budgetStatus: BudgetStatus?
        get() = budgetUtilizationBps?.let { BudgetStatus.fromUtilization(it) }

    /** Change of expenses against last month in basis points, null when not comparable. */
    val expenseChangeBps: Int?
        get() = previousMonthExpensePaise
            ?.takeIf { it > 0 }
            ?.let { ((expensePaise - it) * BPS_SCALE / it).toInt() }

    val hasAnyRecords: Boolean get() = incomePaise > 0 || expensePaise > 0

    private companion object {
        const val BPS_SCALE = 10_000L
    }
}

data class CategorySpending(val category: Category, val amountPaise: Long) {
    init {
        require(amountPaise >= 0) { "Category spending cannot be negative" }
    }
}

data class TransactionPreview(
    val id: Long,
    val description: String,
    val category: Category,
    val type: TransactionType,
    val amountPaise: Long,
    val date: LocalDate,
)
