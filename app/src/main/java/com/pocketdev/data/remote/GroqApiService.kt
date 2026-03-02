package com.pocketdev.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GroqApiService {
    
    @POST("chat/completions")
    suspend fun chatCompletion(
        @Header("Authorization") auth: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}

data class ChatRequest(
    val model: String = "llama-3.3-70b-versatile",
    val messages: List<Message>,
    val temperature: Double = 0.7,
    @SerializedName("max_tokens")
    val maxTokens: Int = 1024
)

data class Message(
    val role: String, // "system", "user", or "assistant"
    val content: String
)

data class ChatResponse(
    val id: String,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage? = null,
    val error: GroqError? = null
)

data class Choice(
    val index: Int,
    val message: Message,
    @SerializedName("finish_reason")
    val finishReason: String? = null
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    @SerializedName("total_tokens")
    val totalTokens: Int
)

data class GroqError(
    val message: String,
    val type: String,
    val code: String? = null
)

sealed class GroqResult<out T> {
    data class Success<T>(val data: T) : GroqResult<T>()
    data class Error(val message: String, val code: String? = null) : GroqResult<Nothing>()
    object NetworkError : GroqResult<Nothing>()
    object RateLimitError : GroqResult<Nothing>()
    object InvalidKeyError : GroqResult<Nothing>()
}
