package com.pocketdev.execution.engine

import com.pocketdev.execution.ErrorType
import com.pocketdev.execution.ExecutionResult
import com.pocketdev.execution.OutputType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.mozilla.javascript.*
import java.io.StringWriter

class JavaScriptExecutionEngine {

    private var executionJob: Job? = null
    private val timeoutMs = 10000L // 10 seconds

    fun execute(code: String): Flow<ExecutionResult> = flow {
        emit(ExecutionResult.Running)
        
        val startTime = System.currentTimeMillis()
        val context = Context.enter()
        
        try {
            withTimeout(timeoutMs) {
                // Configure Rhino
                context.optimizationLevel = -1 // Interpreted mode for better compatibility
                context.languageVersion = Context.VERSION_ES6
                
                val scope = context.initStandardObjects()
                
                // Create a custom console object to capture output
                val outputBuilder = StringBuilder()
                val console = context.newObject(scope)
                
                val logFunction = object : BaseFunction() {
                    override fun call(cx: Context, scope: Scriptable, thisObj: Scriptable, args: Array<Any>): Any? {
                        val message = args.joinToString(" ") { Context.toString(it) }
                        outputBuilder.appendLine(message)
                        return Undefined.instance
                    }
                }
                
                console.put("log", console, logFunction)
                console.put("error", console, logFunction)
                console.put("warn", console, logFunction)
                console.put("info", console, logFunction)
                scope.put("console", scope, console)
                
                // Also capture print() for compatibility
                val printFunction = object : BaseFunction() {
                    override fun call(cx: Context, scope: Scriptable, thisObj: Scriptable, args: Array<Any>): Any? {
                        val message = args.joinToString(" ") { Context.toString(it) }
                        outputBuilder.appendLine(message)
                        return Undefined.instance
                    }
                }
                scope.put("print", scope, printFunction)
                
                try {
                    // Execute JavaScript code
                    val result = context.evaluateString(scope, code, "<script>", 1, null)
                    
                    // Emit any console output
                    if (outputBuilder.isNotEmpty()) {
                        emit(ExecutionResult.Output(outputBuilder.toString().trim(), OutputType.LOG))
                    }
                    
                    // If there's a return value and it's not undefined, emit it
                    if (result != Undefined.instance && result != null) {
                        val resultStr = Context.toString(result)
                        if (resultStr.isNotEmpty() && resultStr != "undefined") {
                            emit(ExecutionResult.Output(resultStr, OutputType.STDOUT))
                        }
                    }
                    
                    val executionTime = System.currentTimeMillis() - startTime
                    emit(ExecutionResult.Success(executionTime))
                    
                } catch (e: RhinoException) {
                    val errorMessage = buildString {
                        appendLine("${e.name}: ${e.message}")
                        if (e.lineNumber() > 0) {
                            appendLine("    at line ${e.lineNumber()}")
                        }
                        if (e.lineSource() != null) {
                            appendLine("    ${e.lineSource()}")
                        }
                    }
                    emit(ExecutionResult.Error(errorMessage.trim(), ErrorType.RUNTIME))
                }
            }
        } catch (e: TimeoutCancellationException) {
            emit(ExecutionResult.Error("Execution timed out after ${timeoutMs / 1000} seconds", ErrorType.TIMEOUT))
        } catch (e: Exception) {
            emit(ExecutionResult.Error(e.message ?: "Unknown JavaScript error", ErrorType.UNKNOWN))
        } finally {
            Context.exit()
        }
    }.flowOn(Dispatchers.Default)

    fun stop() {
        executionJob?.cancel()
        Context.exit()
    }
}
