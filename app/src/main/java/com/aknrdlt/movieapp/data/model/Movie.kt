package com.aknrdlt.movieapp.data.model


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val rating: Double,
)

data class Movies(
    val results: List<Movie>
)

