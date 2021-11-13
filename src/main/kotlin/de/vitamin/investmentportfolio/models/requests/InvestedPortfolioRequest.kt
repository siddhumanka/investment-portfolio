package de.vitamin.investmentportfolio.models.requests

data class InvestedPortfolioRequest(
    val from: String,
    val to: String,
    val monthlyContribution: Double,
    val riskLevel: String
)

