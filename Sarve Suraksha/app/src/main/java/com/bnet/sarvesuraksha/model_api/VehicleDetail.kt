package com.bnet.sarvesuraksha.model_api


data class VehicleDetail(
    val registrationNumber: String,
    val makerName: String,
    val modelName: String,
    val variantName: String,
    val fuelType: String,
    val manufacturingDate: String,
    val registrationDate: String,
    val engineNumber: String,
    val chassisNumber: String,
    val rtoDetail: RtoDetail,
    val vehiclePictures: List<VehiclePicture>
)