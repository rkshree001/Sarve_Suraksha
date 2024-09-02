package com.bnet.sarvesuraksha.model_api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostSummaryDetailsTLMainData(
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("data")
    @Expose
    var data: PostSummaryDetailsTLMainGet? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readParcelable(PostSummaryDetailsTLMainGet::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(statusCode)
        parcel.writeString(message)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostSummaryDetailsTLMainData> {
        override fun createFromParcel(parcel: Parcel): PostSummaryDetailsTLMainData {
            return PostSummaryDetailsTLMainData(parcel)
        }

        override fun newArray(size: Int): Array<PostSummaryDetailsTLMainData?> {
            return arrayOfNulls(size)
        }
    }
}
