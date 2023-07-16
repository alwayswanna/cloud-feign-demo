package a.gleb.common.api;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceServerMessageResponse {
    private UUID messageId;
    private String fromServiceAccount;
    private LocalDateTime timestamp;
}
