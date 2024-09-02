package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleDetailRc {
    @SerializedName("registrationNumber")
    @Expose
    var registrationNumber: String? = null

    @SerializedName("makerName")
    @Expose
    var makerName: String? = null

    @SerializedName("modelName")
    @Expose
    var modelName: String? = null

    @SerializedName("variantName")
    @Expose
    var variantName: String? = null

    @SerializedName("fuelType")
    @Expose
    var fuelType: String? = null

    @SerializedName("manufacturingDate")
    @Expose
    var manufacturingDate: String? = null

    @SerializedName("registrationDate")
    @Expose
    var registrationDate: String? = null

    @SerializedName("engineNumber")
    @Expose
    var engineNumber: String? = null

    @SerializedName("chassisNumber")
    @Expose
    var chassisNumber: String? = null

    @SerializedName("rtoDetail")
    @Expose
    var rtoDetail: GetVehicleRtoDetail? = null

    @SerializedName("vehiclePictures")
    @Expose
    var vehiclePictures: List<GetVehiclePicture>? = null
}
