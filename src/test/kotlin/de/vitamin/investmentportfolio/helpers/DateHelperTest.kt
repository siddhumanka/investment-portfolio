package de.vitamin.investmentportfolio.helpers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DateHelperTest{

    @Test
    internal fun `should return null if provided date is empty`() {

        assertThat(DateHelper.getFormattedDate("")).isNull()
    }

    @Test
    internal fun `should return null if provided date is invalid string`() {

        assertThat(DateHelper.getFormattedDate("adbajhd")).isNull()
    }

    @Test
    internal fun `should change and return date formatted as YYYY-MM-DD provided a valid date`() {

        assertThat(DateHelper.getFormattedDate("01.01.2020")).isEqualTo("2020-01-01")
    }
}