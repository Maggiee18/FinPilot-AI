package com.example.finpilotai.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MoneyFormatterTest {

    @Test fun `formats zero`() = assertEquals("₹0.00", MoneyFormatter.format(0))

    @Test fun `formats paise below one rupee`() = assertEquals("₹0.05", MoneyFormatter.format(5))

    @Test fun `uses indian digit grouping`() {
        assertEquals("₹999.00", MoneyFormatter.format(999_00))
        assertEquals("₹1,000.00", MoneyFormatter.format(1_000_00))
        assertEquals("₹1,00,000.00", MoneyFormatter.format(1_00_000_00))
        assertEquals("₹1,23,456.78", MoneyFormatter.format(12_345_678))
        assertEquals("₹1,00,00,000.00", MoneyFormatter.format(1_00_00_000_00))
    }

    @Test fun `formats negative amounts`() = assertEquals("-₹1,500.50", MoneyFormatter.format(-1_500_50))

    @Test fun `plus sign only for positive values`() {
        assertEquals("+₹250.00", MoneyFormatter.format(250_00, showPlusSign = true))
        assertEquals("₹0.00", MoneyFormatter.format(0, showPlusSign = true))
        assertEquals("-₹250.00", MoneyFormatter.format(-250_00, showPlusSign = true))
    }

    @Test fun `hiding paise truncates instead of rounding`() =
        assertEquals("₹1,234", MoneyFormatter.format(1_234_99, showPaise = false))

    @Test fun `parses plain, grouped and decimal input`() {
        assertEquals(12_345L, MoneyFormatter.parseToPaise("123.45"))
        assertEquals(123_450L, MoneyFormatter.parseToPaise("1,234.5"))
        assertEquals(50_000L, MoneyFormatter.parseToPaise(" 500 "))
        assertEquals(0L, MoneyFormatter.parseToPaise("0"))
    }

    @Test fun `rejects invalid input`() {
        assertNull(MoneyFormatter.parseToPaise(""))
        assertNull(MoneyFormatter.parseToPaise("   "))
        assertNull(MoneyFormatter.parseToPaise("abc"))
        assertNull(MoneyFormatter.parseToPaise("-10"))
        assertNull(MoneyFormatter.parseToPaise("12.345"))
        assertNull(MoneyFormatter.parseToPaise("1e5"))
        assertNull(MoneyFormatter.parseToPaise("12."))
        assertNull(MoneyFormatter.parseToPaise("99999999999999999999"))
    }

    @Test fun `converts rupees to paise`() = assertEquals(150_000L, MoneyFormatter.rupeesToPaise(1_500))
}
