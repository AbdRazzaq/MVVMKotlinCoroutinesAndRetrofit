package com.example.mvvmkotlincoroutinesandretrofit.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmkotlincoroutinesandretrofit.R
import com.example.mvvmkotlincoroutinesandretrofit.data.network.MainRepository
import com.example.mvvmkotlincoroutinesandretrofit.data.network.RetrofitService
import com.example.mvvmkotlincoroutinesandretrofit.databinding.ActivityMainBinding
import com.example.mvvmkotlincoroutinesandretrofit.ui.adapter.MoviesAdapter
import com.example.mvvmkotlincoroutinesandretrofit.ui.viewmodel.MoviesViewModel
import com.example.mvvmkotlincoroutinesandretrofit.ui.viewmodel.MoviesViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MoviesViewModel
    private val adapter = MoviesAdapter()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MoviesViewModelFactory(mainRepository)).get(MoviesViewModel::class.java)


        viewModel.movieList.observe(this, {
            adapter.setMovies(it)
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllMovies()

    }
}