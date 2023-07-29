package com.example.movieplus.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieplus.data.model.Movie
import com.example.movieplus.data.source.Database
import com.example.movieplus.databinding.ItemMovieBinding

class MoviesAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val onCheckboxClick: (Movie) -> Unit
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
                ivMovie.setImageBitmap(movie.imgMovie)
                tvTitle.text = movie.title
                cbMovie.isChecked = movie.isWatched
                cbMovie.setOnClickListener {
                    var updatedFilm = Movie(
                        id = movie.id,
                        imgMovie = movie.imgMovie,
                        title = movie.title,
                        desc = movie.desc,
                        isWatched = !movie.isWatched,
                        movieCategory = movie.movieCategory
                    )
                    Database.updateMovie(Database.getMovie(movie), updatedFilm)
                    onCheckboxClick(movie)
                }

                root.setOnClickListener {
                    onMovieClick(movie) // You can use this callback for regular item click if needed
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