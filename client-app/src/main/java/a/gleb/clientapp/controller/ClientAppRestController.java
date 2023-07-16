package a.gleb.clientapp.controller;

import a.gleb.clientapp.model.MessageModel;
import a.gleb.clientapp.model.ServiceResponse;
import a.gleb.clientapp.service.ClientAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
@Tag(name = ClientAppRestController.CLIENT_APP_REST_CONTROLLER_TAG)
public class ClientAppRestController {

    static final String CLIENT_APP_REST_CONTROLLER_TAG = "client-app-controller";

    private final ClientAppService clientAppService;

    @Operation(
            summary = "Send message"
    )
    @PostMapping("/send")
    public ServiceResponse sendMessage(@Valid @RequestBody MessageModel messageModel){
        return clientAppService.sendMessage(messageModel);
    }

    @Operation(
            summary = "Send messages"
    )
    @PostMapping("/send-messages")
    public List<ServiceResponse> sendMessages(@Valid @RequestBody List<MessageModel> messageModelList){
        return clientAppService.sendMessages(messageModelList);
    }

    @Operation(
            summary = "Get last message"
    )
    @GetMapping("/last-message")
    public ServiceResponse getMessage(){
        return clientAppService.getLastMessage();
    }

    @Operation(
            summary = "Get last 10 messsages"
    )
    @GetMapping("/get-messages")
    public List<ServiceResponse> getMessages(){
        return clientAppService.getMessages();
    }
}
