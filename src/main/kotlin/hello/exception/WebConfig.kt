package hello.exception

import hello.exception.filter.LogFilter
import hello.exception.resolver.MyHandlerExceptionResolver
import hello.exception.resolver.UserHandlerExceptionResolver
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.DispatcherType
import javax.servlet.Filter

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun logFilter(): FilterRegistrationBean<Filter> {
        return FilterRegistrationBean<Filter>().apply {
            this.filter = LogFilter()
            this.order = 1
            this.addUrlPatterns("/*")
            this.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR)
        }
    }

    override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
        resolvers.add(MyHandlerExceptionResolver())
        resolvers.add(UserHandlerExceptionResolver())
    }
}