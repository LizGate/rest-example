package com.example.demo.token.confirmation;

import com.example.demo.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false,name="user_id")
    private UserEntity userEntity;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, UserEntity userEntity) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.userEntity = userEntity;
    }
}
