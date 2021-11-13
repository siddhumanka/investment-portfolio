package de.vitamin.investmentportfolio.services

import de.vitamin.investmentportfolio.clients.FinancialModelingClient
import de.vitamin.investmentportfolio.clients.responses.HistoricalData
import de.vitamin.investmentportfolio.clients.responses.HistoricalDataResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class HistoricalDataServiceTest {

    @Mock
    private lateinit var client: FinancialModelingClient

    private lateinit var service: HistoricalDataService

    private val apiKey = "key"

    @BeforeEach
    internal fun setUp() {
        service = HistoricalDataService(client, apiKey)
    }

    @Test
    internal fun `should return historical data provided a api key from and to dates and stock name`() {
        val stockName = "CAKE"
        val fromDate = "2017-01-01"
        val toDate = "2021-01-01"
        val expectedCloseValue = 20.00
        Mockito.`when`(client.getHistoricalData(stockName, fromDate, toDate, apiKey)).thenReturn(
            HistoricalDataResponse(
                listOf(HistoricalData(expectedCloseValue))
            )
        )

        val closeAmounts = service.getAllCloseValuesFor(stockName, fromDate, toDate)

        assertThat(closeAmounts[0]).isEqualTo(expectedCloseValue)
    }
}