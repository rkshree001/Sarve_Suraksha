package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCartDetailsPremiumDetail {
    @SerializedName("odPremium")
    @Expose
    var odPremium: GetCartDetailsOdPremium? = null

    @SerializedName("tpPremium")
    @Expose
    var tpPremium: GetCartDetailsTpPremium? = null
}
