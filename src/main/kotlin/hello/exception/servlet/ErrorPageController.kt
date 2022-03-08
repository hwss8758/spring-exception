package hello.exception.servlet

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
class ErrorPageController {

    private val log = LoggerFactory.getLogger(this::class.java)

    companion object {
        const val ERROR_EXCEPTION = "javax.servlet.error.exception"
        const val ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type"
        const val ERROR_MESSAGE = "javax.servlet.error.message"
        const val ERROR_REQUEST_URI = "javax.servlet.error.request_uri"
        const val ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name"
        const val ERROR_STATUS_CODE = "javax.servlet.error.status_code"
    }


    @RequestMapping("/error-page/404")
    fun errorPage404(request: HttpServletRequest, response: HttpServletResponse): String {
        log.info("ErrorPageController.errorPage404")
        printErrorInfo(request)
        return "error-page/404"
    }

    @RequestMapping("/error-page/500")
    fun errorPage500(request: HttpServletRequest, response: HttpServletResponse): String {
        log.info("ErrorPageController.errorPage500")
        printErrorInfo(request)
        return "error-page/500"
    }

    @RequestMapping("/error-page/500", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun errorPage500Api(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Map<String, Any>> {
        log.info("ErrorPageController.errorPage500Api")

        val ex = request.getAttribute(ERROR_EXCEPTION) as Exception

        val result = mapOf<String, Any>(
            "status" to request.getAttribute(ERROR_STATUS_CODE),
            "message" to ex.message!!
        )

        val statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int

        return ResponseEntity(result, HttpStatus.valueOf(statusCode))
    }

    private fun printErrorInfo(request: HttpServletRequest) {
        log.info("ERROR_EXCEPTION: ex= {}", request.getAttribute(ERROR_EXCEPTION))
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE))
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE))
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI))
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME))
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE))
        log.info("dispatchType={}", request.dispatcherType)
    }
}