package com.example.movieplus.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.movieplus.R
import com.example.movieplus.common.viewBinding
import com.example.movieplus.data.adapter.MoviesAdapter
import com.example.movieplus.data.model.Movie
import com.example.movieplus.data.source.Database
import com.example.movieplus.databinding.FragmentMovieWatchedBinding

class MovieWatchedFragment : Fragment(R.layout.fragment_movie_watched) {

    private val binding by viewBinding(FragmentMovieWatchedBinding::bind)
    private val watchedAdapter = MoviesAdapter(::onMovieClick, ::onCheckboxClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setData()
        }
    }

    fun setData() {
        Database.isWatched()
        binding.rvWatchedMovies.adapter = watchedAdapter
        watchedAdapter.updateList(Database.getWatchedMovie())
    }

    private fun onMovieClick(movie: Movie) {
        val action = MovieWatchedFragmentDirections.actionMovieWatchedFragmentToDetailsFragment(movie)
        Navigation.findNavController(binding.root).navigate(action)
    }

    private fun onCheckboxClick(movie: Movie) {
        watchedAdapter.updateList(Database.getWatchedMovie())
    }
}