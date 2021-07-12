package com.aknrdlt.movieapp.ui.detail

import androidx.lifecycle.LiveData
import com.aknrdlt.movieapp.data.api.MovieDBInterface
import com.aknrdlt.movieapp.data.datasource.MovieNetworkDataSource
import com.aknrdlt.movieapp.data.datasource.NetworkState
import com.aknrdlt.movieapp.data.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : MovieDBInterface) {

    private lateinit var movieDetailsNetworkDataSource: MovieNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<Movie> {

        movieDetailsNetworkDataSource = MovieNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}