package com.oy.oy_jewels.repository;


import com.oy.oy_jewels.entity.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQEntity, Long> {
    // Additional custom query methods can be added here if needed
}