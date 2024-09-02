package com.bnet.sarvesuraksha.Repository

import com.bnet.sarvesuraksha.api.ApiInterface
import javax.inject.Inject

class VehicleRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    suspend fun getAllVehicleDetailList(mobileNumberString: String) = apiInterface.getAllVehicleDetailListSus(mobileNumberString)
}