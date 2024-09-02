package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PostIdentificationDetailMainGet {
    @SerializedName("identificationDetail")
    @Expose
    private var identificationDetail: UserIdentificationDetail? = null

    fun getIdentificationDetail(): UserIdentificationDetail? {
        return identificationDetail
    }

    fun setIdentificationDetail(identificationDetail: UserIdentificationDetail?) {
        this.identificationDetail = identificationDetail
    }
}