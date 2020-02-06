package com.example.mambamentality.ui.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mambamentality.R
import com.example.mambamentality.adapter.MambaMovieAdapter
import com.example.mambamentality.api.RetrofitHolder.retrofit
import com.example.mambamentality.api.services.MambaService
import com.example.mambamentality.models.Movie
import com.google.android.material.snackbar.Snackbar
import com.vicpin.krealmextensions.query
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieService: MambaService = retrofit.create(MambaService::class.java)

        val movieName = view.search_input.text
        view.search_button.setOnClickListener {
            if (movieName.trim().length > 3) {
                movieService.getPlayersMovie(movieName.toString()).also {
                    GlobalScope.launch {
                        it.await().results.also {
                            if (it.isNotEmpty()) {
                                val newList = it.filter { it.poster_path !== null }
                                if (newList.isNotEmpty()) {
                                    withContext(Dispatchers.Main) {
                                        context?.apply {
                                            recyclerViewMambaMovie.apply {
                                                layoutManager = GridLayoutManager(context, 2)
                                                adapter = MambaMovieAdapter(newList)

                                                view.search_button.visibility = if (it.isEmpty()) {
                                                    View.VISIBLE
                                                } else {
                                                    View.GONE
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                val notFoundSnack = Snackbar.make(
                                    view, "Movie not found", Snackbar.LENGTH_LONG
                                )
                                notFoundSnack.view.setBackgroundColor(Color.parseColor("#fdcb6e"))
                                notFoundSnack.setActionTextColor(Color.parseColor("#6c5ce7"))
                                notFoundSnack.setAction("DISMISS", {
                                    notFoundSnack.dismiss()
                                })
                                notFoundSnack.show()
                            }
                        }
                    }
                }
            } else {
                val notEmptySnack = Snackbar.make(
                    view, "The movie name can't be empty", Snackbar.LENGTH_LONG
                )
                notEmptySnack.view.setBackgroundColor(Color.parseColor("#fdcb6e"))
                notEmptySnack.setActionTextColor(Color.parseColor("#6c5ce7"))
                notEmptySnack.setAction("DISMISS", {
                    notEmptySnack.dismiss()
                })
                notEmptySnack.show()
            }
        }
    }
}