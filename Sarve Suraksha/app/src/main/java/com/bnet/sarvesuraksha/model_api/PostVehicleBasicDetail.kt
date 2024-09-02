package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleBasicDetail {
    @SerializedName("registrationNumber")
    @Expose
    var registrationNumber: String? = null

    @SerializedName("manufacturingMonth")
    @Expose
    var manufacturingMonth: String? = null

    @SerializedName("manufacturingYear")
    @Expose
    var manufacturingYear: String? = null

    @SerializedName("registrationDate")
    @Expose
    var registrationDate: String? = null

    @SerializedName("engineNumber")
    @Expose
    var engineNumber: String? = null

    @SerializedName("chassisNumber")
    @Expose
    var chassisNumber: String? = null
}
