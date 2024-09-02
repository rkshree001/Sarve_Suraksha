package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserDetailDtl {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("mobile_number")
    @Expose
    var mobileNumber: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
}
