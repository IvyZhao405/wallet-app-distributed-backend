package dev.ivyzhao.wallet.merchants.conf;

import dev.ivyzhao.wallet.merchants.security.AuthCheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Component
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("Add interceptor: AuthCheckInterceptor");
        registry.addInterceptor(new AuthCheckInterceptor()).addPathPatterns(
                "/merchants/**"
        ).order(0);
    }
}
