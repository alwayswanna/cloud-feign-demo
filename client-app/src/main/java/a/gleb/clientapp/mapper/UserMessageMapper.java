package a.gleb.clientapp.mapper;

import a.gleb.clientapp.model.MessageModel;
import a.gleb.clientapp.model.ServiceResponse;
import a.gleb.common.api.ResourceServerMessageRequest;
import a.gleb.common.api.ResourceServerMessageResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserMessageMapper {

    @NonNull
    public ResourceServerMessageRequest toResourceServerRequest(MessageModel messageModel) {
        return ResourceServerMessageRequest.builder()
                .username(messageModel.getUsername())
                .message(messageModel.getMessage())
                .build();
    }

    @NonNull
    public ServiceResponse toServiceResponse(ResourceServerMessageResponse response) {
        return ServiceResponse.builder()
                .messageId(response.getMessageId())
                .timestamp(response.getTimestamp())
                .fromServiceAccount(response.getFromServiceAccount())
                .build();
    }
}
