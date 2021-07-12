package com.aknrdlt.movieapp.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aknrdlt.movieapp.data.api.MovieDBInterface
import com.aknrdlt.movieapp.data.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieNetworkDataSource (private val apiService : MovieDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse =  MutableLiveData<Movie>()
    val downloadedMovieResponse: LiveData<Movie>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getMovieDetail(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            it.message?.let { it1 -> Log.e("MovieDetailsDataSource", it1) }
                        }
                    )
            )

        }
        catch (e: Exception){
            e.message?.let { Log.e("MovieDetailsDataSource", it) }
        }
    }
}