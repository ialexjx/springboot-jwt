package com.jwt_auth.repositories;


import com.jwt_auth.models.tables.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Integer> {
    UserTokens findByToken(String token);
}
