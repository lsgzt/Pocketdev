package com.pocketdev.execution

import com.pocketdev.execution.engine.HtmlExecutionEngine
import com.pocketdev.execution.engine.JavaScriptExecutionEngine
import com.pocketdev.execution.engine.PythonExecutionEngine
import com.pocketdev.model.ProgrammingLanguage
import kotlinx.coroutines.flow.Flow

class ExecutionManager {

    private val pythonEngine = PythonExecutionEngine()
    private val jsEngine = JavaScriptExecutionEngine()
    private val htmlEngine = HtmlExecutionEngine()

    fun canExecute(language: ProgrammingLanguage): Boolean {
        return language.isExecutable
    }

    fun execute(code: String, language: ProgrammingLanguage): Flow<ExecutionResult> {
        return when (language) {
            ProgrammingLanguage.PYTHON -> pythonEngine.execute(code)
            ProgrammingLanguage.JAVASCRIPT -> jsEngine.execute(code)
            ProgrammingLanguage.HTML -> htmlEngine.execute(code)
            else -> throw IllegalArgumentException("Language $language is not executable")
        }
    }

    fun stopExecution(language: ProgrammingLanguage) {
        when (language) {
            ProgrammingLanguage.PYTHON -> pythonEngine.stop()
            ProgrammingLanguage.JAVASCRIPT -> jsEngine.stop()
            ProgrammingLanguage.HTML -> htmlEngine.stop()
            else -> {}
        }
    }
}

sealed class ExecutionResult {
    data class Output(val text: String, val type: OutputType = OutputType.STDOUT) : ExecutionResult()
    data class Error(val message: String, val type: ErrorType = ErrorType.RUNTIME) : ExecutionResult()
    data class Success(val executionTimeMs: Long) : ExecutionResult()
    data class HtmlOutput(val htmlContent: String) : ExecutionResult()
    object Running : ExecutionResult()
    object Cancelled : ExecutionResult()
}

enum class OutputType {
    STDOUT,
    STDERR,
    LOG
}

enum class ErrorType {
    SYNTAX,
    RUNTIME,
    TIMEOUT,
    MEMORY,
    UNKNOWN
}
