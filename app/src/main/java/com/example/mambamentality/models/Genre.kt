package com.example.mambamentality.models

import io.realm.RealmObject

open class Genre : RealmObject() {
    var id: Int = 0
    var name: String? = null
    override fun toString(): String {
        return "$name"
    }
}