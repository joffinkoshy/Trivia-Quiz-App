package com.example.quiz.data

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.quiz.TriviaQuizApp
import com.example.quiz.network.API
import com.example.quiz.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.internal.EMPTY_RESPONSE

class TriviaViewModel: ViewModel()

{
     var choseColor:Color by mutableStateOf(Color.LightGray)
    var widthchose:Int by   mutableStateOf(1)
    private set
    fun changeButtonColor(selectedAnswer:String,Answer:String):Color{
        if(selectedAnswer==Answer){
            choseColor=Color.Green
        }
        else{
            choseColor=Color.Red
        }

        return choseColor
    }
    fun changeButtonWidth(selectedAnswer:String,Answer: String):Int{
        if(selectedAnswer==Answer){
            widthchose=2
        }
        else{
            widthchose=2
        }

        return widthchose

    }

    var service = API()
    var _response = MutableLiveData<TriviaResponse>()

    val questions: LiveData<TriviaResponse>
        get() = _response

    fun getQuestions(
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            var response = service.getQuestions()
            _response.postValue(response)
            questions.value

        }

    }

}