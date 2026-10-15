package com.example.finpilotai.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.YearMonth

class MonthlyOverviewTest {

    private fun overview(
        income: Long = 0,
        expense: Long = 0,
        budget: Long? = null,
        previous: Long? = null,
    ) = MonthlyOverview(
        month = YearMonth.of(2026, 10),
        incomePaise = income,
        expensePaise = expense,
        budgetPaise = budget,
        previousMonthExpensePaise = previous,
        categorySpending = emptyList(),
        recentTransactions = emptyList(),
    )

    @Test fun `savings is income minus expenses`() {
        assertEquals(16_549_50L, overview(income = 45_000_00, expense = 28_450_50).savingsPaise)
    }

    @Test fun `savings can be negative`() {
        assertEquals(-500_00L, overview(income = 1_000_00, expense = 1_500_00).savingsPaise)
    }

    @Test fun `empty month has no records`() {
        val empty = overview()
        assertFalse(empty.hasAnyRecords)
        assertEquals(0L, empty.savingsPaise)
        assertNull(empty.budgetUtilizationBps)
        assertNull(empty.expenseChangeBps)
    }

    @Test fun `remaining budget and utilization`() {
        val o = overview(expense = 24_000_00, budget = 32_000_00)
        assertEquals(8_000_00L, o.remainingBudgetPaise)
        assertEquals(7_500, o.budgetUtilizationBps)
        assertEquals(BudgetStatus.APPROACHING, o.budgetStatus)
    }

    @Test fun `exceeded budget gives negative remaining`() {
        val o = overview(expense = 11_000_00, budget = 10_000_00)
        assertEquals(-1_000_00L, o.remainingBudgetPaise)
        assertEquals(11_000, o.budgetUtilizationBps)
        assertEquals(BudgetStatus.EXCEEDED, o.budgetStatus)
    }

    @Test fun `no budget means no utilization`() {
        val o = overview(expense = 100_00)
        assertNull(o.remainingBudgetPaise)
        assertNull(o.budgetUtilizationBps)
        assertNull(o.budgetStatus)
    }

    @Test fun `zero budget does not divide by zero`() {
        val o = overview(expense = 100_00, budget = 0)
        assertNull(o.budgetUtilizationBps)
        assertEquals(-100_00L, o.remainingBudgetPaise)
    }

    @Test fun `month over month change`() {
        assertEquals(1_245, overview(expense = 28_450_00, previous = 25_300_00).expenseChangeBps)
        assertEquals(-5_000, overview(expense = 50_00, previous = 100_00).expenseChangeBps)
        assertEquals(0, overview(expense = 100_00, previous = 100_00).expenseChangeBps)
    }

    @Test fun `no comparison when previous month is empty`() {
        assertNull(overview(expense = 100_00, previous = 0).expenseChangeBps)
        assertNull(overview(expense = 100_00, previous = null).expenseChangeBps)
    }

    @Test fun `income only month still has records`() {
        assertTrue(overview(income = 1_00).hasAnyRecords)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `negative totals are rejected`() {
        overview(expense = -1)
    }
}
