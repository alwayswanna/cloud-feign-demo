package a.gleb.clientapp.scheduller;

import a.gleb.clientapp.model.MessageModel;
import a.gleb.clientapp.service.ClientAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientAppScheduller {

    private static final String USERNAME = "scheduled-username";
    private static final String MESSAGE_TIME = "Current time: %s";

    private final ClientAppService clientAppService;

    @Scheduled(cron = "${client-app.scheduller.send-message-scheduller}")
    public void processSchedulledMessage() {
        try{
            log.info("Start proceed schedulled message");
            clientAppService.sendMessage(buildMessage());
            log.info("End proceed schedulled message");
        }catch (Exception e){
            log.error("Error while proceed schedulled message", e);
        }
    }

    private MessageModel buildMessage() {
        return MessageModel.builder()
                .username(USERNAME)
                .message(String.format(MESSAGE_TIME, LocalDateTime.now()))
                .build();
    }
}
