package com.example.finpilotai.util

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

/** Date and percentage helpers shared by the UI. */
object Formatters {

    fun month(month: YearMonth, locale: Locale = Locale.getDefault()): String =
        month.format(DateTimeFormatter.ofPattern("MMMM yyyy", locale))

    fun shortMonth(month: YearMonth, locale: Locale = Locale.getDefault()): String =
        month.format(DateTimeFormatter.ofPattern("MMM", locale))

    fun dayMonth(date: LocalDate, locale: Locale = Locale.getDefault()): String =
        date.format(DateTimeFormatter.ofPattern("d MMM", locale))

    /**
     * Basis points to a one decimal percentage string. 1234 -> "12.3%", -50 -> "-0.5%".
     * The extra digit is truncated so the value never overstates a change.
     */
    fun percentFromBps(bps: Int, showPlusSign: Boolean = false): String {
        val sign = when {
            bps < 0 -> "-"
            bps > 0 && showPlusSign -> "+"
            else -> ""
        }
        val absolute = abs(bps.toLong())
        val whole = absolute / 100
        val tenth = (absolute % 100) / 10
        return "$sign$whole.$tenth%"
    }
}
