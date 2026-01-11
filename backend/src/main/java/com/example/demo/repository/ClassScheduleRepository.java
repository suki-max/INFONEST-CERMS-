package com.example.demo.repository;

import com.example.demo.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Integer> {
    
    // YEH CHANGE HUA HAI: findByUserId -> findByUser_UserId
    // Custom query: SELECT * FROM class_schedule WHERE user_id = ?
    // (Spring Data JPA is naam ko samjhega: "user" object ke andar "userId" property se find karo)
    List<ClassSchedule> findByUser_UserId(Integer userId);

    // YEH BHI CHANGE HUA HAI: findByVenueId -> findByVenue_VenueId
    // Custom query: SELECT * FROM class_schedule WHERE venue_id = ?
    // (Spring Data JPA is naam ko samjhega: "venue" object ke andar "venueId" property se find karo)
    List<ClassSchedule> findByVenue_VenueId(Integer venueId);
}