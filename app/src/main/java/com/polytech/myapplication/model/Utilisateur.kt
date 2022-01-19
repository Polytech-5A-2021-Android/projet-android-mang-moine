package com.polytech.myapplication.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.polytech.myapplication.BR

@Keep
@Entity(tableName="utilisateur")
data class Utilisateur (
@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = "id")
private var _id: Long = 0L,

@ColumnInfo(name = "username")
private var _username: String? = "",

@ColumnInfo(name = "password")
private var _password: String? = "",

@ColumnInfo(name = "salt")
private var _salt: String? = "",

@ColumnInfo(name = "token")
private var _token: String? = ""): Parcelable,
BaseObservable() {

    var id: Long
        @Bindable get() = _id
        set(value) {
            _id = value
            //notifyPropertyChanged(BR.id)
        }

    var username: String?
        @Bindable get() = _username
        set(value) {
            _username = value
            //notifyPropertyChanged(BR.username)
        }


    var password: String?
        @Bindable get() = _password
        set(value) {
            _password = value
            //notifyPropertyChanged(BR.firstname)
        }


    var salt: String?
        @Bindable get() = _salt
        set(value) {
            _salt = value
            //notifyPropertyChanged(BR.birthdayDate)
        }

    var token: String?
        @Bindable get() = _token
        set(value) {
            _token = value
            //notifyPropertyChanged(BR.gender)
        }



    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_username)
        parcel.writeString(_password)
        parcel.writeString(_salt)
        parcel.writeString(_token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Utilisateur> {
        override fun createFromParcel(parcel: Parcel): Utilisateur {
            return Utilisateur(parcel)
        }

        override fun newArray(size: Int): Array<Utilisateur?> {
            return arrayOfNulls(size)
        }
    }

}