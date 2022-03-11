package hello.exception.api

import hello.exception.exception.UserException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    fun getMember(@PathVariable id: String): MemberDto {

        if (id == "ex") throw RuntimeException("bad user")

        if (id == "bad") throw IllegalArgumentException("illegal argument!!")

        if (id == "user-ex") throw UserException("사용자 오류!!")

        return MemberDto(id, "hello $id")
    }
}

data class MemberDto(
    val memberId: String,
    val name: String
)