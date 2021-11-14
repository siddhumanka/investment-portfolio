package de.vitamin.investmentportfolio.clients.responses

data class HistoricalDataResponse(val historical: List<HistoricalData>)

data class HistoricalData(val close: Double, val date: String)
