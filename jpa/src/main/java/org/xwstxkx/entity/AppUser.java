package org.xwstxkx.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "app_user")
public class AppUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "telegram_user_id")
    private Long telegramUserId;
    @Column(name = "first_login_date")
    @CreationTimestamp
    private LocalDateTime firstLoginDate;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "is_active")
    private Boolean isActive;
    //TODO в дальнейшем расширить данный функционал
    @Enumerated(EnumType.STRING)
    @Column(name = "user_state")
    private UserState userState;
}
