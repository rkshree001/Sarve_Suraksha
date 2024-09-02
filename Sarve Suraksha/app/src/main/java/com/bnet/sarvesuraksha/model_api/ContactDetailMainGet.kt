package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ContactDetailMainGet {
    @SerializedName("contactDetail")
    @Expose
    private var contactDetail: ContactDetailMainRes? = null

    fun getContactDetail(): ContactDetailMainRes? {
        return contactDetail
    }

    fun setContactDetail(contactDetail: ContactDetailMainRes?) {
        this.contactDetail = contactDetail
    }
}