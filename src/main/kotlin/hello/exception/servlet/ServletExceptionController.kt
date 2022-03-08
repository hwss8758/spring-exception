package hello.exception.servlet

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletResponse

@Controller
class ServletExceptionController {

    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/error-ex")
    fun errorEx() {
        log.info("ServletExceptionController.errorEx")
        throw RuntimeException("예외발생!!")
    }

    @GetMapping("error-404")
    fun error404(response: HttpServletResponse) {
        log.info("ServletExceptionController.error404")
        response.sendError(404, "404 error!")
    }

    @GetMapping("error-500")
    fun error500(response: HttpServletResponse) {
        log.info("ServletExceptionController.error500")
        response.sendError(500)
    }
}