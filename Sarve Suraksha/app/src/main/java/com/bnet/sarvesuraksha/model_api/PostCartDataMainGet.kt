package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostCartDataMainGet {
    @SerializedName("policyId")
    @Expose
    var policyId: String? = null

    @SerializedName("IDVCover")
    @Expose
    var iDVCover: Int? = null

    @SerializedName("NCB")
    @Expose
    var ncb: Int? = null

    @SerializedName("tenure")
    @Expose
    var tenure: Int? = null

    @SerializedName("addOn")
    @Expose
    var addOn: List<PostCartDataMainAddOn>? = null

    @SerializedName("premium")
    @Expose
    var premium: Int? = null

    @SerializedName("gst")
    @Expose
    var gst: Int? = null

    @SerializedName("quoteId")
    @Expose
    var quoteId: String? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
