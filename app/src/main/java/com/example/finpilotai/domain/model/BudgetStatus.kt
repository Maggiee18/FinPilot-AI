package com.example.finpilotai.domain.model

/**
 * Budget alert levels based on actual (not predicted) spending.
 * Default thresholds are 75%, 90% and 100%; Milestone 3 makes them configurable.
 */
enum class BudgetStatus {
    ON_TRACK,
    APPROACHING,
    NEAR_LIMIT,
    EXCEEDED;

    companion object {
        const val APPROACHING_BPS = 7_500
        const val NEAR_LIMIT_BPS = 9_000
        const val EXCEEDED_BPS = 10_000

        fun fromUtilization(utilizationBps: Int): BudgetStatus = when {
            utilizationBps >= EXCEEDED_BPS -> EXCEEDED
            utilizationBps >= NEAR_LIMIT_BPS -> NEAR_LIMIT
            utilizationBps >= APPROACHING_BPS -> APPROACHING
            else -> ON_TRACK
        }
    }
}
