package de.vitamin.investmentportfolio.repositories

import de.vitamin.investmentportfolio.helpers.JsonFileReader
import de.vitamin.investmentportfolio.models.responses.ShareDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.util.ResourceUtils

@Repository
class JsonFileRepository(
    @Value("\${database.json.file-name}")
    var fileName: String
) : de.vitamin.investmentportfolio.repositories.Repository {
    private var mapOfRiskLevelToPortfolio = emptyMap<String, List<ShareDetail>>()

    init {
        mapOfRiskLevelToPortfolio = JsonFileReader.readFile(ResourceUtils.getFile("classpath:$fileName"))
    }

    override fun getPortfolioForRiskLevel(riskLevel: String): List<ShareDetail>? = mapOfRiskLevelToPortfolio[riskLevel]

}