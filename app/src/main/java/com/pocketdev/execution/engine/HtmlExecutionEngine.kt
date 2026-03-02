package com.pocketdev.execution.engine

import com.pocketdev.execution.ErrorType
import com.pocketdev.execution.ExecutionResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HtmlExecutionEngine {

    private var executionJob: Job? = null
    private val timeoutMs = 10000L // 10 seconds

    fun execute(code: String): Flow<ExecutionResult> = flow {
        emit(ExecutionResult.Running)
        
        val startTime = System.currentTimeMillis()
        
        try {
            withTimeout(timeoutMs) {
                // Validate HTML
                val validatedHtml = validateAndWrapHtml(code)
                
                // Emit the HTML content for rendering in WebView
                emit(ExecutionResult.HtmlOutput(validatedHtml))
                
                val executionTime = System.currentTimeMillis() - startTime
                emit(ExecutionResult.Success(executionTime))
            }
        } catch (e: TimeoutCancellationException) {
            emit(ExecutionResult.Error("Rendering timed out after ${timeoutMs / 1000} seconds", ErrorType.TIMEOUT))
        } catch (e: Exception) {
            emit(ExecutionResult.Error(e.message ?: "Unknown HTML error", ErrorType.UNKNOWN))
        }
    }.flowOn(Dispatchers.Default)

    fun stop() {
        executionJob?.cancel()
    }

    private fun validateAndWrapHtml(html: String): String {
        // If the HTML already has a doctype or html tag, use it as is
        if (html.contains("<!DOCTYPE", ignoreCase = true) || 
            html.contains("<html", ignoreCase = true)) {
            return html
        }
        
        // Wrap in a basic HTML structure if needed
        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html>")
            appendLine("<head>")
            appendLine("    <meta charset=\"UTF-8\">")
            appendLine("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
            appendLine("    <style>")
            appendLine("        body { font-family: system-ui, sans-serif; padding: 16px; margin: 0; background: white; color: black; }")
            appendLine("        * { box-sizing: border-box; }")
            appendLine("    </style>")
            appendLine("</head>")
            appendLine("<body>")
            appendLine(html)
            appendLine("</body>")
            appendLine("</html>")
        }
    }

    companion object {
        fun getDefaultHtmlTemplate(): String {
            return """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Page</title>
    <style>
        body {
            font-family: system-ui, -apple-system, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background: #f5f5f5;
        }
        h1 {
            color: #333;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Hello, World!</h1>
        <p>Welcome to your HTML page.</p>
        <button onclick="sayHello()">Click Me</button>
        <p id="message"></p>
    </div>
    
    <script>
        function sayHello() {
            document.getElementById('message').textContent = 'Hello from JavaScript!';
        }
    </script>
</body>
</html>"""
        }
    }
}
