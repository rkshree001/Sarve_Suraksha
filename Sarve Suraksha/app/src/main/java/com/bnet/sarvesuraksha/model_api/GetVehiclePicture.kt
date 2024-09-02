package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehiclePicture {
    @SerializedName("picturId")
    @Expose
    var picturId: String? = null

    @SerializedName("pictureName")
    @Expose
    var pictureName: String? = null

    @SerializedName("picture")
    @Expose
    var picture: String? = null
}
