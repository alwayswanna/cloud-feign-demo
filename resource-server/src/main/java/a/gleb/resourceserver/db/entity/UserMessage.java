package a.gleb.resourceserver.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usermessage")
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "from_user")
    private String fromUser;

    @Column(name = "message_time")
    private LocalDateTime messageTime;

    @Column(name = "user_field")
    private String userField;

    @Column(name = "user_message")
    private String userMessage;
}
