package de.vitamin.investmentportfolio.services

import de.vitamin.investmentportfolio.exceptions.InvalidRiskLevelException
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
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
    private lateinit var repository : Repository

    @InjectMocks
    private lateinit var portfolioService: PortfolioService

    @Test
    internal fun `should throw InvalidRiskLevelException if port folio not found`() {
        val notFoundRiskLevel = "99"
        `when`(repository.getPortfolioForRiskLevel(notFoundRiskLevel)).thenReturn(null)

        assertThrows<InvalidRiskLevelException> { portfolioService.getPortFolio(notFoundRiskLevel) }
    }

    @Test
    internal fun `should return port folio if risk level is valid`() {
        val validRiskLevel = "2"
        val expectedPortFolio = PortFolioResponse(emptyList())
        `when`(repository.getPortfolioForRiskLevel(validRiskLevel)).thenReturn(emptyList())

        val portFolio = portfolioService.getPortFolio(validRiskLevel)

        assertThat(portFolio).isEqualTo(expectedPortFolio)
    }


}