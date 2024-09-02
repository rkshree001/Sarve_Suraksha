package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetUserDetailMainRes(
    @SerializedName("profilePicture")
    @Expose
    val profilePicture: String? = null,

    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("fullName")
    @Expose
    val fullName: String? = null,

    @SerializedName("memberType")
    @Expose
    val memberType: String? = null
)
