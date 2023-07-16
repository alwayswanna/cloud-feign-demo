package a.gleb.resourceserver.configuration;

import a.gleb.resourceserver.configuration.properties.ResourceServerConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ResourceServerConfigurationProperties.class)
public class ResourceServerConfig {
}
