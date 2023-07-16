package a.gleb.clientapp.configuration;

import a.gleb.clientapp.configuration.properties.ClientAppConfigurationProperties;
import a.gleb.clientapp.feign.ResourceServiceFeignClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableFeignClients(clients = {
        ResourceServiceFeignClient.class
})
@EnableScheduling
@EnableConfigurationProperties(ClientAppConfigurationProperties.class)
public class ClientAppConfig {
}
