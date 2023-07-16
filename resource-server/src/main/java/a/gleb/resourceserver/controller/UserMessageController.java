package a.gleb.resourceserver.controller;

import a.gleb.common.api.ResourceServerMessageRequest;
import a.gleb.common.api.ResourceServerMessageResponse;
import a.gleb.resourceserver.service.UserMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static a.gleb.resourceserver.configuration.OpenApiConfiguration.OAUTH2_SECURITY_SCHEMA;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = UserMessageController.USER_MESSAGE_CONTROLLER_TAG)
public class UserMessageController {

    static final String USER_MESSAGE_CONTROLLER_TAG = "user-message-controller";
    private static final String APPLICATION_NAME_HEADER = "application-name";

    private final UserMessageService userMessageService;

    @Operation(
            summary = "Save new message",
            security = @SecurityRequirement(name = OAUTH2_SECURITY_SCHEMA)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping("/message")
    public ResourceServerMessageResponse saveMessage(
            @Valid @RequestBody ResourceServerMessageRequest resourceServerMessageRequest,
            @RequestHeader(APPLICATION_NAME_HEADER) String appName
    ) {
        return userMessageService.saveMessage(resourceServerMessageRequest, appName);
    }

    @Operation(
            summary = "Save list of messages",
            security = @SecurityRequirement(name = OAUTH2_SECURITY_SCHEMA)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping("/messages")
    public List<ResourceServerMessageResponse> saveMessages(
            @Valid @RequestBody List<ResourceServerMessageRequest> resourceServerMessageRequestList,
            @RequestHeader(APPLICATION_NAME_HEADER) String appName
    ) {
        return userMessageService.saveMessages(resourceServerMessageRequestList, appName);
    }

    @Operation(
            summary = "Find first message by date of create",
            security = @SecurityRequirement(name = OAUTH2_SECURITY_SCHEMA)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/last-message")
    public ResourceServerMessageResponse getLastMessage() {
        return userMessageService.getLastMessage();
    }

    @Operation(
            summary = "Method returns first 10 messages",
            security = @SecurityRequirement(name = OAUTH2_SECURITY_SCHEMA)
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping("/messages")
    public List<ResourceServerMessageResponse> getLastMessages() {
        return userMessageService.getLastMessages();
    }
}
