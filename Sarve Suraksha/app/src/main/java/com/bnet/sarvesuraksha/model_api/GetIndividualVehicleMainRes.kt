package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetIndividualVehicleMainRes {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("logInId")
    @Expose
    var logInId: String? = null

    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: GetVehicleDetailRc? = null

    @SerializedName("ownerdetail")
    @Expose
    var ownerdetail: GetVehicleOwnerDetail? = null

    @SerializedName("insuranceDetail")
    @Expose
    var insuranceDetail: List<GetVehicleInsuranceDetail>? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
