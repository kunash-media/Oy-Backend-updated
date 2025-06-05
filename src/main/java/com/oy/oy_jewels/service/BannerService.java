package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.BannerEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BannerService {

    // Save banner with images
    BannerEntity saveBannerWithImages(String pageName, String header, String text,
                                      MultipartFile bannerFileOne, MultipartFile bannerFileTwo,
                                      MultipartFile bannerFileThree, MultipartFile bannerFileFour);

    // Get all banners
    List<BannerEntity> getAllBanners();

    // Get banner by ID
    Optional<BannerEntity> getBannerById(Long id);

    // Get banner by page name (first match)
    Optional<BannerEntity> getBannerByPageName(String pageName);

    // Get all banners by page name
    List<BannerEntity> getBannersByPageName(String pageName);

    // Update banner with images
    BannerEntity updateBannerWithImages(Long id, String pageName, String header, String text,
                                        MultipartFile bannerFileOne, MultipartFile bannerFileTwo,
                                        MultipartFile bannerFileThree, MultipartFile bannerFileFour);

    // Delete banner
    void deleteBanner(Long id);

    // Get specific banner image by image number (1-4)
    byte[] getBannerImage(Long id, int imageNumber);

    // Search banners by header
    List<BannerEntity> searchBannersByHeader(String header);

    // Get banners with images
    List<BannerEntity> getBannersWithImages();

    // Count images for a banner
    Integer countImagesForBanner(Long id);

    // Check if banner exists by page name
    boolean existsByPageName(String pageName);
}
