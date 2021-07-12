package com.aknrdlt.movieapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.aknrdlt.movieapp.R
import com.aknrdlt.movieapp.data.api.MovieDBInterface
import com.aknrdlt.movieapp.data.api.MovieDBObject
import com.aknrdlt.movieapp.data.api.POSTER_BASE_URL
import com.aknrdlt.movieapp.data.datasource.NetworkState
import com.aknrdlt.movieapp.data.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.rating_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : MovieDBInterface = MovieDBObject.getClient(this)
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: Movie){
        this.title = it.title

        movie_title.text = it.title
        released.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        tv_overview.text = it.overview

        val moviePosterURL = POSTER_BASE_URL + it.backdropPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_poster);
    }


    private fun getViewModel(movieId:Int): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieRepository,movieId) as T
            }
        })[MovieViewModel::class.java]
    }
}

