package com.aknrdlt.movieapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.aknrdlt.movieapp.R
import com.aknrdlt.movieapp.data.api.MovieDBObject
import com.aknrdlt.movieapp.data.model.Movies
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "News"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = MovieDBObject.getClient(this)
        val call = request.getMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful){
                    progress_bar.visibility = View.GONE

                    initViews(response)
                    handler = Handler()

                    main_content.setOnRefreshListener {
                        runnable = Runnable {
                            initViews(response)
                            main_content.isRefreshing = false
                        }
                        handler.postDelayed(
                            runnable, 1000.toLong()
                        )
                    }
                    main_content.setColorSchemeResources(
                        android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun initViews(response: Response<Movies>){
        rv_movie_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = RVAdapter(response.body()!!.results, this@MainActivity)
        }
    }

}