package com.example.perpustakaandigital.model

import android.os.Parcel
import android.os.Parcelable

class Book(
        var id : String?,
        var name : String?,
        var image : String?,
        var category : String?,
        var author : String?,
        var description : String?
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
                p0?.writeString(id)
                p0?.writeString(name)
                p0?.writeString(image)
                p0?.writeString(category)
                p0?.writeString(author)
                p0?.writeString(description)
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