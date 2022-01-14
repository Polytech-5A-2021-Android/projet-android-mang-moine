package com.polytech.myapplication.adapter

import com.polytech.myapplication.model.User

class UserListener(val clickListener: (userid: Long) -> Unit) {
    fun onClick(user: User) = clickListener(user.id)
}