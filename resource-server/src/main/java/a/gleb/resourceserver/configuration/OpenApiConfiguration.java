package a.gleb.resourceserver.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import static a.gleb.resourceserver.configuration.OpenApiConfiguration.OAUTH2_SECURITY_SCHEMA;

@OpenAPIDefinition(
        info = @Info(
                title = "resource-server",
                description = "Example app",
                version = "v1"
        )
)
@SecurityScheme(name = OAUTH2_SECURITY_SCHEMA, type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                clientCredentials = @OAuthFlow(
                        tokenUrl = "${springdoc.oAuthFlow.tokenUrl}"
                )
        )
)
public record OpenApiConfiguration() {

    public static final String OAUTH2_SECURITY_SCHEMA = "myOauth2Security";
}
