package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyPoliciesMainGet {
    @SerializedName("healthInsurance")
    @Expose
    var healthInsurance: HealthInsuranceRes? = null

    @SerializedName("travelInsurance")
    @Expose
    var travelInsurance: TravelInsuranceRes? = null

    @SerializedName("vehicleInsurance")
    @Expose
    var vehicleInsurance: VehicleInsuranceRes? = null
}
