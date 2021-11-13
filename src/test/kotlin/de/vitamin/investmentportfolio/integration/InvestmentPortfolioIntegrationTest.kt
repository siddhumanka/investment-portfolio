package de.vitamin.investmentportfolio.integration

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class InvestmentPortfolioIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Nested
    inner class NotFound {

        @Test
        internal fun `should return 404 for a non existing endpoint`() {

            val request = MockMvcRequestBuilders
                .get("/non-existing")

            val response = mockMvc.perform(request)

            response
                .andExpect(status().isNotFound)
        }
    }

    @Nested
    inner class InvestmentPortfolioTest {

        @Test
        internal fun `should return Bad request for invalid risk level`() {
            val request = MockMvcRequestBuilders
                .get("/users/me/investment-portfolio")
                .param("riskLevel", "2")

            val response = mockMvc.perform(request)

            response
                .andExpect(status().isBadRequest)
        }

        @Test
        internal fun `should return Bad request if risk level is not provided`() {
            val request = MockMvcRequestBuilders
                .get("/users/me/investment-portfolio")

            val response = mockMvc.perform(request)

            response
                .andExpect(status().isBadRequest)
        }

        @Test
        internal fun `should return valid portfolio for valid risk level`() {
            val request = MockMvcRequestBuilders
                .get("/users/me/investment-portfolio")
                .param("riskLevel", "5")

            val response = mockMvc.perform(request)

            response
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.portfolio[0].weight").value(0.65))
                .andExpect(jsonPath("$.portfolio[0].ticker").value("CAKE"))
                .andExpect(jsonPath("$.portfolio[1].weight").value(0.20))
                .andExpect(jsonPath("$.portfolio[1].ticker").value("PZZA"))
                .andExpect(jsonPath("$.portfolio[2].weight").value(0.15))
                .andExpect(jsonPath("$.portfolio[2].ticker").value("EAT"))
        }

    }

}