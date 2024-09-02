package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAddedFamilyMemberMainGet {
    @SerializedName("memberDetail")
    @Expose
    var memberDetail: GetAddedFamilyMemberMainRes? = null
}
