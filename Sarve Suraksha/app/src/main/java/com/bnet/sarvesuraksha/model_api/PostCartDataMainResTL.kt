package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostCartDataMainResTL {
    @SerializedName("insuranceId")
    @Expose
    var insuranceId: String? = null

    @SerializedName("quoteId")
    @Expose
    var quoteId: String? = null

    @SerializedName("sumAssured")
    @Expose
    var sumAssured: Int? = null

    @SerializedName("coverUpto")
    @Expose
    var coverUpto: Int? = null

    @SerializedName("planType")
    @Expose
    var planType: String? = null

    @SerializedName("paymentTenure")
    @Expose
    var paymentTenure: Int? = null

    @SerializedName("paymentFrequency")
    @Expose
    var paymentFrequency: String? = null

    @SerializedName("addOnDetail")
    @Expose
    var addOnDetail: List<String>? = null

    @SerializedName("gst")
    @Expose
    var gst: Int? = null

    @SerializedName("premium")
    @Expose
    var premium: Double? = null

    @SerializedName("gstAmount")
    @Expose
    var gstAmount: Double? = null

    @SerializedName("discount")
    @Expose
    var discount: Int? = null

    @SerializedName("totalPremium")
    @Expose
    var totalPremium: Double? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
