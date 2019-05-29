package ru.sber.alex.minibank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.sber.alex.minibank.layers.services.ClientDetailServiceImpl;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("login").setViewName("login");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;

    @Bean
    @Qualifier("myUserDetailServiceImpl")
    public UserDetailsService getUserDetailService(){
        return new ClientDetailServiceImpl();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public DataSource dataSource() {
        final Map<String, String> env = System.getenv();
        return DataSourceBuilder
                .create()
                .type(MysqlDataSource.class)
                .url(dbUrl)
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }
    /*
    @Bean(destroyMethod = "close")
    public Client restClient() {
        return ClientBuilder.newBuilder().register(new JacksonJsonProvider(objectMapper)).build();
    }

    @Configuration
    public static class JerseyConfig extends ResourceConfig {

        public JerseyConfig() {
            register(ServiceController.class);
            register(new LoggingFeature(Logger.getLogger("http-endpoints"), Level.INFO, LoggingFeature.Verbosity.PAYLOAD_TEXT, null));
        }
    }
    */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Function<String, String> serviceUrlProvider() {
        return serviceName -> System.getenv().get(serviceName + ".url");
    }

    @PostConstruct
    public void setupObjectMapper() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

}
