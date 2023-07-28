package com.example.movieplus.ui.movies

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.movieplus.R
import com.example.movieplus.common.datePicker
import com.example.movieplus.common.showTimePicker
import com.example.movieplus.databinding.FragmentMoviesBinding
import com.example.movieplus.common.viewBinding
import com.example.movieplus.data.model.Movie
import com.example.movieplus.data.source.Database
import com.example.movieplus.databinding.AddAlertDialogDesignBinding

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding(FragmentMoviesBinding::bind)
    private val movieAdapter = MoviesAdapter(::onMovieClick)

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
        binding.rvMovies.adapter = movieAdapter
        movieAdapter.updateList(Database.getMovieList())
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val binding = AddAlertDialogDesignBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()

        var selectedDate =  ""
        val saveTypeList = listOf("Action", "Science-Finction")
        var selectedSaveType = ""
        val saveTypeAdapter = ArrayAdapter(
            requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, saveTypeList
        )

        with(binding) {

            val calendar = Calendar.getInstance()

            actSaveType.setAdapter(saveTypeAdapter)

            actSaveType.setOnItemClickListener{ _, _, position, _ ->
                selectedSaveType = saveTypeList[position]
            }


            btnAdd.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()

                if (title.isNotEmpty() && desc.isNotEmpty() && selectedSaveType.isNotEmpty()) {
                    Database.addMovie(title, desc, selectedDate, selectedSaveType)
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

    private fun onMovieClick(desc:String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }
}