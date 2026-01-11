package com.example.demo.repository;

import com.example.demo.entity.EventRecruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRecruitmentRepository extends JpaRepository<EventRecruitment, Integer> {

    // Custom query: SELECT * FROM event_recruitment WHERE event_id = ?
    // (Spring Data JPA is naam ko samjhega: "event" object ke andar "eventId" property se find karo)
    List<EventRecruitment> findByEvent_EventId(Integer eventId);

    // Custom query: SELECT * FROM event_recruitment WHERE user_id = ?
    List<EventRecruitment> findByUser_UserId(Integer userId);
}