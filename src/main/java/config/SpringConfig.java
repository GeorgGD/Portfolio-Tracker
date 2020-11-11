package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.portfolioTracker.api", "com.portfolioTracker.controller"})
public class SpringConfig {
	
}
