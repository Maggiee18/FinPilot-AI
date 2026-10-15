package com.example.finpilotai.util

import java.math.BigDecimal
import kotlin.math.abs

/**
 * All money in FinPilot AI is stored as [Long] paise (1 rupee = 100 paise).
 * Floating point is never used for financial arithmetic.
 *
 * Formatting is implemented by hand (instead of NumberFormat) so the output is
 * identical on every device and JVM, which keeps unit tests deterministic.
 */
object MoneyFormatter {

    const val CURRENCY_CODE = "INR"
    private const val CURRENCY_SYMBOL = "₹" // Indian Rupee sign
    private const val PAISE_PER_RUPEE = 100L

    /** Digits with an optional dot and at most two decimal places. No signs or exponents. */
    private val AMOUNT_PATTERN = Regex("""^\d+(\.\d{1,2})?$""")

    /**
     * Formats paise using Indian digit grouping, for example 12345678 -> "₹1,23,456.78".
     *
     * @param showPaise when false, the paise part is dropped (truncated, not rounded),
     * which is useful for compact chart labels.
     * @param showPlusSign prefix positive values with "+" (used for income rows).
     */
    fun format(paise: Long, showPaise: Boolean = true, showPlusSign: Boolean = false): String {
        require(paise != Long.MIN_VALUE) { "Amount out of supported range" }
        val sign = when {
            paise < 0 -> "-"
            paise > 0 && showPlusSign -> "+"
            else -> ""
        }
        val absolute = abs(paise)
        val rupees = absolute / PAISE_PER_RUPEE
        val remainder = absolute % PAISE_PER_RUPEE
        val grouped = groupIndian(rupees.toString())
        val fraction = if (showPaise) "." + remainder.toString().padStart(2, '0') else ""
        return "$sign$CURRENCY_SYMBOL$grouped$fraction"
    }

    /**
     * Parses user input such as "1,234.50" into paise.
     * Returns null for blank, non numeric, negative, or more than two decimal places.
     * Zero is returned as 0; deciding whether zero is allowed is the validator's job.
     */
    fun parseToPaise(input: String): Long? {
        val cleaned = input.trim().replace(",", "")
        if (!AMOUNT_PATTERN.matches(cleaned)) return null
        return try {
            BigDecimal(cleaned).movePointRight(2).setScale(0).longValueExact()
        } catch (e: ArithmeticException) {
            null // larger than Long can hold
        }
    }

    /** Converts whole rupees to paise, e.g. for test fixtures and default budgets. */
    fun rupeesToPaise(rupees: Long): Long = Math.multiplyExact(rupees, PAISE_PER_RUPEE)

    /** Indian grouping: last three digits, then groups of two. "1234567" -> "12,34,567". */
    internal fun groupIndian(digits: String): String {
        if (digits.length <= 3) return digits
        val lastThree = digits.takeLast(3)
        val rest = digits.dropLast(3)
        val restGrouped = rest.reversed().chunked(2).joinToString(",").reversed()
        return "$restGrouped,$lastThree"
    }
}
