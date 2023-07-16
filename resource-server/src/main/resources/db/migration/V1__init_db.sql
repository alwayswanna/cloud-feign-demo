
CREATE TABLE usermessage (
    id              uuid                                PRIMARY KEY,
    from_user       text                                NOT NULL,
    message_time    timestamp without time zone         NOT NULL,
    user_field      text                                NOT NULL,
    user_message    text
);