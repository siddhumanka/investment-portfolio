package de.vitamin.investmentportfolio.helpers

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.vitamin.investmentportfolio.models.responses.ShareDetail
import java.io.File

object JsonFileReader {

    private val mapper = jacksonObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())

    fun readFile(file: File): Map<String, List<ShareDetail>> {
        return mapper.readValue(file)
    }
}