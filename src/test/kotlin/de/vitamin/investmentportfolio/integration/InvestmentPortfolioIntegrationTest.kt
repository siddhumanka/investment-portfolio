package de.vitamin.investmentportfolio.integration

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.reactive.function.BodyInserters

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "36000")
class InvestmentPortfolioIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Autowired
    private lateinit var webclient: WebTestClient

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


    @Nested
    inner class CurrentValueTest {

        @Test
        internal fun `should return Bad request for invalid from date provided`() {
            val request = MockMvcRequestBuilders
                .post("/users/me/investment-portfolio/current-value")
                .contentType(APPLICATION_JSON)
                .content(
                    """{
                              "from"                : "ajhdbdjc",   |
                              "to"                  : "01.03.2021", |
                              "monthlyContribution" : 450,          |
                              "riskLevel"           : 5  
                         }""".trimMargin()
                )

            val response = mockMvc.perform(request)

            response
                .andExpect(status().isBadRequest)
        }

        @Test
        internal fun `should return Bad request for invalid to date provided`() {
            val request = MockMvcRequestBuilders
                .post("/users/me/investment-portfolio/current-value")
                .contentType(APPLICATION_JSON)
                .content(
                    """{
                              "from"                : "01.03.2021",   |
                              "to"                  : "01.45.2021", |
                              "monthlyContribution" : 450,          |
                              "riskLevel"           : 5  
                         }""".trimMargin()
                )

            val response = mockMvc.perform(request)

            response
                .andExpect(status().isBadRequest)
        }

        @Test
        internal fun `should return Bad request for invalid monthly contribution provided`() {
            val request = MockMvcRequestBuilders
                .post("/users/me/investment-portfolio/current-value")
                .contentType(APPLICATION_JSON)
                .content(
                    """{
                              "from"                : "01.03.2021",   |
                              "to"                  : "01.45.2021", |
                              "monthlyContribution" : ajsbfdjh,          |
                              "riskLevel"           : 5  
                         }""".trimMargin()
                )

            val response = mockMvc.perform(request)

            response
                .andExpect(status().isBadRequest)
        }

        @Test
        internal fun `should return current value portfolio for valid request`() {

            webclient
                .post()
                .uri("/users/me/investment-portfolio/current-value")
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromValue("{\n" +
                            "\"from\"                : \"01.01.2017\",\n" +
                            "\"to\"                  : \"09.03.2017\",\n" +
                            "\"monthlyContribution\" : 80.00,\n" +
                            "\"riskLevel\"           : 7\n" +
                            "}"))
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("$.totalInvestment").isEqualTo(208.00)
                .jsonPath("$.currentPortfolio[0].ticker").isEqualTo("CAKE")
                .jsonPath("$.currentPortfolio[0].currentValue").isEqualTo(168.00)
                .jsonPath("$.currentPortfolio[1].ticker").isEqualTo("PZZA")
                .jsonPath("$.currentPortfolio[1].currentValue").isEqualTo(42.00)
                .jsonPath("$.currentPortfolio[2].ticker").isEqualTo("EAT")
                .jsonPath("$.currentPortfolio[2].currentValue").isEqualTo(64.0)
        }

    }

    companion object {
        private lateinit var wmServer: WireMockServer

        @BeforeAll
        @JvmStatic
        internal fun startWireMock() {
            wmServer = WireMockServer(
                WireMockConfiguration().port(8081)
                    .extensions(ResponseTemplateTransformer(false))
            )
            wmServer.start()
        }

        @AfterAll
        @JvmStatic
        internal fun stopWireMock() {
            wmServer.stop()
        }
    }

}