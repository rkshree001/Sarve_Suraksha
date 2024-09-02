package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostExternalLoanDetail {
    @SerializedName("isLoan")
    @Expose
    var isLoan: Boolean? = null

    @SerializedName("bankName")
    @Expose
    var bankName: String? = null
}
