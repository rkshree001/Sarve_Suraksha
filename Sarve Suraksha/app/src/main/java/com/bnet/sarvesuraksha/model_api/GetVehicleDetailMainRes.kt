package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleDetailMainRes {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("vehicleType")
    @Expose
    var vehicleType: String? = null

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

    @SerializedName("vehicleSegment")
    @Expose
    var vehicleSegment: String? = null

    @SerializedName("registrationDate")
    @Expose
    var registrationDate: String? = null

    @SerializedName("rtoCode")
    @Expose
    var rtoCode: String? = null

    @SerializedName("cityName")
    @Expose
    var cityName: String? = null

    @SerializedName("vehiclePictures")
    @Expose
    var vehiclePictures: List<GetVehiclePicture>? = null

    @SerializedName("insuranceDetail")
    @Expose
    var insuranceDetail: List<GetVehicleInsuranceDetail>? = null

    @SerializedName("typeOfVehicle")
    @Expose
    var typeOfVehicle: String? = null
}
