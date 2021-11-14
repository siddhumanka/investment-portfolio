package de.vitamin.investmentportfolio.services

import de.vitamin.investmentportfolio.exceptions.InvalidAttributeException
import de.vitamin.investmentportfolio.helpers.DateHelper
import de.vitamin.investmentportfolio.models.requests.InvestedPortfolioRequest
import de.vitamin.investmentportfolio.models.responses.InvestedPortfolioResponse
import de.vitamin.investmentportfolio.models.responses.InvestedShareDetail
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.models.responses.ShareDetail
import de.vitamin.investmentportfolio.repositories.Repository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.abs

@Service
class PortfolioService(
    private val repository: Repository,
    private val historicalDataService: HistoricalDataService
) {
    fun getPortfolio(riskLevel: String): PortFolioResponse {
        val portfolios = repository.getPortfolioForRiskLevel(riskLevel)
            ?: throw InvalidAttributeException("Invalid risk level provided : $riskLevel")

        return PortFolioResponse(portfolios)
    }

    fun getInvestedPortfolio(request: InvestedPortfolioRequest): InvestedPortfolioResponse {
        val fromDate = DateHelper.getFormattedDate(request.from)
            ?: throw InvalidAttributeException("from date should be valid in format dd.mm.yyyy")
        val toDate = DateHelper.getFormattedDate(request.to)
            ?: throw InvalidAttributeException("to date should be valid in format dd.mm.yyyy")

        val portfolio = getPortfolio(request.riskLevel).portfolio

        val desiredStockEntries = getDesiredStocks(portfolio, fromDate, toDate)

        val investedShareDetails = desiredStockEntries.map { metadata ->
            InvestedShareDetail(
                ticker = metadata.name,
                currentValue = calculateInvested(metadata.closeValues, request.monthlyContribution, metadata.weight)
            )
        }

        val totalAmountInvested = calculateTotalInvestedAmount(desiredStockEntries, request)

        return InvestedPortfolioResponse(investedShareDetails, totalAmountInvested)
    }

    private fun calculateTotalInvestedAmount(
        desiredStockEntries: List<DesiredStockEntry>,
        request: InvestedPortfolioRequest
    ) = desiredStockEntries.map { (it.weight * request.monthlyContribution) * it.closeValues.size }
        .reduceRight { i, j -> i + j }

    private fun getDesiredStocks(
        portfolio: List<ShareDetail>,
        fromDate: String,
        toDate: String
    ) = portfolio.map {
        DesiredStockEntry(
            it.ticker,
            it.weight,
            historicalDataService.getAllCloseValuesFor(it.ticker, fromDate, toDate)
        )
    }

    private fun calculateInvested(closeValues: List<Double>, monthlyContribution: Double, weight: Double): Double {
        val perShareMonthlyContribution = monthlyContribution * weight
        val totalSharesPurchased = closeValues.map { (perShareMonthlyContribution / it) * calculateChange(it, closeValues.last())  }
        return totalSharesPurchased.zip(closeValues).map { pair -> pair.first * pair.second }.reduce { i, j -> i + j }
    }

    private fun calculateChange(oldValue: Double, currentValue: Double): Double {
        return currentValue / oldValue
    }
}

private data class DesiredStockEntry(val name: String, val weight: Double, val closeValues: List<Double>)
