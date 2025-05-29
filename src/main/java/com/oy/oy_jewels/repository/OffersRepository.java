package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.OffersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends JpaRepository<OffersEntity, Long> {

}
