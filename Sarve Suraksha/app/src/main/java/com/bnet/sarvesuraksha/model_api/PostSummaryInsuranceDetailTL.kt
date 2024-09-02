package com.bnet.sarvesuraksha.model_api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostSummaryInsuranceDetailTL(
    @SerializedName("_id")
    @Expose
    var id: String? = null,

    @SerializedName("insurerName")
    @Expose
    var insurerName: String? = null,

    @SerializedName("insurerImg")
    @Expose
    var insurerImg: String? = null,

    @SerializedName("planName")
    @Expose
    var planName: String? = null,

    @SerializedName("premium")
    @Expose
    var premium: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(insurerName)
        parcel.writeString(insurerImg)
        parcel.writeString(planName)
        parcel.writeValue(premium)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostSummaryInsuranceDetailTL> {
        override fun createFromParcel(parcel: Parcel): PostSummaryInsuranceDetailTL {
            return PostSummaryInsuranceDetailTL(parcel)
        }

        override fun newArray(size: Int): Array<PostSummaryInsuranceDetailTL?> {
            return arrayOfNulls(size)
        }
    }
}
