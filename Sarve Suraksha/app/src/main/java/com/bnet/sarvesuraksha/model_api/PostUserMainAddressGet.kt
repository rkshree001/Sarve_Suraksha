package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PostUserMainAddressGet {

    @SerializedName("addressDetail")
    @Expose
    private var addressDetail: PostUserMainAddressRes? = null

    fun getAddressDetail(): PostUserMainAddressRes? {
        return addressDetail
    }

    fun setAddressDetail(addressDetail: PostUserMainAddressRes?) {
        this.addressDetail = addressDetail
    }
}