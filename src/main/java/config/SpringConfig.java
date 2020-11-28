package config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import okhttp3.OkHttpClient;

@EnableWebMvc
@Configuration
@ComponentScan({"com.portfolioTracker"})
public class SpringConfig implements WebMvcConfigurer{

	/**
	 * The following method figures out which view the dispatcher should pick
	 * @return The view resolver	
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver view = new InternalResourceViewResolver();
		view.setPrefix("/WEB-INF/view/");
		view.setSuffix(".jsp");
		return view;
	}

	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public OkHttpClient client() {
		return new OkHttpClient();
	}

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
}
