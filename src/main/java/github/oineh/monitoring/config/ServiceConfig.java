package github.oineh.monitoring.config;

import io.github.tcp.network.Monitoring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public Monitoring monitoring() {
        return new Monitoring();
    }
}
