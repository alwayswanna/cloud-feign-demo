package a.gleb.clientapp.configuration.properties;

import a.gleb.clientapp.feign.ResourceServiceFeignClient;
import feign.Logger;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties("client-app")
public class ClientAppConfigurationProperties {

    @NotNull
    private FeignProperties feignProperties;
    @NotNull
    private String applicationName;

    @Getter
    @Setter
    public static class FeignProperties{

        @NotNull
        private String registrationId;

        private Logger.Level loggerLevel = Logger.Level.NONE;

        private FeignRetryer feignRetryer = new FeignRetryer();
    }

    @Getter
    @Setter
    public static class FeignRetryer {

        /**
         * Start interval between attempts in milliseconds
         * Default: 10
         */
        private int startInterval = 10;

        /**
         * Max interval between attempts in milliseconds
         * Default: 3000
         */
        private int maxInterval = 3000;

        /**
         * Count of attempt for {@link ResourceServiceFeignClient}
         * Default: 10
         */
        private int attempts = 10;
    }
}
