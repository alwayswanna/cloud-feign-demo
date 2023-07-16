package a.gleb.clientapp.feign.configuration;

import a.gleb.clientapp.configuration.properties.ClientAppConfigurationProperties.FeignProperties;
import a.gleb.clientapp.feign.model.OAuth2Authentication;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.Assert;

public class KeycloakOauth2Interceptor implements RequestInterceptor {

    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_HEADER_FORMAT = "%s %s";

    private final OAuth2AuthorizeRequest oAuth2AuthorizeRequest;
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public KeycloakOauth2Interceptor(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
            FeignProperties properties
    ){
        var oAuth2AuthorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                oAuth2AuthorizedClientService
        );
        oAuth2AuthorizedClientManager.setAuthorizedClientProvider(
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .refreshToken()
                        .build()
        );

        var registrationId = properties.getRegistrationId();
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
        this.oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(registrationId)
                .principal(new OAuth2Authentication(registrationId))
                .build();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION, getAccessToken());
    }

    private String getAccessToken() {
        var client = authorize();
        Assert.notNull(client, "AuthorizedClient cannot be null");
        return String.format(AUTHORIZATION_HEADER_FORMAT, BEARER, client.getAccessToken().getTokenValue());
    }

    private OAuth2AuthorizedClient authorize() {
        try {
            return oAuth2AuthorizedClientManager.authorize(oAuth2AuthorizeRequest);
        } catch (ClientAuthorizationException e) {
            return oAuth2AuthorizedClientManager.authorize(oAuth2AuthorizeRequest);
        }
    }
}
