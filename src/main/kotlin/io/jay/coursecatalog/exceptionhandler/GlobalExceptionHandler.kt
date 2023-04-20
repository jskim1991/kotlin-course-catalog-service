package io.jay.coursecatalog.exceptionhandler

import io.jay.coursecatalog.exception.InstructorNotFoundException
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    companion object : KLogging()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<String> {
        logger.error(ex.stackTraceToString())
        val errors = ex.bindingResult.allErrors
            .map { error -> error.defaultMessage!! }
            .sorted()

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.joinToString(", "))
    }

    @ExceptionHandler(InstructorNotFoundException::class)
    fun handleAll(ex: InstructorNotFoundException): ResponseEntity<String> {
        logger.error(ex.stackTraceToString())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleAll(ex: RuntimeException): ResponseEntity<String> {
        logger.error(ex.stackTraceToString())
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.message)
    }

}