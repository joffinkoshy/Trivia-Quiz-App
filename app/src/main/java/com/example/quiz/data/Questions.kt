package com.example.quiz.data

data class Questions(
    val question:String,
    val correctAnswer: String,
    val incorrect_answers:List<String>
) {
}