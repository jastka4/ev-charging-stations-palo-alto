package org.jastka4.javaapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                                     final Object handler) throws Exception {
                if (handler instanceof HandlerMethod) {
                    final HandlerMethod handlerMethod = (HandlerMethod) handler;
                    final Method method = handlerMethod.getMethod();
                    log.info("{} - {} - method '{}' on controller '{}'",
                            request.getMethod(), request.getRequestURI(), method.getName(),
                            handlerMethod.getBean().getClass()
                    );
                }
                return true;
            }
        });
    }
}
