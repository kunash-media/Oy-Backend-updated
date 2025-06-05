package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.BannerEntity;
import com.oy.oy_jewels.repository.BannerRepository;
import com.oy.oy_jewels.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public BannerEntity saveBannerWithImages(String pageName, String header, String text,
                                             MultipartFile bannerFileOne, MultipartFile bannerFileTwo,
                                             MultipartFile bannerFileThree, MultipartFile bannerFileFour) {
        try {
            BannerEntity banner = new BannerEntity(pageName, header, text);

            // Process and save images
            if (bannerFileOne != null && !bannerFileOne.isEmpty()) {
                banner.setBannerFileOne(bannerFileOne.getBytes());
            }
            if (bannerFileTwo != null && !bannerFileTwo.isEmpty()) {
                banner.setBannerFileTwo(bannerFileTwo.getBytes());
            }
            if (bannerFileThree != null && !bannerFileThree.isEmpty()) {
                banner.setBannerFileThree(bannerFileThree.getBytes());
            }
            if (bannerFileFour != null && !bannerFileFour.isEmpty()) {
                banner.setBannerFileFour(bannerFileFour.getBytes());
            }

            return bannerRepository.save(banner);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image files: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BannerEntity> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Optional<BannerEntity> getBannerById(Long id) {
        return bannerRepository.findById(id);
    }

    @Override
    public Optional<BannerEntity> getBannerByPageName(String pageName) {
        return bannerRepository.findFirstByPageName(pageName);
    }

    @Override
    public List<BannerEntity> getBannersByPageName(String pageName) {
        return bannerRepository.findByPageName(pageName);
    }

    @Override
    public BannerEntity updateBannerWithImages(Long id, String pageName, String header, String text,
                                               MultipartFile bannerFileOne, MultipartFile bannerFileTwo,
                                               MultipartFile bannerFileThree, MultipartFile bannerFileFour) {
        try {
            Optional<BannerEntity> existingBannerOpt = bannerRepository.findById(id);
            if (existingBannerOpt.isEmpty()) {
                throw new RuntimeException("Banner not found with id: " + id);
            }

            BannerEntity existingBanner = existingBannerOpt.get();

            // Update text fields
            existingBanner.setPageName(pageName);
            existingBanner.setHeader(header);
            existingBanner.setText(text);

            // Update images only if new files are provided
            if (bannerFileOne != null && !bannerFileOne.isEmpty()) {
                existingBanner.setBannerFileOne(bannerFileOne.getBytes());
            }
            if (bannerFileTwo != null && !bannerFileTwo.isEmpty()) {
                existingBanner.setBannerFileTwo(bannerFileTwo.getBytes());
            }
            if (bannerFileThree != null && !bannerFileThree.isEmpty()) {
                existingBanner.setBannerFileThree(bannerFileThree.getBytes());
            }
            if (bannerFileFour != null && !bannerFileFour.isEmpty()) {
                existingBanner.setBannerFileFour(bannerFileFour.getBytes());
            }

            return bannerRepository.save(existingBanner);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image files: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBanner(Long id) {
        if (!bannerRepository.existsById(id)) {
            throw new RuntimeException("Banner not found with id: " + id);
        }
        bannerRepository.deleteById(id);
    }

    @Override
    public byte[] getBannerImage(Long id, int imageNumber) {
        Optional<BannerEntity> bannerOpt = bannerRepository.findById(id);
        if (bannerOpt.isEmpty()) {
            throw new RuntimeException("Banner not found with id: " + id);
        }

        BannerEntity banner = bannerOpt.get();

        switch (imageNumber) {
            case 1:
                return banner.getBannerFileOne();
            case 2:
                return banner.getBannerFileTwo();
            case 3:
                return banner.getBannerFileThree();
            case 4:
                return banner.getBannerFileFour();
            default:
                throw new RuntimeException("Invalid image number. Must be between 1 and 4.");
        }
    }

    @Override
    public List<BannerEntity> searchBannersByHeader(String header) {
        return bannerRepository.findByHeaderContainingIgnoreCase(header);
    }

    @Override
    public List<BannerEntity> getBannersWithImages() {
        return bannerRepository.findBannersWithImages();
    }

    @Override
    public Integer countImagesForBanner(Long id) {
        if (!bannerRepository.existsById(id)) {
            throw new RuntimeException("Banner not found with id: " + id);
        }
        return bannerRepository.countImagesById(id);
    }

    @Override
    public boolean existsByPageName(String pageName) {
        return bannerRepository.existsByPageName(pageName);
    }
}