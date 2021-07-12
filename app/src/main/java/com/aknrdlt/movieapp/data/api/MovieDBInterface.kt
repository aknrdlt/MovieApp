package com.aknrdlt.movieapp.data.api

import com.aknrdlt.movieapp.data.model.Movie
import com.aknrdlt.movieapp.data.model.Movies
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=a87bc70e878ff3134468fb8ee82b360a&language=en-US&page=1

    // https://api.themoviedb.org/3/movie/508943-lucaapi?api_key=a87bc70e878ff3134468fb8ee82b360a&language=en-US

    // https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getMovies(@Query("api_key") key: String): Call<Movies>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") id : Int) : Single<Movie>
}