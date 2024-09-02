package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCartDetailsTpPremium {
    @SerializedName("basicTPPremium")
    @Expose
    var basicTPPremium: Int? = null
}
