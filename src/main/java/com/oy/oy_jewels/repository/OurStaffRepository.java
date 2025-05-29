package com.oy.oy_jewels.repository;


import com.oy.oy_jewels.entity.OurStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OurStaffRepository extends JpaRepository<OurStaffEntity, Long> {

    // Find staff by email address
    Optional<OurStaffEntity> findByEmailAddress(String emailAddress);

    // Find staff by staff role
    List<OurStaffEntity> findByStaffRole(String staffRole);

    // Find staff by staff name (case insensitive)
    List<OurStaffEntity> findByStaffNameContainingIgnoreCase(String staffName);

    // Check if email exists
    boolean existsByEmailAddress(String emailAddress);

    // Find staff by contact number
    Optional<OurStaffEntity> findByContactNumber(String contactNumber);

    // Custom query to find active staff (you can modify this based on your requirements)
    @Query("SELECT s FROM OurStaffEntity s WHERE s.staffRole = :role ORDER BY s.joiningDate DESC")
    List<OurStaffEntity> findStaffByRoleOrderedByJoiningDate(@Param("role") String role);

    // Find all staff ordered by joining date
    List<OurStaffEntity> findAllByOrderByJoiningDateDesc();
}
