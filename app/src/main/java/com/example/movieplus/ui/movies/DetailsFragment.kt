package com.example.movieplus.ui.movies

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieplus.R
import com.example.movieplus.common.viewBinding
import com.example.movieplus.data.model.Movie
import com.example.movieplus.data.source.Database
import com.example.movieplus.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private lateinit var movie: Movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            movie = DetailsFragmentArgs.fromBundle(it).movie
        }

        with(binding) {
            ivDetail.setImageBitmap(movie.imgMovie)
            tvDetailTitle.text = movie.title
            tvDetailDesc.text = movie.desc
            tvDetailCategory.text = movie.movieCategory

            btnDelete.setOnClickListener {
                createDialog()
            }
        }
    }

    fun createDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Movie")
        builder.setMessage("Are you sure you want to delete the movie?")
        val dialog = builder.create()

        builder.setPositiveButton("Delete") { _, _ ->
            Database.removeMovie(movie)
            Database.isWatched()
            dialog.dismiss()

            findNavController().popBackStack()
        }
        builder.setNegativeButton("No") { _, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}