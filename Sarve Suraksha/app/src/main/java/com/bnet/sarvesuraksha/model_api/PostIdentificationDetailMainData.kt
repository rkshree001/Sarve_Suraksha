package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PostIdentificationDetailMainData {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: PostIdentificationDetailMainGet? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): PostIdentificationDetailMainGet? {
        return data
    }

    fun setData(data: PostIdentificationDetailMainGet?) {
        this.data = data
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

}