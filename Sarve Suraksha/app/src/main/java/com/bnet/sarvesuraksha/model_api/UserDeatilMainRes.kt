package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserDeatilMainRes {
    @SerializedName("userDetail")
    @Expose
    var userDetail: UserDetailDtl? = null
}
