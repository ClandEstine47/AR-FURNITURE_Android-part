package com.clandestinestudio.arfurniture.Model
import android.os.Parcel
import android.os.Parcelable

class FurnitureModelClass (
    val id: String?,
    val name: String?,
    val category: String?,
    val image_url: String?,
    val dimensions: String?,
    val description: String?,
    val itemFolderName: String?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(category)
        parcel.writeString(image_url)
        parcel.writeString(dimensions)
        parcel.writeString(description)
        parcel.writeString(itemFolderName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FurnitureModelClass> {
        override fun createFromParcel(parcel: Parcel): FurnitureModelClass {
            return FurnitureModelClass(parcel)
        }

        override fun newArray(size: Int): Array<FurnitureModelClass?> {
            return arrayOfNulls(size)
        }
    }
}
