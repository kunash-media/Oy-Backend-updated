package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.BannerUpdateRequest;
import com.oy.oy_jewels.entity.BannerEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BannerService {

    // Save banner with images
    BannerEntity saveBannerWithImages(String pageName, String header, String text,
                                      MultipartFile[] bannerFileOne, MultipartFile bannerFileTwo,
                                      MultipartFile bannerFileThree, MultipartFile bannerFileFour);

    // Get all banners
    List<BannerEntity> getAllBanners();

    // Get banner by ID
    Optional<BannerEntity> getBannerById(Long id);

    // Get all banners by page name
    BannerEntity getBannerByPageName(String pageName);

    // Delete banner
    void deleteBanner(Long id);

    // Search banners by header
    List<BannerEntity> searchBannersByHeader(String header);

    // Get banners with images
    List<BannerEntity> getBannersWithImages();

    BannerEntity updateBanner(Long id,
                              BannerUpdateRequest updateRequest,
                              MultipartFile[] bannerFileOne,
                              MultipartFile bannerFileTwo,
                              MultipartFile bannerFileThree,
                              MultipartFile bannerFileFour) throws IOException;
}