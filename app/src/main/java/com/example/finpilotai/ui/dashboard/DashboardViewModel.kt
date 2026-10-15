package com.example.finpilotai.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.finpilotai.domain.model.MonthlyOverview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Clock
import java.time.YearMonth

/** Immutable UI state for the dashboard (MVVM: one state object exposed as StateFlow). */
data class DashboardUiState(
    val selectedMonth: YearMonth,
    val currentMonth: YearMonth,
    val isLoading: Boolean = false,
    /** Null means "no records for this month", which renders the empty state. */
    val overview: MonthlyOverview? = null,
) {
    val canGoToNextMonth: Boolean get() = selectedMonth < currentMonth
}

/**
 * Milestone 1: owns month selection only. There is no data layer yet, so the overview
 * is always null and the screen shows its empty state instead of invented numbers.
 * Milestone 2 injects a repository and maps Room data into [MonthlyOverview].
 */
class DashboardViewModel(
    clock: Clock = Clock.systemDefaultZone(),
) : ViewModel() {

    private val currentMonth: YearMonth = YearMonth.now(clock)

    private val _uiState = MutableStateFlow(
        DashboardUiState(selectedMonth = currentMonth, currentMonth = currentMonth),
    )
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun onPreviousMonth() {
        _uiState.update { it.copy(selectedMonth = it.selectedMonth.minusMonths(1)) }
    }

    fun onNextMonth() {
        _uiState.update { state ->
            if (state.canGoToNextMonth) state.copy(selectedMonth = state.selectedMonth.plusMonths(1)) else state
        }
    }
}
