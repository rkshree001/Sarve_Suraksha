package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AdditionalDetailMainGet {
    @SerializedName("additionalDetail")
    @Expose
    private var additionalDetail: AdditionalDetailMainRes? = null

    fun getAdditionalDetail(): AdditionalDetailMainRes? {
        return additionalDetail
    }

    fun setAdditionalDetail(additionalDetail: AdditionalDetailMainRes?) {
        this.additionalDetail = additionalDetail
    }
}