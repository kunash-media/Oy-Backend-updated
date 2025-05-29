package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.DisclaimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisclaimerRepository extends JpaRepository<DisclaimerEntity, Long> {

    // Find by title containing (case insensitive)
    List<DisclaimerEntity> findByDisclaimerTitleContainingIgnoreCase(String title);

    // Find by exact title
    DisclaimerEntity findByDisclaimerTitle(String title);

    // Find by section number
    DisclaimerEntity findBySectionNumber(Integer sectionNumber);

    // Find all ordered by section number
    List<DisclaimerEntity> findAllByOrderBySectionNumberAsc();

    // Find disclaimers without titles (where title is null)
    List<DisclaimerEntity> findByDisclaimerTitleIsNull();
}
