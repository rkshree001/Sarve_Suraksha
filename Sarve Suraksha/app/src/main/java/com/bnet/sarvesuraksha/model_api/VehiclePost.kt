package com.bnet.sarvesuraksha.model_api

data class VehiclePost(
    val vehicleDetail: VehicleDetail,
    val ownerdetail: OwnerDetail,
    val insuranceDetail: List<InsuranceDetail>,
    val logInId: String
)