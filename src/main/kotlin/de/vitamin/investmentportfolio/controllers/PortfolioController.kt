package de.vitamin.investmentportfolio.controllers

import de.vitamin.investmentportfolio.models.requests.InvestedPortfolioRequest
import de.vitamin.investmentportfolio.models.responses.InvestedPortfolioResponse
import de.vitamin.investmentportfolio.models.responses.PortFolioResponse
import de.vitamin.investmentportfolio.services.PortfolioService
import org.springframework.web.bind.annotation.*

@RestController
class PortfolioController(private val portfolioService: PortfolioService) {

    @GetMapping("/users/me/investment-portfolio")
    fun getPortfolio(@RequestParam(required = true, name = "riskLevel") riskLevel: String): PortFolioResponse {
        return portfolioService.getPortfolio(riskLevel)
    }

    @PostMapping("/users/me/investment-portfolio/current-value")
    fun getInvestedPortfolio(@RequestBody request: InvestedPortfolioRequest): InvestedPortfolioResponse {
        return portfolioService.getInvestedPortfolio(request)
    }


}