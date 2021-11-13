package de.vitamin.investmentportfolio.models.responses

data class PortFolioResponse(val portfolio: List<ShareDetail> )

data class ShareDetail(val weight: Double, val ticker: String)
