package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleRtoDetail {
    @SerializedName("rtoCode")
    @Expose
    var rtoCode: String? = null

    @SerializedName("rtoName")
    @Expose
    var rtoName: String? = null

    @SerializedName("pincode")
    @Expose
    var pincode: String? = null

    @SerializedName("cityName")
    @Expose
    var cityName: String? = null

    @SerializedName("stateName")
    @Expose
    var stateName: String? = null


}
