package com.example.movieplus.data.source

import com.example.movieplus.data.model.Movie

object Database {

    private val movieList = mutableListOf<Movie>()

    fun getMovieList() = movieList

    fun addMovie(imgMovie: Int, title: String, date: String, saveType: String) {
        movieList.add(
            Movie(
                id = (movieList.lastOrNull()?.id ?: 1) + 1,
                imgMovie = imgMovie,
                title = title,
                date = date,
                saveType = saveType
            )
        )
    }

    fun removeMovie(movie: Movie) = movieList.remove(movie)
}