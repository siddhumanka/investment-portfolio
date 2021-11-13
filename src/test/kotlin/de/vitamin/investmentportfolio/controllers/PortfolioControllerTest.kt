package de.vitamin.investmentportfolio.controllers

import de.vitamin.investmentportfolio.exceptions.InvalidRiskLevelException
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.services.PortfolioService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
internal class PortfolioControllerTest {

    @Mock
    private lateinit var portfolioService: PortfolioService

    @InjectMocks
    private lateinit var controller : PortfolioController

    @Test
    internal fun `should return valid portfolio for a valid risk level`() {
        val validRiskLevel = "2"
        val expectedPortfolioResponse = PortFolioResponse(emptyList())
        `when`(portfolioService.getPortFolio(validRiskLevel)).thenReturn(expectedPortfolioResponse)

        val response = controller.getPortfolio(validRiskLevel)

        assertThat(response).isEqualTo(expectedPortfolioResponse)
    }
}