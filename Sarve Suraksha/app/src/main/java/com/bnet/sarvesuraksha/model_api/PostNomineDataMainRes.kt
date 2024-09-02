package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostNomineDataMainRes {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: PostNomineDataVehDtl? = null

    @SerializedName("vehicleBasicDetail")
    @Expose
    var vehicleBasicDetail: AddVehicleBasicDetail? = null

    @SerializedName("ownerDetail")
    @Expose
    var ownerDetail: PostVehicleOwnerDetail? = null

    @SerializedName("insuranceDetail")
    @Expose
    var insuranceDetail: List<GetQouteFormListInsuranceDetail>? = null

    @SerializedName("vehicleType")
    @Expose
    var vehicleType: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
