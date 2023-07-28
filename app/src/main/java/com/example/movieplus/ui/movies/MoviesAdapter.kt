package com.example.movieplus.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.movieplus.data.model.Movie
import com.example.movieplus.databinding.ItemMovieBinding

class MoviesAdapter(
    private val onMovieClick: (String) -> Unit
): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val movieList =  mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviesViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) = holder.bind(movieList[position])

    inner class MoviesViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                ivMovie.setImageResource(movie.imgMovie)
                tvTitle.text = movie.title

                root.setOnClickListener {
                   // onMovieClick()
                }
            }
        }
    }

    fun updateList(list: List<Movie>) {
        movieList.clear()
        movieList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    override fun getItemCount() = movieList.size


}