package com.example.androidassignment.model

data class ApiResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String?
)

data class Message(
    val role: String,
    val content: String
)

data class Content(
    val titles: List<String>,
    val description: String
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

