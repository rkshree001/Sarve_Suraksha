package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCartBikeOdPremium {
    @SerializedName("basicOdPremium")
    @Expose
    var basicOdPremium: Int? = null

    @SerializedName("odDiscount")
    @Expose
    var odDiscount: Int? = null

    @SerializedName("odNCB")
    @Expose
    var odNCB: Int? = null

    @SerializedName("totalOdPremium")
    @Expose
    var totalOdPremium: Int? = null
}
