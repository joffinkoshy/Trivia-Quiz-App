package com.example.quiz.network

import android.util.Log
import com.example.quiz.data.TriviaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import com.example.quiz.data.RetrofitConnection

class API {


        private val retrofit = RetrofitConnection.RetrofitInstance.getInstance()

        suspend fun getQuestions(
        ):TriviaResponse {

            return withContext(Dispatchers.IO) {
                var response =
                    retrofit.create(ApiService::class.java).getQuestions(amount = 10)

                Log.e("xdd", response.toString() )
                response.body()!!
            }
        }

    }
