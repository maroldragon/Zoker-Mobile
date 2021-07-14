package com.example.perpustakaandigital.model

import android.os.Parcel
import android.os.Parcelable

class Rating(
    var idRating : String? = "",
    var idUser : String? = "",
    var idBuku : String? = "",
    var rating : String? = "",
    var ulasan: String? = "",
    var tanggal : String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(idRating)
        p0?.writeString(idUser)
        p0?.writeString(idBuku)
        p0?.writeString(rating)
        p0?.writeString(ulasan)
        p0?.writeString(tanggal)
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Rating> {
        override fun createFromParcel(parcel: Parcel): Rating{
            return Rating(parcel)
        }

        override fun newArray(size: Int): Array<Rating?> {
            return arrayOfNulls(size)
        }
    }
}