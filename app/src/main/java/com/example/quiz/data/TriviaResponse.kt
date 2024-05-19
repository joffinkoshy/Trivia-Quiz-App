package com.example.quiz.data

    data class TriviaResponse(
        val results: List<Selection>

    ) {
        infix fun by(lazy: Lazy<TriviaResponse>) {

        }
    }
