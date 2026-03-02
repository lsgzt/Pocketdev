package com.pocketdev.data.repository

import com.pocketdev.data.remote.*
import com.pocketdev.model.AiModel
import com.pocketdev.model.ProgrammingLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AiRepository {

    private val apiService = RetrofitClient.groqApiService
    private val maxRetries = 3
    private val retryDelayMs = 1000L

    suspend fun fixBug(code: String, language: ProgrammingLanguage, apiKey: String, model: AiModel): GroqResult<String> {
        val prompt = buildString {
            appendLine("Analyze this ${language.displayName} code and identify any bugs, errors, or issues.")
            appendLine("Provide the corrected code with clear explanations of what was wrong.")
            appendLine()
            appendLine("```${language.fileExtension}")
            appendLine(code)
            appendLine("```")
            appendLine()
            appendLine("Please respond in this format:")
            appendLine("1. ISSUE: Brief description of the problem")
            appendLine("2. EXPLANATION: Detailed explanation of what was wrong")
            appendLine("3. CORRECTED CODE: The fixed code")
        }

        return makeAiRequest(prompt, apiKey, model)
    }

    suspend fun explainCode(code: String, language: ProgrammingLanguage, apiKey: String, model: AiModel): GroqResult<String> {
        val prompt = buildString {
            appendLine("Explain this ${language.displayName} code in simple, beginner-friendly terms.")
            appendLine("Break down what each part does step-by-step.")
            appendLine()
            appendLine("```${language.fileExtension}")
            appendLine(code)
            appendLine("```")
            appendLine()
            appendLine("Please provide:")
            appendLine("1. OVERVIEW: What this code does in one sentence")
            appendLine("2. STEP-BY-STEP: Explain each part of the code")
            appendLine("3. KEY CONCEPTS: Programming concepts used in this code")
        }

        return makeAiRequest(prompt, apiKey, model)
    }

    suspend fun improveCode(code: String, language: ProgrammingLanguage, apiKey: String, model: AiModel): GroqResult<String> {
        val prompt = buildString {
            appendLine("Suggest improvements for this ${language.displayName} code.")
            appendLine("Focus on: best practices, performance optimization, readability, and maintainability.")
            appendLine()
            appendLine("```${language.fileExtension}")
            appendLine(code)
            appendLine("```")
            appendLine()
            appendLine("Please provide:")
            appendLine("1. IMPROVEMENTS LIST: Bullet points of suggested improvements")
            appendLine("2. IMPROVED CODE: The optimized code")
            appendLine("3. EXPLANATION: Why these improvements are better")
        }

        return makeAiRequest(prompt, apiKey, model)
    }

    suspend fun getAutocompleteSuggestion(
        code: String,
        cursorPosition: Int,
        language: ProgrammingLanguage,
        apiKey: String,
        model: AiModel
    ): GroqResult<String> {
        val prompt = buildString {
            appendLine("Complete the code at the cursor position in this ${language.displayName} code.")
            appendLine("Only provide the completion, no explanations.")
            appendLine()
            appendLine("```${language.fileExtension}")
            appendLine(code.substring(0, cursorPosition) + "<CURSOR>" + code.substring(cursorPosition))
            appendLine("```")
        }

        return makeAiRequest(prompt, apiKey, model)
    }

    private suspend fun makeAiRequest(prompt: String, apiKey: String, model: AiModel): GroqResult<String> {
        return withContext(Dispatchers.IO) {
            var lastException: Exception? = null
            
            repeat(maxRetries) { attempt ->
                try {
                    val request = ChatRequest(
                        model = model.modelId,
                        messages = listOf(
                            Message(
                                role = "system",
                                content = "You are a helpful coding assistant. Provide clear, accurate, and beginner-friendly responses."
                            ),
                            Message(
                                role = "user",
                                content = prompt
                            )
                        ),
                        temperature = 0.7,
                        maxTokens = 1024
                    )

                    val response = apiService.chatCompletion(
                        auth = "Bearer $apiKey",
                        request = request
                    )

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.error != null) {
                            return@withContext GroqResult.Error(body.error.message, body.error.code)
                        }
                        
                        val content = body?.choices?.firstOrNull()?.message?.content
                        if (content != null) {
                            return@withContext GroqResult.Success(content)
                        } else {
                            return@withContext GroqResult.Error("Empty response from AI")
                        }
                    } else {
                        val errorCode = response.code()
                        val errorBody = response.errorBody()?.string()
                        
                        return@withContext when (errorCode) {
                            401 -> GroqResult.InvalidKeyError
                            429 -> GroqResult.RateLimitError
                            in 500..599 -> {
                                if (attempt < maxRetries - 1) {
                                    delay(retryDelayMs * (attempt + 1))
                                    return@repeat
                                }
                                GroqResult.Error("Server error: $errorCode", errorCode.toString())
                            }
                            else -> GroqResult.Error("API error: $errorCode - $errorBody", errorCode.toString())
                        }
                    }
                } catch (e: UnknownHostException) {
                    lastException = e
                    return@withContext GroqResult.NetworkError
                } catch (e: SocketTimeoutException) {
                    lastException = e
                    if (attempt < maxRetries - 1) {
                        delay(retryDelayMs * (attempt + 1))
                    }
                } catch (e: IOException) {
                    lastException = e
                    return@withContext GroqResult.NetworkError
                } catch (e: HttpException) {
                    lastException = e
                    if (attempt < maxRetries - 1) {
                        delay(retryDelayMs * (attempt + 1))
                    }
                } catch (e: Exception) {
                    lastException = e
                    if (attempt < maxRetries - 1) {
                        delay(retryDelayMs * (attempt + 1))
                    }
                }
            }
            
            GroqResult.Error("Failed after $maxRetries attempts: ${lastException?.message}")
        }
    }

    fun validateApiKey(apiKey: String): Boolean {
        return apiKey.startsWith("gsk_") && apiKey.length > 20
    }
}
