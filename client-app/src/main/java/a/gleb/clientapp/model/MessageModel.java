package a.gleb.clientapp.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {

    @NotEmpty
    private String username;
    @NotEmpty
    private String message;
}
