package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.CancellationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancellationRepository extends JpaRepository<CancellationEntity, Long> {

    // Find cancellation policies by title containing the given string (case-insensitive)
    List<CancellationEntity> findByCancellationTitleContainingIgnoreCase(String title);

    // Find cancellation policies by description containing the given string (case-insensitive)
    List<CancellationEntity> findByCancellationDescriptionContainingIgnoreCase(String description);

    // Custom query to find by title or description
    @Query("SELECT c FROM CancellationEntity c WHERE " +
            "LOWER(c.cancellationTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.cancellationDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CancellationEntity> findByTitleOrDescriptionContaining(@Param("keyword") String keyword);

    // Find all cancellation policies ordered by creation date (newest first)
    List<CancellationEntity> findAllByOrderByCreatedAtDesc();

    // Find all cancellation policies ordered by title
    List<CancellationEntity> findAllByOrderByCancellationTitleAsc();
}
