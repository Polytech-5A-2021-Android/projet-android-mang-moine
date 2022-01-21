package com.polytech.myapplication.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Keep
@Entity(tableName="mesure", foreignKeys = [ForeignKey(entity = Seuil::class, parentColumns = ["id"], childColumns = ["seuil_id"])])
data class Mesure(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private var _id: Long = 0L,

    @ColumnInfo(name = "quantite_gaz")
    private var _quantite_gaz: Float = 0f,

    @ColumnInfo(name = "date_mesure")
    private var _date_mesure: Long = 0,

    @ColumnInfo(name = "seuil_id")
    private var _seuil_id: Long = 0L

): Parcelable, BaseObservable() {

    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            //notifyPropertyChanged(BR.id)
        }

    var quantite_gaz: Float
        @Bindable get() = _quantite_gaz
        set(value) {
            _quantite_gaz = value
            //notifyPropertyChanged(BR.username)
        }

    var date_mesure: Long
        @Bindable get() = _date_mesure
        set(value) {
            _date_mesure = value
            //notifyPropertyChanged(BR.birthdayDate)
        }

    var seuil_id: Long
        @Bindable get() = _seuil_id
        set(value) {
            _seuil_id = value
            //notifyPropertyChanged(BR.firstname)
        }


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readValue(Float::class.java.classLoader) as Float,
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeValue(_quantite_gaz)
        parcel.writeLong(_date_mesure)
        parcel.writeLong(_seuil_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Seuil> {
        override fun createFromParcel(parcel: Parcel): Seuil {
            return Seuil(parcel)
        }

        override fun newArray(size: Int): Array<Seuil?> {
            return arrayOfNulls(size)
        }
    }
}
