package com.example.mambamentality.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movie : RealmObject() {

    @PrimaryKey
    var id: Int = 0
    var title: String? = null
    var overview: String? = null
    var backdrop_path: String? = null
    var poster_path: String? = null
    var vote_average: Float? = null
    var vote_count: Int? = null
    var genres: RealmList<Genre> = RealmList()
    var isInFavorites: Boolean = false
}