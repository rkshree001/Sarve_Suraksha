package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostExternalCNGKitDetail {
    @SerializedName("isInstalled")
    @Expose
    var isInstalled: Boolean? = null

    @SerializedName("kitValue")
    @Expose
    var kitValue: Int? = null
}
