package com.example.mambamentality.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.mambamentality.R
import com.example.mambamentality.adapter.MambaMovieAdapter
import com.example.mambamentality.api.RetrofitHolder
import com.example.mambamentality.api.services.MambaService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.await

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun start(context: Context, movieId: Int) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            })
        }
    }

    private val getMovieIdFromExtra by lazy {
        intent?.getIntExtra(EXTRA_MOVIE_ID, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mambaService: MambaService = RetrofitHolder.retrofit.create(MambaService::class.java)

        mambaService.getMambaMovieDetail(getMovieIdFromExtra.toString()).also{
            GlobalScope.launch {
                it.await().also{
                    withContext(Dispatchers.Main){
                        val titleDetail: TextView = findViewById(R.id.activity_detail_title)
                        val descriptionDetail: TextView = findViewById(R.id.activity_detail_description)

                        titleDetail.text = it.title
                        descriptionDetail.text = it.overview
                    }
                }
            }
        }
    }
}
