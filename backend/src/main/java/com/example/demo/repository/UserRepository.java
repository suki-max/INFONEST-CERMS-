package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    Optional<User> findTopByOrderByUserIdDesc();

    @Query(value = "SELECT * FROM \"user\" WHERE club_id = :clubId", nativeQuery = true)
    List<User> findByClubId(@Param("clubId") String clubId);
}