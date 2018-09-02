package club.gorpg.helper.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@ComponentScan(basePackages = "club.gorpg")
@ServletComponentScan(basePackages = "club.gorpg")
public class Application {

	@Value("${game.base.path}")
	private String basePath;

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 将所有/static/** 访问都映射到classpath:/static/ 目录下
		registry.addResourceHandler("/mv/**").addResourceLocations(basePath);
	}

	@Autowired
	public static void main(String[] args) {
		// try {
		SpringApplication.run(Application.class, args);
		// } catch (SilentExitException e) {
		// // DO Nothing
		// }
	}
}
