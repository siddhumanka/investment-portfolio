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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

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
                .andExpect(MockMvcResultMatchers.status().isNotFound)
        }
    }

    @Nested
    inner class InvestmentPortfolioTest {

        @Test
        internal fun `should return Bad request for invalid risk level`() {
            val request = MockMvcRequestBuilders
                .get("/users/me/investment-portfolio")
                .requestAttr("riskLevel", "99")

            val response = mockMvc.perform(request)

            response
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
        }

    }

}