package de.vitamin.investmentportfolio.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class JsonFileRepositoryTest{

    private val repository = JsonFileRepository("test-portfolios.json")

    @Test
    internal fun `should return portfolios from json file`() {

        val portfolio = repository.getPortfolioForRiskLevel("5")

        assertThat(portfolio!![0].weight).isEqualTo(0.65)
        assertThat(portfolio[0].ticker).isEqualTo("CAKE")

    }

    @Test
    internal fun `should return null if risk level is empty`() {

        val portfolio = repository.getPortfolioForRiskLevel("")

        assertThat(portfolio).isNull()
    }

    @Test
    internal fun `should return null if risk level is invalid`() {

        val portfolio = repository.getPortfolioForRiskLevel("34333")

        assertThat(portfolio).isNull()
    }
}