package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.SEOEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SEORepository extends JpaRepository<SEOEntity, Long> {

}
