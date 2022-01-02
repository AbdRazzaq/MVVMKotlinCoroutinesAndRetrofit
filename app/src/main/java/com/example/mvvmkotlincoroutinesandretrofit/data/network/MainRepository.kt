package com.example.mvvmkotlincoroutinesandretrofit.data.network

class MainRepository constructor(private val retrofitService: RetrofitService ) {
    suspend fun getAllMovies() = retrofitService.getAllMovies()
}