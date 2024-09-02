package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCartBikePremiumDetail {
    @SerializedName("odPremium")
    @Expose
    var odPremium: GetCartBikeOdPremium? = null
}
