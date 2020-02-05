package com.example.mambamentality.api.services

import com.example.mambamentality.models.Movie
import com.example.mambamentality.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MambaService {


    @GET("search/movie?query=Kobe%20Bryant")
    fun getMambaMovie(
        @Query("api_key") apiKey: String = "66ee102c15b779b77afde5b5948b26c4"
    ): Call<MovieResponse>
}