package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetTermLifeQuoteListMainGet {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("insurerName")
    @Expose
    var insurerName: String? = null

    @SerializedName("insurerSlug")
    @Expose
    var insurerSlug: String? = null

    @SerializedName("insurerImg")
    @Expose
    var insurerImg: String? = null

    @SerializedName("planName")
    @Expose
    var planName: String? = null

    @SerializedName("planSlug")
    @Expose
    var planSlug: String? = null

    @SerializedName("sumAssured")
    @Expose
    var sumAssured: Int? = null

    @SerializedName("coverUpto")
    @Expose
    var coverUpto: Int? = null

    @SerializedName("paymentTenure")
    @Expose
    var paymentTenure: Int? = null

    @SerializedName("paymentFrequency")
    @Expose
    var paymentFrequency: String? = null

    @SerializedName("claimSettled")
    @Expose
    var claimSettled: String? = null

    @SerializedName("discount")
    @Expose
    var discount: Int? = null

    @SerializedName("addOndetail")
    @Expose
    var addOndetail: List<GetTermLifeQuoteListAddOndetail>? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null

    @SerializedName("premium")
    @Expose
    var premium: Double? = null
}
