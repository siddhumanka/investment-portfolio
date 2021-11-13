package de.vitamin.investmentportfolio.controllers

import de.vitamin.investmentportfolio.exceptions.InvalidRiskLevelException
import de.vitamin.investmentportfolio.models.requests.CurrentValueRequest
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.services.PortfolioService
import org.springframework.web.bind.annotation.*

@RestController
class PortfolioController(private val portfolioService: PortfolioService) {

    @GetMapping("/users/me/investment-portfolio")
    fun getPortfolio(@RequestParam(required = true, name = "riskLevel") riskLevel: String): PortFolioResponse {
        return portfolioService.getPortFolio(riskLevel)
    }

    @PostMapping("/users/me/investment-portfolio/current-value")
    fun getPortfolio(@RequestBody request: CurrentValueRequest): PortFolioResponse {
        throw InvalidRiskLevelException("Invalid request")
    }


}