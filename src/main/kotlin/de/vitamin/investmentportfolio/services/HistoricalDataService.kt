package de.vitamin.investmentportfolio.services

import de.vitamin.investmentportfolio.clients.FinancialModelingClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class HistoricalDataService(
    private val client: FinancialModelingClient,
    @Value("\${financial-modeling.api-key}")
    var apiKey: String
) {

    fun getAllCloseValuesFor(stockName: String, fromDate: String, toDate: String): List<Double> {
        return client.getHistoricalData(stockName, fromDate, toDate, apiKey).historical.map { it.close }.reversed()
    }

}