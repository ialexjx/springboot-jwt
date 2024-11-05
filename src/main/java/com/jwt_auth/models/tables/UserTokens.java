package com.jwt_auth.models.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Boolean isRevoked = false;
}