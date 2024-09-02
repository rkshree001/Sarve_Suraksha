package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleInsuranceDetail {
    @SerializedName("insuranceType")
    @Expose
    var insuranceType: String? = null

    @SerializedName("insurerName")
    @Expose
    var insurerName: String? = null

    @SerializedName("policyNumber")
    @Expose
    var policyNumber: String? = null

    @SerializedName("policyExpiryDate")
    @Expose
    var policyExpiryDate: String? = null
}
