package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VerifyOtpMainResToken {
    @SerializedName("token")
    @Expose
    var token: String? = null
}
