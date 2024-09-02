package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VehicleInsuranceRes {
    @SerializedName("active")
    @Expose
    var active: Int? = null

    @SerializedName("expired")
    @Expose
    var expired: Int? = null

    @SerializedName("activePolicy")
    @Expose
    var activePolicy: List<VehicleActivePolicyRes>? = null

    @SerializedName("expiredPolicye")
    @Expose
    var expiredPolicye: List<VehicleExpiredPolicyeRes>? = null
}
