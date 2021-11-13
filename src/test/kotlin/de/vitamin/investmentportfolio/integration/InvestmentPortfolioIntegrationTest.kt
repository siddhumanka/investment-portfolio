package de.vitamin.investmentportfolio.integration

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class InvestmentPortfolioIntegrationTest {

    @Autowired
    private lateinit var testClient: WebTestClient

    @Test
    internal fun `should return 404 for a non existing endpoint`() {
        testClient.post()
            .uri("/non-existing")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType("application", "graphql"))
            .bodyValue("{}")
            .exchange()
            .expectStatus().isNotFound
    }

}