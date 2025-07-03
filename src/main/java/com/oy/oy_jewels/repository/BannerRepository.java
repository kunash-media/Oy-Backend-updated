package com.oy.oy_jewels.repository;


import com.oy.oy_jewels.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, Long> {

    // Find banner by page name (returns first match)
    Optional<BannerEntity> findFirstByPageName(String pageName);

    // Find all banners by page name
    BannerEntity findByPageName(String pageName);

    // Check if banner exists by page name
    boolean existsByPageName(String pageName);

    @Query("SELECT b FROM BannerEntity b WHERE b.pageName = :pageName")
    BannerEntity findSingleByPageName(@Param("pageName") String pageName);

    // Search banners by header containing keyword (case-insensitive)
    @Query("SELECT b FROM BannerEntity b WHERE LOWER(b.header) LIKE LOWER(CONCAT('%', :header, '%'))")
    List<BannerEntity> findByHeaderContainingIgnoreCase(@Param("header") String header);

    // Find banners that have at least one image
    @Query("SELECT b FROM BannerEntity b WHERE b.bannerFileOne IS NOT NULL OR b.bannerFileTwo IS NOT NULL OR b.bannerFileThree IS NOT NULL OR b.bannerFileFour IS NOT NULL")
    List<BannerEntity> findBannersWithImages();

    // Custom query to count non-null images for a specific banner
    @Query("SELECT " +
            "(CASE WHEN b.bannerFileOne IS NOT NULL THEN 1 ELSE 0 END) + " +
            "(CASE WHEN b.bannerFileTwo IS NOT NULL THEN 1 ELSE 0 END) + " +
            "(CASE WHEN b.bannerFileThree IS NOT NULL THEN 1 ELSE 0 END) + " +
            "(CASE WHEN b.bannerFileFour IS NOT NULL THEN 1 ELSE 0 END) " +
            "FROM BannerEntity b WHERE b.id = :id")
    Integer countImagesById(@Param("id") Long id);


}
