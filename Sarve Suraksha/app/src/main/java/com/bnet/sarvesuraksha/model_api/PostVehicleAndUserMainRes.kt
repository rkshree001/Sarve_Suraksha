package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleAndUserMainRes {
    @SerializedName("vehicleId")
    @Expose
    var vehicleId: String? = null

    @SerializedName("isClaimed")
    @Expose
    var isClaimed: Boolean? = null

    @SerializedName("cngKitInstalled")
    @Expose
    var cngKitInstalled: Boolean? = null

    @SerializedName("userDetail")
    @Expose
    var userDetail: PostVehicleAndUserMainUserDetail? = null

    @SerializedName("loginId")
    @Expose
    var loginId: String? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null

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
