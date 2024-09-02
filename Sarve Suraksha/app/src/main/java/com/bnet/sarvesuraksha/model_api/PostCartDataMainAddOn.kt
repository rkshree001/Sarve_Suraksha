package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostCartDataMainAddOn {
    @SerializedName("addOnName")
    @Expose
    var addOnName: String? = null

    @SerializedName("addOnPremium")
    @Expose
    var addOnPremium: Int? = null
}
