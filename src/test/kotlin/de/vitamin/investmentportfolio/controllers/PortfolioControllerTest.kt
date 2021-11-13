package de.vitamin.investmentportfolio.controllers

import de.vitamin.investmentportfolio.exceptions.InvalidRiskLevelException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


internal class PortfolioControllerTest {

    private val controller = PortfolioController()

    @Test
    internal fun `should return InvalidRiskLevelProvidedException for invalid risk level`() {
        val invalidRiskLevel = "99"

        assertThrows<InvalidRiskLevelException> { controller.getPortfolioFor(invalidRiskLevel) }
    }
}