package org.max;


import java.util.concurrent.TimeUnit;

import io.undertow.Undertow.Builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.boot.web.servlet.ErrorPage;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class);
	}
   
	@Bean
	public UndertowEmbeddedServletContainerFactory servletContainer(){
		
		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
		
		factory.setPort(18989);
		factory.setSessionTimeout(12,TimeUnit.MINUTES);
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/WEB-INF/views/error/401.jsp");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/views/error/404.jsp");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/WEB-INF/views/error/500.jsp");
        factory.addErrorPages(error401Page, error404Page, error500Page);
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("html", "text/html;charset=UTF-8");
        mappings.add("json", "application/json;charset=UTF-8");
        mappings.add("ico", "image/vnd.microsoft.icon");
        factory.setMimeMappings(mappings);
		return factory;
	}
}
