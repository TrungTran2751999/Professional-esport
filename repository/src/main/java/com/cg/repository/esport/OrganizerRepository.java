package com.cg.repository.esport;

import com.cg.domain.esport.dto.OrganizerResponseDTO;
import com.cg.domain.esport.entities.Organizer;
import com.cg.domain.esport.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    List<Organizer> findByEmail(String email);
    Organizer getByUser(User user);
    @Query(value = "SELECT * FROM ORGANIZERS WHERE USER_ID = :id", nativeQuery = true)
    Organizer getByUserId(@Param("id") Long id);

    List<Organizer> getByDeleted(Boolean deleted);

}
