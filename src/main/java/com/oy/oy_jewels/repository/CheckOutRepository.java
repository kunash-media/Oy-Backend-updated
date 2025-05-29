package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.CheckOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOutEntity, Long> {

    List<CheckOutEntity> findByEmail(String email);

    List<CheckOutEntity> findByPhoneNumber(String phoneNumber);

    List<CheckOutEntity> findByCity(String city);

    List<CheckOutEntity> findByCountry(String country);
}
