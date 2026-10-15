package com.example.finpilotai.ui.dashboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId

class DashboardViewModelTest {

    // 14 Oct 2026, 10:00 IST
    private val clock = Clock.fixed(Instant.parse("2026-10-14T04:30:00Z"), ZoneId.of("Asia/Kolkata"))

    @Test fun `starts on the current month with no fabricated data`() {
        val state = DashboardViewModel(clock).uiState.value
        assertEquals(YearMonth.of(2026, 10), state.selectedMonth)
        assertNull(state.overview)
        assertFalse(state.canGoToNextMonth)
    }

    @Test fun `previous month crosses the year boundary`() {
        val vm = DashboardViewModel(Clock.fixed(Instant.parse("2026-01-10T00:00:00Z"), ZoneId.of("UTC")))
        vm.onPreviousMonth()
        assertEquals(YearMonth.of(2025, 12), vm.uiState.value.selectedMonth)
        assertTrue(vm.uiState.value.canGoToNextMonth)
    }

    @Test fun `cannot move past the current month`() {
        val vm = DashboardViewModel(clock)
        vm.onNextMonth()
        assertEquals(YearMonth.of(2026, 10), vm.uiState.value.selectedMonth)
    }

    @Test fun `next after previous returns to current month`() {
        val vm = DashboardViewModel(clock)
        vm.onPreviousMonth()
        vm.onPreviousMonth()
        vm.onNextMonth()
        assertEquals(YearMonth.of(2026, 9), vm.uiState.value.selectedMonth)
    }
}
