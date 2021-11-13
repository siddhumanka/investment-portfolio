package de.vitamin.investmentportfolio.helpers


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.util.ResourceUtils

internal class JsonFileReaderTest{

    @Test
    internal fun `should return parts from json file`() {
        val mapOfRiskLevelToPortfolio = JsonFileReader.readFile(ResourceUtils.getFile("classpath:test-portfolios.json"))

        assertThat(mapOfRiskLevelToPortfolio["5"]!![0].weight).isEqualTo(0.65)
        assertThat(mapOfRiskLevelToPortfolio["5"]!![0].ticker).isEqualTo("CAKE")

        assertThat(mapOfRiskLevelToPortfolio["5"]!![1].weight).isEqualTo(0.20)
        assertThat(mapOfRiskLevelToPortfolio["5"]!![1].ticker).isEqualTo("PZZA")

        assertThat(mapOfRiskLevelToPortfolio["5"]!![2].weight).isEqualTo(0.15)
        assertThat(mapOfRiskLevelToPortfolio["5"]!![2].ticker).isEqualTo("EAT")
    }
}