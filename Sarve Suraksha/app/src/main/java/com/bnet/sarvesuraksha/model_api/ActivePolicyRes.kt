package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ActivePolicyRes(
    @SerializedName("orderId")
    @Expose
    val orderId: String? = null,

    @SerializedName("policyDetail")
    @Expose
    val policyDetail: String? = null,

    @SerializedName("insurerName")
    @Expose
    val insurerName: String? = null,

    @SerializedName("insurerImg")
    @Expose
    val insurerImg: String? = null,

    @SerializedName("premium")
    @Expose
    val premium: Int? = null,

    @SerializedName("sumInsured")
    @Expose
    val sumInsured: Int? = null,

    @SerializedName("pNo")
    @Expose
    val pNo: String? = null,

    @SerializedName("due")
    @Expose
    val due: String? = null,

    @SerializedName("policyDoc")
    @Expose
    val policyDoc: String? = null,

    @SerializedName("policyType")
    @Expose
    val policyType: String? = null
)
