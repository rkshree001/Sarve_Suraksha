package com.bnet.sarvesuraksha.model_api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostSummaryDetailsTLMainGet(
    @SerializedName("insuranceDetail")
    @Expose
    var insuranceDetail: PostSummaryInsuranceDetailTL?,

    @SerializedName("summaryDetail")
    @Expose
    var summaryDetail: PostSummaryDetailsTL?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(PostSummaryInsuranceDetailTL::class.java.classLoader),
        parcel.readParcelable(PostSummaryDetailsTL::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(insuranceDetail, flags)
        parcel.writeParcelable(summaryDetail, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostSummaryDetailsTLMainGet> {
        override fun createFromParcel(parcel: Parcel): PostSummaryDetailsTLMainGet {
            return PostSummaryDetailsTLMainGet(parcel)
        }

        override fun newArray(size: Int): Array<PostSummaryDetailsTLMainGet?> {
            return arrayOfNulls(size)
        }
    }
}
