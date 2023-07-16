package a.gleb.common.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceServerMessageRequest {
    private String username;
    private String message;
}
