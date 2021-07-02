package com.example.perpustakaandigital.model
import android.os.Parcel
import android.os.Parcelable

class Pinjam(
        var pinjamId : String?,
        var bookId : String?,
        var tanggal : String?
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(pinjamId)
        p0?.writeString(bookId)
        p0?.writeString(tanggal)
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}