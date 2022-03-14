package hello.exception.advice

import hello.exception.api.ErrorResult
import hello.exception.exception.UserException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {

    private val log = LoggerFactory.getLogger(this::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalExHandle(e: IllegalArgumentException): ErrorResult {
        log.error("[exceptionHandler] ex", e)
        return ErrorResult("BAD", e.message!!)
    }

    @ExceptionHandler
    fun userExHandle(e: UserException): ResponseEntity<ErrorResult> {
        log.error("[exceptionHandler] ex", e)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResult("user-ex", e.message!!))
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    fun exHandle(e: Exception): ErrorResult {
        log.error("[exceptionHandler] ex", e)
        return ErrorResult("EX", "내부오류")
    }
}