package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

    @Modifying
    @Query("DELETE FROM OtpEntity o WHERE o.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM OtpEntity o WHERE o.user.id = :userId AND o.mobileNumber = :mobileNumber AND o.expiresAt > :now AND o.isUsed = false ORDER BY o.createdAt DESC")
    List<OtpEntity> findValidOtps(@Param("userId") Long userId,
                                  @Param("mobileNumber") String mobileNumber,
                                  @Param("now") LocalDateTime now);

    @Modifying
    @Query("DELETE FROM OtpEntity o WHERE o.expiresAt < :currentTime")
    void deleteByExpiresAtBefore(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT o FROM OtpEntity o WHERE o.user.userId = :userId AND o.email = :email AND o.expiresAt > :currentTime AND o.isUsed = false")
    List<OtpEntity> findValidOtpsByEmail(@Param("userId") Long userId, @Param("email") String email, @Param("currentTime") LocalDateTime currentTime);

    void deleteByEmail(String email);
}
