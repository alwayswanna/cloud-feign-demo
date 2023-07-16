package a.gleb.resourceserver.mapper;

import a.gleb.common.api.ResourceServerMessageRequest;
import a.gleb.common.api.ResourceServerMessageResponse;
import a.gleb.resourceserver.db.entity.UserMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
public class MessageMapper {

    @NonNull
    public UserMessage toUserMessage(ResourceServerMessageRequest resourceServerMessageRequest, String userAccount) {
        return UserMessage.builder()
                .fromUser(StringUtils.isNotEmpty(userAccount) ? userAccount : EMPTY)
                .messageTime(LocalDateTime.now())
                .userField(resourceServerMessageRequest.getUsername())
                .userMessage(resourceServerMessageRequest.getMessage())
                .build();
    }

    @NonNull
    public ResourceServerMessageResponse toResourceServerMessageResponse(UserMessage message) {
        return ResourceServerMessageResponse.builder()
                .messageId(message.getId())
                .fromServiceAccount(message.getFromUser())
                .timestamp(message.getMessageTime())
                .build();
    }
}
