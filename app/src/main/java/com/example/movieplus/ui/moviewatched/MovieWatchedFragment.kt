package com.example.movieplus.ui.moviewatched

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.movieplus.R
import com.example.movieplus.common.viewBinding
import com.example.movieplus.databinding.FragmentMovieWatchedBinding

class MovieWatchedFragment : Fragment(R.layout.fragment_movie_watched) {

    private val binding by viewBinding(FragmentMovieWatchedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }
}