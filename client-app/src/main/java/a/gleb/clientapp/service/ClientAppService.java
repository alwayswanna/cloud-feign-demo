package a.gleb.clientapp.service;

import a.gleb.clientapp.feign.ResourceServiceFeignClient;
import a.gleb.clientapp.mapper.UserMessageMapper;
import a.gleb.clientapp.model.MessageModel;
import a.gleb.clientapp.model.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientAppService {

    private final UserMessageMapper userMessageMapper;
    private final ResourceServiceFeignClient resourceServiceFeignClient;

    @NonNull
    public ServiceResponse sendMessage(MessageModel messageModel) {
        var request = userMessageMapper.toResourceServerRequest(messageModel);
        var response = resourceServiceFeignClient.send(request);

        return userMessageMapper.toServiceResponse(response);
    }

    @NonNull
    public List<ServiceResponse> sendMessages(List<MessageModel> messageModelList) {
        var response = resourceServiceFeignClient.sendMessages(messageModelList.stream()
                .map(userMessageMapper::toResourceServerRequest)
                .toList()
        );

        return response.stream()
                .map(userMessageMapper::toServiceResponse)
                .toList();
    }

    @NonNull
    public ServiceResponse getLastMessage() {
        return userMessageMapper.toServiceResponse(resourceServiceFeignClient.getMessage());
    }

    @NonNull
    public List<ServiceResponse> getMessages() {
        return resourceServiceFeignClient.getMessages()
                .stream()
                .map(userMessageMapper::toServiceResponse)
                .toList();
    }
}
