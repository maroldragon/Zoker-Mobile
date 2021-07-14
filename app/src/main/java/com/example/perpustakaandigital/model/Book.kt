package com.example.perpustakaandigital.model

import android.os.Parcel
import android.os.Parcelable

class Book(
        var isbn : String? = "",
        var judul : String? = "",
        var file : String? = "",
        var tahunTerbit : String? = "",
        var penerbit : String? = "",
        var rating : String? = "",
        var cover : String? = "",
        var kategori : String? = "",
        var penulis : String? = "",
        var deskripsi : String? = ""
): Parcelable {
        constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
        }

        override fun writeToParcel(p0: Parcel?, p1: Int) {
                p0?.writeString(isbn)
                p0?.writeString(judul)
                p0?.writeString(file)
                p0?.writeString(tahunTerbit)
                p0?.writeString(penerbit)
                p0?.writeString(rating)
                p0?.writeString(cover)
                p0?.writeString(kategori)
                p0?.writeString(penulis)
                p0?.writeString(deskripsi)
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