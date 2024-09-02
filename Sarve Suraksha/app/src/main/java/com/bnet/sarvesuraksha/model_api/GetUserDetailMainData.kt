package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetUserDetailMainData(
    @SerializedName("status")
    @Expose
    val status: Int? = null,

    @SerializedName("data")
    @Expose
    val data: GetUserDetailMainGet? = null
)
