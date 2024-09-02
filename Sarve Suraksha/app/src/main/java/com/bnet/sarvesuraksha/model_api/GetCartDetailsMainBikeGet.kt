package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCartDetailsMainBikeGet {
    @SerializedName("insurerName")
    @Expose
    var insurerName: String? = null

    @SerializedName("insurerImg")
    @Expose
    var insurerImg: String? = null

    @SerializedName("insuranceType")
    @Expose
    var insuranceType: String? = null

    @SerializedName("IDVCover")
    @Expose
    var iDVCover: Int? = null

    @SerializedName("NCB")
    @Expose
    var ncb: Int? = null

    @SerializedName("premiumDetail")
    @Expose
    var premiumDetail: GetCartBikePremiumDetail? = null

    @SerializedName("addOnPremium")
    @Expose
    var addOnPremium: Int? = null

    @SerializedName("totalPremium")
    @Expose
    var totalPremium: Int? = null

    @SerializedName("gst")
    @Expose
    var gst: Int? = null

    @SerializedName("gstAmount")
    @Expose
    var gstAmount: Double? = null

    @SerializedName("payablePremium")
    @Expose
    var payablePremium: Double? = null
}
