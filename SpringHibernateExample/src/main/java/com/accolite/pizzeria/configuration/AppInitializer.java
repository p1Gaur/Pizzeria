package com.accolite.pizzeria.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext container) throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ctx.setServletContext(container);

		ServletRegistration.Dynamic servlet = container.addServlet(
				"dispatcher", new DispatcherServlet(ctx));

		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/customer");
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/user");
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/menu");
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/cart");
	}

}
