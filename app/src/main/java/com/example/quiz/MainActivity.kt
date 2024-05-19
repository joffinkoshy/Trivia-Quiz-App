package com.example.quiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.data.TriviaResponse
import com.example.quiz.data.TriviaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quiz.ui.theme.QuizTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TriviaViewModel>()
    private val REP by lazy{viewModel._response.value!!}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            QuizTheme {


                if(REP.results.size!=0){
                    Log.d("Fetched","If cond  ${REP.results.size}")
                    TriviaQuizApp(REP)}
                else{

                   Text(text = "HELLO WORLD")

                }


            }

    }}
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
 fun TriviaQuizApp(rep:TriviaResponse) {
     val viewModel=TriviaViewModel()

        var currentQuestionIndex by remember { mutableIntStateOf(0) }
        val currentQuestion = rep.results[currentQuestionIndex].quiz

        Text(
            "Trivia Quiz App", fontSize = 30.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, 14.dp),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier.fillMaxHeight(0.05f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_indicator),
                contentDescription = "Image card",
                modifier = Modifier
                    .fillMaxSize()
                    .offset(0.dp, 40.dp)
            )
        }


        Column(


            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, 126.dp)
                .padding(36.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            ElevatedCard(
                shape = RoundedCornerShape(26.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(Color(0xFF8D23C1)),
                elevation = CardDefaults.cardElevation(20.dp)
            ) {
                Text(
                    text = currentQuestion!!.question,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(33.dp, 33.dp),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            var selectedAnswer by remember { mutableStateOf("") }
            var selectedAnswerIndex by remember { mutableIntStateOf(-1) }
            var choseColor: Color by remember { mutableStateOf(Color.LightGray) }
            var chosewidth: Int by remember { mutableIntStateOf(0) }




            (currentQuestion!!.incorrect_answers + currentQuestion!!.correctAnswer).shuffled()
                .forEachIndexed { index, answer ->
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedCard(border = BorderStroke(
                        width = (if (selectedAnswerIndex == index) chosewidth else 0).dp,
                        color = if (selectedAnswerIndex == index) choseColor else Color.LightGray
                    ),
                        onClick = {
                            selectedAnswerIndex = index
                            selectedAnswer = answer

                            if (selectedAnswerIndex != -1) {

                                choseColor = viewModel.changeButtonColor(
                                    selectedAnswer,
                                    currentQuestion.correctAnswer
                                )
                                chosewidth = viewModel.changeButtonWidth(
                                    selectedAnswer,
                                    currentQuestion.correctAnswer
                                )
                                CoroutineScope(Dispatchers.IO).launch {
                                    delay(800)
                                    currentQuestionIndex++


                                    if (currentQuestionIndex >= rep.results.size) {
                                        currentQuestionIndex = 0 // Restart the quiz
                                    }
                                    selectedAnswerIndex = -1 // Reset the selected answer
                                }
                            }


                        }) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Card {
                                RadioButton(
                                    selected = selectedAnswerIndex == index,
                                    onClick = {
                                        selectedAnswerIndex = index

                                        if (selectedAnswerIndex != -1) {

                                            choseColor = viewModel.changeButtonColor(
                                                selectedAnswer,
                                                currentQuestion.correctAnswer
                                            )
                                            chosewidth = viewModel.changeButtonWidth(
                                                selectedAnswer,
                                                currentQuestion.correctAnswer
                                            )
                                            CoroutineScope(Dispatchers.IO).launch {
                                                delay(800)
                                                currentQuestionIndex++


                                                if (currentQuestionIndex >= rep.results.size) {
                                                    currentQuestionIndex = 0 // Restart the quiz
                                                }
                                                selectedAnswerIndex =
                                                    -1 // Reset the selected answer
                                            }
                                        }


                                    },
                                    colors = RadioButtonDefaults.colors(),
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = answer, fontWeight = FontWeight.Bold)
                        }
                    }
                }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }




