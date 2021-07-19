package com.example.perpustakaandigital.model

import android.os.Parcel
import android.os.Parcelable

class RatingPrediksi(
    var idRating : String? = "",
    var idUser : String? = "",
    var idBuku : String? = "",
    var rating : String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
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
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<RatingPrediksi> {
        override fun createFromParcel(parcel: Parcel): RatingPrediksi{
            return RatingPrediksi(parcel)
        }

        override fun newArray(size: Int): Array<RatingPrediksi?> {
            return arrayOfNulls(size)
        }
    }
}