package de.vitamin.investmentportfolio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class InvestmentPortfolioApplication

fun main(args: Array<String>) {
	runApplication<InvestmentPortfolioApplication>(*args)
}
