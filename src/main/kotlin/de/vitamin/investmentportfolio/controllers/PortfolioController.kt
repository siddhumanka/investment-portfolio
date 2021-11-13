package de.vitamin.investmentportfolio.controllers

import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.services.PortfolioService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PortfolioController(private val portfolioService: PortfolioService) {

    @GetMapping("/users/me/investment-portfolio")
    fun getPortfolio(@RequestParam(required = true, name = "riskLevel") riskLevel: String): PortFolioResponse {
        return portfolioService.getPortFolio(riskLevel)
    }
}