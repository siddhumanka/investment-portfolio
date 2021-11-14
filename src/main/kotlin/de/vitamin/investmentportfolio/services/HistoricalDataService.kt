package de.vitamin.investmentportfolio.services

import de.vitamin.investmentportfolio.clients.FinancialModelingClient
import de.vitamin.investmentportfolio.clients.responses.HistoricalData
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class HistoricalDataService(
    private val client: FinancialModelingClient,
    @Value("\${financial-modeling.api-key}")
    var apiKey: String
) {

    fun getAllCloseValuesFor(stockName: String, fromDate: String, toDate: String): List<Double> {
        val historicalDataList = client.getHistoricalData(stockName, fromDate, toDate, apiKey).historical
        return getOnlyMonthlyCloseValues(historicalDataList.reversed())
    }

    private fun getOnlyMonthlyCloseValues(historical: List<HistoricalData>) :  List<Double> {
        val monthlyCloseValues = mutableListOf<Double>()
        var i = 0
        monthlyCloseValues.add(historical[0].close)
        while (i < historical.size - 1) {
            if (getMonth(historical[i + 1].date) != getMonth(historical[i].date))
                monthlyCloseValues.add(historical[i + 1].close)
            i++
        }
        return monthlyCloseValues
    }

    private fun getMonth(date: String): String {
        return date.split("-")[1]
    }
}