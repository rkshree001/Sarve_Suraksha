package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GetUserDeatilsMainGet {

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: GetUserDeatilsMainRes? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): GetUserDeatilsMainRes? {
        return data
    }

    fun setData(data: GetUserDeatilsMainRes?) {
        this.data = data
    }

}