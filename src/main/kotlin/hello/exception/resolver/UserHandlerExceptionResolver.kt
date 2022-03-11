package hello.exception.resolver

import com.fasterxml.jackson.databind.ObjectMapper
import hello.exception.exception.UserException
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserHandlerExceptionResolver : HandlerExceptionResolver {

    private val objectMapper: ObjectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView? {
        try {
            if (ex is UserException) {

                log.info("UserException resolver to 400")

                val acceptHeader = request.getHeader("accept")
                response.status = HttpServletResponse.SC_BAD_REQUEST

                if ("application/json" == acceptHeader) {

                    val errorResult = mapOf<String, Any?>(
                        "ex" to ex.javaClass,
                        "message" to ex.message
                    )

                    val result = objectMapper.writeValueAsString(errorResult)

                    response.apply {
                        contentType = "application/json"
                        characterEncoding = "utf-8"
                        writer.write(result)
                    }

                    return ModelAndView()
                } else {
                    // text/html
                    return ModelAndView("error/500")
                }

            }
        } catch (e: IOException) {
            log.error("resolver ex", e)
        }

        return null
    }
}