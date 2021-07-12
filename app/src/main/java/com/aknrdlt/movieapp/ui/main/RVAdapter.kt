package com.aknrdlt.movieapp.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aknrdlt.movieapp.R
import com.aknrdlt.movieapp.data.api.POSTER_BASE_URL
import com.aknrdlt.movieapp.data.model.Movie
import com.aknrdlt.movieapp.ui.detail.DetailActivity
import com.bumptech.glide.Glide


class RVAdapter(val movies: List<Movie>, val context: Context): RecyclerView.Adapter<RVAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        return holder.bind(movies[position])
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photo: ImageView = itemView.findViewById(R.id.iv_item)
        private val title: TextView = itemView.findViewById(R.id.tv_name)
        private val released: TextView = itemView.findViewById(R.id.tv_released)
        private val rating: TextView = itemView.findViewById(R.id.tv_rating)

        fun bind(movie: Movie) {
            Glide.with(itemView.context)
                .load(POSTER_BASE_URL + movie.posterPath)
                .into(photo)
            title.text = movie.title
            released.text = movie.releaseDate
            rating.text = movie.rating.toString()
        }

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val clickedDataItem: Movie = movies[pos]

                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("id", clickedDataItem.id)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }
    }
}