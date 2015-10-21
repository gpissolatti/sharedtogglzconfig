package sharedtogglzconfig;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.console.TogglzConsoleServlet;
import org.togglz.servlet.TogglzFilter;

@Configuration
public class SharedTogglzServlet {

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {
    return new ServletRegistrationBean(new TogglzConsoleServlet(), "/togglz/*");
  }

  @Bean
  public TogglzFilter togglzFilter() {
    return new TogglzFilter();
  }

}
