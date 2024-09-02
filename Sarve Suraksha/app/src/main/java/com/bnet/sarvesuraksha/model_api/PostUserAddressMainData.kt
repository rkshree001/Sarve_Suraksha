package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PostUserAddressMainData {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: PostUserMainAddressGet? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): PostUserMainAddressGet? {
        return data
    }

    fun setData(data: PostUserMainAddressGet?) {
        this.data = data
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }
}