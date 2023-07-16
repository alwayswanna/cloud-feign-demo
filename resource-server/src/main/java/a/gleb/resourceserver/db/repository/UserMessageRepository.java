package a.gleb.resourceserver.db.repository;

import a.gleb.resourceserver.db.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserMessageRepository extends JpaRepository<UserMessage, UUID> {

    /* method find last message by date */
    @Query(value = "select * from usermessage order by message_time desc limit 1", nativeQuery = true)
    Optional<UserMessage> findFirstOrderByMessageTime();

    /* method find last 10 message by date */
    @Query(value = "select * from usermessage order by message_time desc limit 10", nativeQuery = true)
    List<UserMessage> findFirst10OrderByMessageTime();
}
