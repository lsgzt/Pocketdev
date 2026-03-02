package com.pocketdev.execution.engine

import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.pocketdev.execution.ErrorType
import com.pocketdev.execution.ExecutionResult
import com.pocketdev.execution.OutputType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class PythonExecutionEngine {

    private val python: Python = Python.getInstance()
    private var executionJob: Job? = null
    private val timeoutMs = 10000L // 10 seconds

    fun execute(code: String): Flow<ExecutionResult> = flow {
        emit(ExecutionResult.Running)
        
        val startTime = System.currentTimeMillis()
        
        try {
            withTimeout(timeoutMs) {
                // Capture stdout and stderr
                val stdout = ByteArrayOutputStream()
                val stderr = ByteArrayOutputStream()
                
                val oldOut = System.out
                val oldErr = System.err
                
                System.setOut(PrintStream(stdout))
                System.setErr(PrintStream(stderr))
                
                try {
                    // Execute Python code
                    val module = python.getModule("builtins")
                    val compiled = module.callAttr("compile", code, "<string>", "exec")
                    
                    val globals = python.builtins.callAttr("dict")
                    module.callAttr("exec", compiled, globals)
                    
                    // Check for output
                    val output = stdout.toString()
                    val error = stderr.toString()
                    
                    if (output.isNotEmpty()) {
                        emit(ExecutionResult.Output(output, OutputType.STDOUT))
                    }
                    
                    if (error.isNotEmpty()) {
                        emit(ExecutionResult.Output(error, OutputType.STDERR))
                    }
                    
                    val executionTime = System.currentTimeMillis() - startTime
                    emit(ExecutionResult.Success(executionTime))
                    
                } catch (e: PyException) {
                    val errorMessage = e.message ?: "Unknown Python error"
                    emit(ExecutionResult.Error(errorMessage, ErrorType.RUNTIME))
                } finally {
                    System.setOut(oldOut)
                    System.setErr(oldErr)
                }
            }
        } catch (e: TimeoutCancellationException) {
            emit(ExecutionResult.Error("Execution timed out after ${timeoutMs / 1000} seconds", ErrorType.TIMEOUT))
        } catch (e: Exception) {
            emit(ExecutionResult.Error(e.message ?: "Unknown error", ErrorType.UNKNOWN))
        }
    }.flowOn(Dispatchers.IO)

    fun stop() {
        executionJob?.cancel()
    }
}
