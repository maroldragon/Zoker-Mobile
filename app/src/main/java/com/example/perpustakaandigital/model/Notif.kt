package com.example.perpustakaandigital.model

import android.os.Parcel
import android.os.Parcelable

class Notif(
        var id : String?,
        var title : String?,
        var status : String?,
        var hasLooked : String?
): Parcelable {
        constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
        }

        override fun writeToParcel(p0: Parcel?, p1: Int) {
                p0?.writeString(id)
                p0?.writeString(title)
                p0?.writeString(status)
                p0?.writeString(hasLooked)
        }

        override fun describeContents(): Int {
                TODO("Not yet implemented")
        }

        companion object CREATOR : Parcelable.Creator<Notif> {
                override fun createFromParcel(parcel: Parcel): Notif {
                        return Notif(parcel)
                }

                override fun newArray(size: Int): Array<Notif?> {
                        return arrayOfNulls(size)
                }
        }
}