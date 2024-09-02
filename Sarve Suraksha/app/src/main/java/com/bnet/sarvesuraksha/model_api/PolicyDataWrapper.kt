package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PolicyDataWrapper<T> {
    @SerializedName("data")
    @Expose
    var data: T? = null
}
