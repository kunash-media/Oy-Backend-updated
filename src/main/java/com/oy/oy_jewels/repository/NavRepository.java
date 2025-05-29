package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.NavEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavRepository extends JpaRepository<NavEntity, Long> {

}