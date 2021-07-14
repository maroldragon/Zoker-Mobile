package com.example.perpustakaandigital.model
import android.os.Parcel
import android.os.Parcelable

class Peminjaman(
        var idPeminjaman : String? = "",
        var idUser : String? = "",
        var idBuku : String? = "",
        var tanggal : String? = "",
        var status: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(idPeminjaman)
        p0?.writeString(idUser)
        p0?.writeString(idBuku)
        p0?.writeString(tanggal)
        p0?.writeString(status)
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