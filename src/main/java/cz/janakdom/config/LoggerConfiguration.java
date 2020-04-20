package cz.janakdom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {
    @Bean
    public Logger log() {
        return LoggerFactory.getLogger("cz.janakdom.dackend.logger");
    }
}
