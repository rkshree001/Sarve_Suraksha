package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostQuoteAdditionalDetailMainGet {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("logInId")
    @Expose
    var logInId: String? = null

    @SerializedName("basicDetail")
    @Expose
    var basicDetail: PostQuoteAdditionalBasicDetail? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null

    @SerializedName("personalDetail")
    @Expose
    var personalDetail: PostQuoteAdditionalPersonalDetail? = null

    @SerializedName("additionalDetail")
    @Expose
    var additionalDetail: PostQuoteAdditionalDetail? = null
}
