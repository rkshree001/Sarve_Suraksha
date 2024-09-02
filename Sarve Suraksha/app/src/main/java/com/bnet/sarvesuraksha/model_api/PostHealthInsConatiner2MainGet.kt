package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostHealthInsConatiner2MainGet {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("basicDetail")
    @Expose
    var basicDetail: PostHealthInsuranceBasicDetail? = null

    @SerializedName("memberDetail")
    @Expose
    var memberDetail: List<Any>? = null

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
    var personalDetail: PostHealthInsC2PersonalDetail? = null
}
