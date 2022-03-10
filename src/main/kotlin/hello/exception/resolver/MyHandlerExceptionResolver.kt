package hello.exception.resolver

import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MyHandlerExceptionResolver : HandlerExceptionResolver {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView? {
        try {
            if (ex is IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400")
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.message)
                return ModelAndView()
            }
        } catch (e: IOException) {
            log.error("resolver ex", e)
        }

        return null
    }
}