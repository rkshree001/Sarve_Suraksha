package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TravelActivePolicyRes {
    @SerializedName("orderId")
    @Expose
    var orderId: String? = null

    @SerializedName("policyDetail")
    @Expose
    var policyDetail: String? = null

    @SerializedName("insurerName")
    @Expose
    var insurerName: String? = null

    @SerializedName("insurerImg")
    @Expose
    var insurerImg: String? = null

    @SerializedName("medicalExpeenses")
    @Expose
    var medicalExpeenses: Int? = null

    @SerializedName("baggageLoss")
    @Expose
    var baggageLoss: Int? = null

    @SerializedName("LossOfPassport")
    @Expose
    var lossOfPassport: Int? = null

    @SerializedName("pNo")
    @Expose
    var pNo: String? = null

    @SerializedName("due")
    @Expose
    var due: String? = null

    @SerializedName("policyDoc")
    @Expose
    var policyDoc: String? = null

    @SerializedName("policyType")
    @Expose
    var policyType: String? = null

    fun getpNo(): String? {
        return pNo
    }

    fun setpNo(pNo: String?) {
        this.pNo = pNo
    }
}
