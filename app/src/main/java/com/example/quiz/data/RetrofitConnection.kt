package com.example.quiz.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {


    object RetrofitInstance {
        fun getInstance(): Retrofit {
            val BASEURL="https://opentdb.com/api.php"
            return Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }
}