package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQEntity, Long> {

    // Find all active FAQs ordered by display order
    List<FAQEntity> findByIsActiveTrueOrderByDisplayOrderAsc();

    // Find FAQs by category
    List<FAQEntity> findByCategoryAndIsActiveTrueOrderByDisplayOrderAsc(String category);

    // Find all categories
    @Query("SELECT DISTINCT f.category FROM FAQEntity f WHERE f.isActive = true AND f.category IS NOT NULL")
    List<String> findAllActiveCategories();

    // Search FAQs by question or answer containing keyword
    @Query("SELECT f FROM FAQEntity f WHERE f.isActive = true AND " +
            "(LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.answer) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY f.displayOrder ASC")
    List<FAQEntity> searchFAQsByKeyword(@Param("keyword") String keyword);

    // Find FAQ by question (for duplicate check)
    FAQEntity findByQuestionIgnoreCase(String question);

    // Get max display order for ordering
    @Query("SELECT MAX(f.displayOrder) FROM FAQEntity f")
    Integer findMaxDisplayOrder();

    // Find FAQs by multiple categories
    @Query("SELECT f FROM FAQEntity f WHERE f.isActive = true AND f.category IN :categories ORDER BY f.displayOrder ASC")
    List<FAQEntity> findByCategoriesAndIsActiveTrue(@Param("categories") List<String> categories);
}
