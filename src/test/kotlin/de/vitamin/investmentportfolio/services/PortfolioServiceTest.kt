package de.vitamin.investmentportfolio.services

import de.vitamin.investmentportfolio.exceptions.InvalidAttributeException
import de.vitamin.investmentportfolio.models.requests.InvestedPortfolioRequest
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.models.responses.ShareDetail
import de.vitamin.investmentportfolio.repositories.Repository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
internal class PortfolioServiceTest {

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var historicalDataService: HistoricalDataService

    @InjectMocks
    private lateinit var portfolioService: PortfolioService

    @Test
    internal fun `should throw InvalidAttributeException if port folio not found`() {
        val notFoundRiskLevel = "99"
        `when`(repository.getPortfolioForRiskLevel(notFoundRiskLevel)).thenReturn(null)

        assertThrows<InvalidAttributeException> { portfolioService.getPortfolio(notFoundRiskLevel) }
    }

    @Test
    internal fun `should return port folio if risk level is valid`() {
        val validRiskLevel = "2"
        val expectedPortFolio = PortFolioResponse(emptyList())
        `when`(repository.getPortfolioForRiskLevel(validRiskLevel)).thenReturn(emptyList())

        val portFolio = portfolioService.getPortfolio(validRiskLevel)

        assertThat(portFolio).isEqualTo(expectedPortFolio)
    }

    @Test
    internal fun `should throw InvalidAttributeException if from date is invalid`() {
        val investedPortfolioRequest = InvestedPortfolioRequest(
            from = "dkjncd",
            to = "03.06.2021",
            monthlyContribution = 450.00,
            riskLevel = "5"
        )

        assertThrows<InvalidAttributeException> { portfolioService.getInvestedPortfolio(investedPortfolioRequest) }
    }

    @Test
    internal fun `should throw InvalidAttributeException if to date is invalid`() {
        val investedPortfolioRequest = InvestedPortfolioRequest(
            from = "01.01.2017",
            to = "sdfv",
            monthlyContribution = 450.00,
            riskLevel = "5"
        )

        assertThrows<InvalidAttributeException> { portfolioService.getInvestedPortfolio(investedPortfolioRequest) }
    }


    @Test
    internal fun `should return calculated invested portfolio for valid request`() {
        val riskLevel = "7"
        val investedPortfolioRequest = InvestedPortfolioRequest(
            from = "01.01.2017",
            to = "09.03.2017",
            monthlyContribution = 80.00,
            riskLevel = riskLevel
        )

        `when`(repository.getPortfolioForRiskLevel(riskLevel)).thenReturn(
            listOf(
                ShareDetail(0.30, "CAKE"),
                ShareDetail(0.30, "PZZA"),
                ShareDetail(0.40, "EAT")
            )
        )

        `when`(historicalDataService.getAllCloseValuesFor("CAKE", "2017-01-01", "2017-03-09"))
            .thenReturn(listOf(12.00, 24.00, 48.00))

        `when`(historicalDataService.getAllCloseValuesFor("PZZA", "2017-01-01", "2017-03-09"))
            .thenReturn(listOf(48.00, 24.00, 12.00))

        `when`(historicalDataService.getAllCloseValuesFor("EAT", "2017-01-01", "2017-03-09"))
            .thenReturn(listOf(12.00, 12.00))

        val investedPortfolio = portfolioService.getInvestedPortfolio(investedPortfolioRequest)

        assertThat(investedPortfolio.currentPortfolio[0].ticker).isEqualTo("CAKE")
        assertThat(investedPortfolio.currentPortfolio[0].currentValue).isEqualTo(168.00)

        assertThat(investedPortfolio.currentPortfolio[1].ticker).isEqualTo("PZZA")
        assertThat(investedPortfolio.currentPortfolio[1].currentValue).isEqualTo(42.00)

        assertThat(investedPortfolio.currentPortfolio[2].ticker).isEqualTo("EAT")
        assertThat(investedPortfolio.currentPortfolio[2].currentValue).isEqualTo(64.0)

        assertThat(investedPortfolio.totalInvestment).isEqualTo(208.00)
    }


}