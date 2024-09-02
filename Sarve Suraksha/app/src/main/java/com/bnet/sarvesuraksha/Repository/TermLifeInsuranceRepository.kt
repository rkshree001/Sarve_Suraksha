package com.bnet.sarvesuraksha.Repository


import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListMainData
import retrofit2.Call
import javax.inject.Inject

class TermLifeInsuranceRepository @Inject constructor(private val apiInterface: ApiInterface) {

    fun postAndGetTLQuote(termLifeID: String, data: String?): Call<GetTermLifeQuoteListMainData?> {
        return apiInterface.postAndGetTLQuote(termLifeID, data)
    }
}
