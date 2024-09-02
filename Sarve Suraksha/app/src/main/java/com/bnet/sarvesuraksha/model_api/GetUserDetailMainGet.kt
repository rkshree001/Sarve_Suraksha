package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetUserDetailMainGet(
    @SerializedName("memberList")
    @Expose
    val memberList: List<GetUserDetailMainRes>? = null
)
