package ru.otus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig implements WebMvcConfigurer {

        @Bean
        public SpringResourceTemplateResolver templateResolver() {
            SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
            templateResolver.setApplicationContext(applicationContext);
            templateResolver.setPrefix("classpath:/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode(TemplateMode.HTML);
            templateResolver.setCacheable(true);
            return templateResolver;
        }

        @Autowired
        private ApplicationContext applicationContext;

        @Bean
        public SpringTemplateEngine templateEngine() {
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver());
            templateEngine.setEnableSpringELCompiler(true);
            return templateEngine;
        }
    }
