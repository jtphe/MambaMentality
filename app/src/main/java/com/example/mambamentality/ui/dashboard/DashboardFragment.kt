package com.example.mambamentality.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mambamentality.R
import com.example.mambamentality.adapter.MambaMovieAdapter
import com.example.mambamentality.api.RetrofitHolder
import com.example.mambamentality.api.services.MambaService
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await


class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mambaService: MambaService = RetrofitHolder.retrofit.create(MambaService::class.java)

        mambaService.getMambaMovie().also {
            GlobalScope.launch {
                it.await().results.also { listMambaMovie ->
                    val listMambaMovieFilter = listMambaMovie.filter { it.poster_path !== null }
                    withContext(Dispatchers.Main) {
                        recyclerViewMambaMovie.apply {
                            context?.also {
                                layoutManager = GridLayoutManager(context, 2)
                                adapter = MambaMovieAdapter(listMambaMovieFilter)
                            }
                        }
                    }
                }
            }
        }
    }
}