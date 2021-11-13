package de.vitamin.investmentportfolio.controllers

import de.vitamin.investmentportfolio.exceptions.InvalidRiskLevelException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PortfolioController {


    @GetMapping("/users/me/investment-portfolio")
    fun getPortfolioFor(@RequestParam("riskLevel") riskLevel: String) {

        throw InvalidRiskLevelException("Invalid risk level provided : $riskLevel")
    }
}