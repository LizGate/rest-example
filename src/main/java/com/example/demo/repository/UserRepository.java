package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
    @Query(value = "SELECT id FROM USERS WHERE USERNAME = ?1", nativeQuery = true)
    long getAuthUserId(String authUsername);
}
