package de.vitamin.investmentportfolio.services

import de.vitamin.investmentportfolio.exceptions.InvalidRiskLevelException
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.repositories.Repository
import org.springframework.stereotype.Service

@Service
class PortfolioService(private val repository: Repository) {
    fun getPortFolio(riskLevel: String): PortFolioResponse {

        val portfolios = repository.getPortfolioForRiskLevel(riskLevel)
            ?: throw InvalidRiskLevelException("Invalid risk level provided : $riskLevel")

        return PortFolioResponse(portfolios)
    }

}
