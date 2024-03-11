package kakao.login.config;

import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html;charset-UTF-8");

        registry.viewResolver(resolver);
    }
}
