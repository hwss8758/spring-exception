package hello.exception.filter

import org.slf4j.LoggerFactory
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

class LogFilter : Filter {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun init(filterConfig: FilterConfig?) {
        log.info("LogFilter.init")
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val requestURI = httpRequest.requestURI
        val uuid = UUID.randomUUID().toString()

        try {
            log.info("REQUEST  [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI)
            chain?.doFilter(request, response)
        } catch (e: Exception) {
            log.info("EXCEPTION [{}]", e.message)
            chain?.doFilter(request, response)
        } finally {
            log.info("RESPONSE [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);
        }
    }

    override fun destroy() {
        log.info("LogFilter.destroy")
    }
}