package com.example.androidassignment.model

data class ApiResponse(val choices: List<Choice>)

data class Choice(
    val index: Int,
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)

data class Content(
    val titles: List<String>,
    val description: String
)

