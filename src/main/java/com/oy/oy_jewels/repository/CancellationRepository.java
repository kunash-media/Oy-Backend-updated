package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.CancellationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancellationRepository extends JpaRepository<CancellationEntity, Long> {

    // Find by title containing (case insensitive)
    List<CancellationEntity> findByCancellationTitleContainingIgnoreCase(String title);

    // Find by exact title
    CancellationEntity findByCancellationTitle(String title);
}
