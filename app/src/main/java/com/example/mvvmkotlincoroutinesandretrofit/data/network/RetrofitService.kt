package com.example.mvvmkotlincoroutinesandretrofit.data.network


import com.example.mvvmkotlincoroutinesandretrofit.data.model.MovieModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("movielist.json")
    suspend fun getAllMovies() : Response<List<MovieModel>>

    companion object{
        var retrofitService : RetrofitService? = null
        fun getInstance(): RetrofitService{
            if(retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }


}