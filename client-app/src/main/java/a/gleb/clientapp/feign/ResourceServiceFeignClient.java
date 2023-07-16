package a.gleb.clientapp.feign;

import a.gleb.clientapp.feign.configuration.FeignClientConfiguration;
import a.gleb.common.api.ResourceServerMessageRequest;
import a.gleb.common.api.ResourceServerMessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(
        name = "resource-server",
        url = "${client-app.feign-properties.resource-server.url}",
        configuration = FeignClientConfiguration.class
)
public interface ResourceServiceFeignClient {

    @PostMapping("/message")
    ResourceServerMessageResponse send(ResourceServerMessageRequest messageModel);

    @PostMapping("/messages")
    List<ResourceServerMessageResponse> sendMessages(List<ResourceServerMessageRequest> messageModelLis);

    @GetMapping("/last-message")
    ResourceServerMessageResponse getMessage();

    @GetMapping("/messages")
    List<ResourceServerMessageResponse> getMessages();
}
