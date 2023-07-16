package a.gleb.resourceserver.service;

import a.gleb.common.api.ResourceServerMessageRequest;
import a.gleb.common.api.ResourceServerMessageResponse;
import a.gleb.resourceserver.configuration.properties.ResourceServerConfigurationProperties;
import a.gleb.resourceserver.db.repository.UserMessageRepository;
import a.gleb.resourceserver.exception.UserMessageNotFoundException;
import a.gleb.resourceserver.exception.UserMessageValidationException;
import a.gleb.resourceserver.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserMessageService {

    private final MessageMapper messageMapper;
    private final UserMessageRepository userMessageRepository;
    private final ResourceServerConfigurationProperties properties;

    @NonNull
    public ResourceServerMessageResponse saveMessage(
            @NonNull ResourceServerMessageRequest resourceServerMessageRequest,
            String appName
    ) {
        validateUserMessageLength(resourceServerMessageRequest);
        var message = userMessageRepository.save(messageMapper.toUserMessage(resourceServerMessageRequest, appName));

        return messageMapper.toResourceServerMessageResponse(message);
    }

    @NonNull
    public List<ResourceServerMessageResponse> saveMessages(
            List<ResourceServerMessageRequest> resourceServerMessageRequestList,
            String appName
    ) {
        resourceServerMessageRequestList.forEach(this::validateUserMessageLength);
        var userMessages = resourceServerMessageRequestList.stream()
                .map(request -> messageMapper.toUserMessage(request, appName))
                .toList();
        var savedUserMessage = userMessageRepository.saveAll(userMessages);

        return savedUserMessage.stream()
                .map(messageMapper::toResourceServerMessageResponse)
                .toList();
    }

    @NonNull
    public ResourceServerMessageResponse getLastMessage() {
        return userMessageRepository.findFirstOrderByMessageTime()
                .map(messageMapper::toResourceServerMessageResponse)
                .orElseThrow(() -> new UserMessageNotFoundException("Message not found"));
    }

    @NonNull
    public List<ResourceServerMessageResponse> getLastMessages() {
        var userMessages = userMessageRepository.findFirst10OrderByMessageTime();
        if (userMessages.isEmpty()) {
            throw new UserMessageNotFoundException("Messages not found");
        }

        return userMessages.stream()
                .map(messageMapper::toResourceServerMessageResponse)
                .toList();
    }

    private void validateUserMessageLength(ResourceServerMessageRequest resourceServerMessageRequest) {
        var message = resourceServerMessageRequest.getMessage();
        if (message != null && message.length() > properties.getMaxUserMessageLength()) {
            throw new UserMessageValidationException(format("Length of user message more than %s", properties.getMaxUserMessageLength()));
        }
    }
}
