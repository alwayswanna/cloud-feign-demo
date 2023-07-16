package a.gleb.resourceserver.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("resource-server")
public class ResourceServerConfigurationProperties {

    private int maxUserMessageLength = 10;
}
