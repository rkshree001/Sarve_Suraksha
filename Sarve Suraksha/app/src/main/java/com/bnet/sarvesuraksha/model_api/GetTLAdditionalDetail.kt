package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetTLAdditionalDetail {
    @SerializedName("occupation")
    @Expose
    var occupation: String? = null

    @SerializedName("annuallIncome")
    @Expose
    var annuallIncome: GetTLAdditionalAnnuallIncome? = null

    @SerializedName("qualification")
    @Expose
    var qualification: String? = null

    @SerializedName("tobacco")
    @Expose
    var tobacco: Boolean? = null
}
