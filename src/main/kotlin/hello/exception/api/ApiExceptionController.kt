package hello.exception.api

import hello.exception.exception.BadRequestException
import hello.exception.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    fun getMember(@PathVariable id: String): MemberDto {

        if (id == "ex") throw RuntimeException("bad user")

        if (id == "bad") throw IllegalArgumentException("illegal argument!!")

        if (id == "user-ex") throw UserException("사용자 오류!!")

        return MemberDto(id, "hello $id")
    }

    @GetMapping("/api/response-status-ex1")
    fun responseStatusEx1() {
        throw BadRequestException()
    }

    @GetMapping("/api/response-status-ex2")
    fun responseStatusEx2() {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 에러!", IllegalArgumentException())
    }

    @GetMapping("/api/default-handler-ex")
    fun defaultException(@RequestParam data: Int): String = "ok"
}

data class MemberDto(
    val memberId: String,
    val name: String
)