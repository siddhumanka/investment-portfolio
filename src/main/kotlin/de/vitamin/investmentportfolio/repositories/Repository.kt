package de.vitamin.investmentportfolio.repositories

import de.vitamin.investmentportfolio.models.responses.ShareDetail

interface Repository {
    fun getPortfolioForRiskLevel(riskLevel : String) :  List<ShareDetail>?
}
