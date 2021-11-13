package de.vitamin.investmentportfolio.clients

import de.vitamin.investmentportfolio.clients.responses.HistoricalDataResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "financial-modeling-client", url = "\${financial-modeling.url}")
interface FinancialModelingClient {

    @RequestMapping(
        value = ["/api/v3/historical-price-full/{stockName}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        method = [RequestMethod.GET]
    )
    fun getHistoricalData(@PathVariable("stockName") stockName: String,
                          @RequestParam("from") fromDate: String,
                          @RequestParam("to") toDate: String,
                          @RequestParam("apikey") apiKey: String)
            : HistoricalDataResponse

}