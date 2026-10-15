package com.example.finpilotai.ui.dashboard

import com.example.finpilotai.domain.model.Category
import com.example.finpilotai.domain.model.CategorySpending
import com.example.finpilotai.domain.model.MonthlyOverview
import com.example.finpilotai.domain.model.TransactionPreview
import com.example.finpilotai.domain.model.TransactionType
import java.time.LocalDate
import java.time.YearMonth

/**
 * Sample values for Android Studio @Preview ONLY. This object is never referenced
 * from the running app; the real dashboard only renders data from local storage.
 */
internal object DashboardPreviewData {
    private val month = YearMonth.of(2026, 10)

    val overview = MonthlyOverview(
        month = month,
        incomePaise = 45_000_00,
        expensePaise = 28_450_50,
        budgetPaise = 32_000_00,
        previousMonthExpensePaise = 25_300_00,
        categorySpending = listOf(
            CategorySpending(Category.HOUSING, 12_000_00),
            CategorySpending(Category.FOOD, 7_250_50),
            CategorySpending(Category.TRANSPORT, 3_100_00),
            CategorySpending(Category.EDUCATION, 2_800_00),
            CategorySpending(Category.ENTERTAINMENT, 1_900_00),
            CategorySpending(Category.OTHER, 1_400_00),
        ),
        recentTransactions = listOf(
            TransactionPreview(1, "Groceries", Category.FOOD, TransactionType.EXPENSE, 1_240_00, month.atDay(12)),
            TransactionPreview(2, "Monthly stipend", Category.SALARY, TransactionType.INCOME, 45_000_00, month.atDay(1)),
            TransactionPreview(3, "Metro card recharge", Category.TRANSPORT, TransactionType.EXPENSE, 500_00, LocalDate.of(2026, 10, 10)),
        ),
    )

    val stateWithData = DashboardUiState(
        selectedMonth = month,
        currentMonth = month,
        overview = overview,
    )

    val emptyState = DashboardUiState(selectedMonth = month, currentMonth = month)
}
