package a.gleb.clientapp.feign.configuration;

import a.gleb.clientapp.configuration.properties.ClientAppConfigurationProperties;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

public class FeignClientConfiguration {

    private static final String APPLICATION_NAME_HEADER = "application-name";

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
            ClientAppConfigurationProperties properties
    ){
        return new KeycloakOauth2Interceptor(
                clientRegistrationRepository,
                oAuth2AuthorizedClientService,
                properties.getFeignProperties()
        );
    }

    @Bean
    RequestInterceptor applicationNameInterceptor(ClientAppConfigurationProperties properties){
        return requestTemplate -> {
            requestTemplate.header(APPLICATION_NAME_HEADER, properties.getApplicationName());
        };
    }

    @Bean
    Retryer retryer(ClientAppConfigurationProperties properties) {
        var retryerProperties = properties.getFeignProperties().getFeignRetryer();
        return new Retryer.Default(
                retryerProperties.getStartInterval(),
                retryerProperties.getMaxInterval(),
                retryerProperties.getAttempts()
        );
    }

    @Bean
    ErrorDecoder errorDecoder(){
        return new FeignRetryErrorDecoder();
    }

    @Bean
    Logger.Level logger(ClientAppConfigurationProperties properties){
        return properties.getFeignProperties().getLoggerLevel();
    }
}
