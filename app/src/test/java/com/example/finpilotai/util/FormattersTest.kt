package com.example.finpilotai.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.YearMonth
import java.util.Locale

class FormattersTest {

    @Test fun `percent from basis points`() {
        assertEquals("0.0%", Formatters.percentFromBps(0))
        assertEquals("12.3%", Formatters.percentFromBps(1_234))
        assertEquals("100.0%", Formatters.percentFromBps(10_000))
        assertEquals("-0.5%", Formatters.percentFromBps(-50))
        assertEquals("+7.5%", Formatters.percentFromBps(750, showPlusSign = true))
    }

    @Test fun `month label`() =
        assertEquals("October 2026", Formatters.month(YearMonth.of(2026, 10), Locale.ENGLISH))
}
