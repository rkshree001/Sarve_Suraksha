package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleInsDeclarationDetail {
    @SerializedName("declareInformation")
    @Expose
    var declareInformation: Boolean? = null

    @SerializedName("KycConsent")
    @Expose
    var kycConsent: Boolean? = null
}
