package com.example.mambamentality.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movie : RealmObject() {

    @PrimaryKey
    var id: Int = 0
    var title: String? = null
    var poster_path: String? = null
    var overview: String? = null
    var isInFavorites: Boolean = false
}