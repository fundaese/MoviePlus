package com.example.movieplus.data.source

import android.graphics.Bitmap
import com.example.movieplus.data.model.Movie

object Database {

    private val movieList = mutableListOf<Movie>()
    private val toWatchMovieList = mutableListOf<Movie>()
    private val watchedMovieList = mutableListOf<Movie>()

    fun getMovie(movie: Movie) = movieList.indexOf(movie)
    fun getWatchedMovie() = watchedMovieList
    fun getWatchMovie() = toWatchMovieList
    fun getMovieList() = movieList

    fun addMovie(imgMovie: Bitmap, title: String, desc: String, movieCategory: String, isWatched: Boolean) {
        movieList.add(
            Movie(
                id = (movieList.lastOrNull()?.id ?: 1) + 1,
                imgMovie = imgMovie,
                title = title,
                desc = desc,
                movieCategory = movieCategory,
                isWatched = isWatched
            )
        )
    }


    fun removeMovie(movie: Movie) = movieList.remove(movie)

    fun updateMovie(index: Int, movie: Movie) {
        movieList.set(index, movie)
        isWatched()
    }

    fun isWatched() {
        watchedMovieList.clear()
        toWatchMovieList.clear()
        movieList.forEach {
            if (it.isWatched) {
                watchedMovieList.add(it)
            } else {
                toWatchMovieList.add(it)
            }
        }
    }

}