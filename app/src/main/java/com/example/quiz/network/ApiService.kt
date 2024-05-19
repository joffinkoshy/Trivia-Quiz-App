package com.example.quiz.network

import com.example.quiz.data.TriviaResponse
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("/api.php")
     fun getQuestions(

        @Query("amount") amount: Int,

    ):Response<TriviaResponse>


}


