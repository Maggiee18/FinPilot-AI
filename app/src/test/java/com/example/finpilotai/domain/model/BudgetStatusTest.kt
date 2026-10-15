package com.example.finpilotai.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class BudgetStatusTest {

    @Test fun `threshold boundaries`() {
        assertEquals(BudgetStatus.ON_TRACK, BudgetStatus.fromUtilization(0))
        assertEquals(BudgetStatus.ON_TRACK, BudgetStatus.fromUtilization(7_499))
        assertEquals(BudgetStatus.APPROACHING, BudgetStatus.fromUtilization(7_500))
        assertEquals(BudgetStatus.APPROACHING, BudgetStatus.fromUtilization(8_999))
        assertEquals(BudgetStatus.NEAR_LIMIT, BudgetStatus.fromUtilization(9_000))
        assertEquals(BudgetStatus.NEAR_LIMIT, BudgetStatus.fromUtilization(9_999))
        assertEquals(BudgetStatus.EXCEEDED, BudgetStatus.fromUtilization(10_000))
        assertEquals(BudgetStatus.EXCEEDED, BudgetStatus.fromUtilization(25_000))
    }
}
