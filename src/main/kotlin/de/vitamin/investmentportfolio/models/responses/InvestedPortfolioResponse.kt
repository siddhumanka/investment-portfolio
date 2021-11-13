package de.vitamin.investmentportfolio.models.responses

data class InvestedPortfolioResponse(val currentPortfolio: List<InvestedShareDetail>, val totalInvestment: Double)