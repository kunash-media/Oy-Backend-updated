package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.TermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsRepository extends JpaRepository<TermsEntity, Long> {

    // Find by title containing (case insensitive)
    List<TermsEntity> findByTermsTitleContainingIgnoreCase(String title);

    // Find by exact title
    TermsEntity findByTermsTitle(String title);
}
