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
@Entity(tableName="seuil", foreignKeys = [ForeignKey(entity = Utilisateur::class, parentColumns = ["id"], childColumns = ["utilisateur_id"])])
data class Seuil(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private var _id: Long = 0L,

    @ColumnInfo(name = "valeur")
    private var _valeur: Float = 0f,

    @ColumnInfo(name = "utilisateur_id")
    private var _utilisateur_id: Long = 0L

): Parcelable, BaseObservable() {

    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            //notifyPropertyChanged(BR.id)
        }

    var valeur: Float
        @Bindable get() = _valeur
        set(value) {
            _valeur = value
            //notifyPropertyChanged(BR.username)
        }


    var utilisateur_id: Long
        @Bindable get() = _utilisateur_id
        set(value) {
            _utilisateur_id = value
            //notifyPropertyChanged(BR.firstname)
        }



    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readValue(Float::class.java.classLoader) as Float,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeValue(_valeur)
        parcel.writeLong(_utilisateur_id)
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
