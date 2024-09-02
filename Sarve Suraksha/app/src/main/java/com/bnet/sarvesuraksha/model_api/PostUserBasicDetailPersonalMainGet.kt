package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class PostUserBasicDetailPersonalMainGet {
    @SerializedName("personalDetail")
    @Expose
    private var personalDetail: PostUserBasicDetailPersonalMainRes? = null

    fun getPersonalDetail(): PostUserBasicDetailPersonalMainRes? {
        return personalDetail
    }

    fun setPersonalDetail(personalDetail: PostUserBasicDetailPersonalMainRes?) {
        this.personalDetail = personalDetail
    }

}