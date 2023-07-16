package a.gleb.clientapp.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {

    private UUID messageId;
    private String fromServiceAccount;
    private LocalDateTime timestamp;
}
