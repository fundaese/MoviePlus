package com.example.movieplus.ui.movies

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.movieplus.R
import com.example.movieplus.common.viewBinding
import com.example.movieplus.data.adapter.MoviesAdapter
import com.example.movieplus.data.model.Movie
import com.example.movieplus.data.source.Database
import com.example.movieplus.databinding.AddAlertDialogDesignBinding
import com.example.movieplus.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding(FragmentMoviesBinding::bind)
    private val movieAdapter = MoviesAdapter(::onMovieClick, ::onCheckboxClick)
    private lateinit var addAlertDialogDesignBinding: AddAlertDialogDesignBinding

    private var selectedImage: Bitmap? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
        uri?.let {
            selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, it))
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
            }
            addAlertDialogDesignBinding.ivMovieDetail.setImageBitmap(selectedImage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData(Database.getMovieList())

        with(binding) {
            fabAdd.setOnClickListener {
                showAddDialog()
            }
        }
    }

    private fun setData(list: List<Movie>) {
        Database.isWatched()
        binding.rvMovies.adapter = movieAdapter
        movieAdapter.updateList(Database.getWatchMovie())
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        addAlertDialogDesignBinding = AddAlertDialogDesignBinding.inflate(layoutInflater)
        builder.setView(addAlertDialogDesignBinding.root)
        val dialog = builder.create()

        val movieCategoryList = listOf("Action", "Comedy", "Adventure", "Animation", "Drama")
        var selectedCategory = ""
        val movieCategoryAdapter = ArrayAdapter(
            requireContext(),
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            movieCategoryList
        )

        with(addAlertDialogDesignBinding) {
            actCategory.setAdapter(movieCategoryAdapter)

            selectedImage = null

            actCategory.setOnItemClickListener{ _, _, position, _ ->
                selectedCategory = movieCategoryList[position]
            }

            ivMovieDetail.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
              //  ivMovieDetail.setImageBitmap(selectedImage)
            }

            btnAdd.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()

                if (selectedImage != null && title.isNotEmpty() && selectedCategory.isNotEmpty()) {
                    Database.addMovie(selectedImage!!, title, desc, selectedCategory, false)
                    setData(Database.getMovieList())
                    dialog.dismiss()
                }
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun onMovieClick(movie: Movie) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movie)
        Navigation.findNavController(binding.root).navigate(action)
    }

    private fun onCheckboxClick(movie: Movie) {
         Database.removeMovie(movie)
         movieAdapter.updateList(Database.getWatchMovie())
    }
}