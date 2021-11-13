package de.vitamin.investmentportfolio.controllers

import de.vitamin.investmentportfolio.models.requests.InvestedPortfolioRequest
import de.vitamin.investmentportfolio.models.responses.InvestedPortfolioResponse
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.services.PortfolioService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
    private lateinit var controller: PortfolioController

    @Test
    internal fun `should return valid portfolio for a valid risk level`() {
        val validRiskLevel = "2"
        val expectedPortfolioResponse = PortFolioResponse(emptyList())
        `when`(portfolioService.getPortfolio(validRiskLevel)).thenReturn(expectedPortfolioResponse)

        val response = controller.getPortfolio(validRiskLevel)

        assertThat(response).isEqualTo(expectedPortfolioResponse)
    }

    @Test
    internal fun `should return current value portfolio for a valid request`() {
        val validRequest =
            InvestedPortfolioRequest(
                from = "01.01.2017",
                to = "03.06.2021",
                monthlyContribution = 450.00,
                riskLevel = "5"
            )
        val expectedInvestedPortfolioResponse =
            InvestedPortfolioResponse(currentPortfolio = emptyList(), totalInvestment = 234.43)
        `when`(portfolioService.getInvestedPortfolio(validRequest)).thenReturn(expectedInvestedPortfolioResponse)

        val response = controller.getInvestedPortfolio(validRequest)

        assertThat(response).isEqualTo(expectedInvestedPortfolioResponse)
    }

}