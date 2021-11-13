package de.vitamin.investmentportfolio.exceptions

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = BAD_REQUEST)
data class InvalidAttributeException(override val message: String) : Exception(message)
