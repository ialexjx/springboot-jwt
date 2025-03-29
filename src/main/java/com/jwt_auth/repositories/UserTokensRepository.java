package com.jwt_auth.repositories;


import com.jwt_auth.models.tables.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Integer> {
    Optional<UserTokens> findByToken(String token);
}
