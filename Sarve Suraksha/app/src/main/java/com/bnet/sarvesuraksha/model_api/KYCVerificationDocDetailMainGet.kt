package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class KYCVerificationDocDetailMainGet {
    @SerializedName("quoteId")
    @Expose
    var quoteId: String? = null

    @SerializedName("docDetail")
    @Expose
    var docDetail: KYCVerificationDocDetail? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
