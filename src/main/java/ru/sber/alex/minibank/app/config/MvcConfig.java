package ru.sber.alex.minibank.app.config;

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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.sber.alex.minibank.businesslogic.services.ClientDetailService;
import ru.sber.alex.minibank.data.jparepository.ClientRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

/**
 * Главный конфиг MVC-приложения.
 * Здесь указываются статические ресурсы приложения, настройки БД,
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;

    private final ObjectMapper objectMapper;

    public MvcConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    @Qualifier("myUserDetailServiceImpl")
    public UserDetailsService getUserDetailService(){
        return new ClientDetailService();
    }

    /**
     * Добавляет обработчики ресурсов.
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * Создает бин DataSource, при настройке указывается класс источника данных (СУБД), адрес и параметры доступа
     * @return настроенный DataSource
     */
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

    @PostConstruct
    public void setupObjectMapper() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    /*
    Оставляю здесь - расскоментирую при форке на микросервисную архитектуру
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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Function<String, String> serviceUrlProvider() {
        return serviceName -> System.getenv().get(serviceName + ".url");
    }
    */
}
