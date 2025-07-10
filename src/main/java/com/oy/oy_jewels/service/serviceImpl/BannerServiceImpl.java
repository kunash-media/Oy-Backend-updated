package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.BannerUpdateRequest;
import com.oy.oy_jewels.entity.BannerEntity;
import com.oy.oy_jewels.repository.BannerRepository;
import com.oy.oy_jewels.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public BannerEntity saveBannerWithImages(String pageName, String header, String text,
                                             MultipartFile[] bannerFileOne, MultipartFile bannerFileTwo,
                                             MultipartFile bannerFileThree, MultipartFile bannerFileFour) {
        try {
            BannerEntity banner = new BannerEntity(pageName, header, text);

            // Process and save multiple images for bannerFileOne
            if (bannerFileOne != null && bannerFileOne.length > 0) {
                List<byte[]> bannerFileOneList = new ArrayList<>();
                for (MultipartFile file : bannerFileOne) {
                    if (file != null && !file.isEmpty()) {
                        bannerFileOneList.add(file.getBytes());
                    }
                }
                banner.setBannerFileOne(bannerFileOneList);
            }

            // Process and save other single images with null checks
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

    //patch api impl
    @Transactional
    public BannerEntity updateBanner(Long id,
                                     BannerUpdateRequest updateRequest,
                                     MultipartFile[] bannerFileOne,
                                     MultipartFile bannerFileTwo,
                                     MultipartFile bannerFileThree,
                                     MultipartFile bannerFileFour) throws IOException {

        BannerEntity existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with id: " + id));

        // Update text fields if provided in the request
        if (updateRequest != null) {
            if (updateRequest.getPageName() != null) {
                existingBanner.setPageName(updateRequest.getPageName());
            }
            if (updateRequest.getHeader() != null) {
                existingBanner.setHeader(updateRequest.getHeader());
            }
            if (updateRequest.getText() != null) {
                existingBanner.setText(updateRequest.getText());
            }
        }

        // Update bannerFileOne if new files are provided
        if (bannerFileOne != null && bannerFileOne.length > 0) {
            // Clear existing bannerFileOne images
            existingBanner.getBannerFileOne().clear();

            // Add new images
            List<byte[]> newBannerFileOneList = new ArrayList<>();
            for (MultipartFile file : bannerFileOne) {
                if (file != null && !file.isEmpty()) {
                    newBannerFileOneList.add(file.getBytes());
                }
            }
            existingBanner.setBannerFileOne(newBannerFileOneList);
        }

        // Update other image fields if new files are provided
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
    }

    public List<BannerEntity> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Optional<BannerEntity> getBannerById(Long id) {
        return bannerRepository.findById(id);
    }

    @Override
    public BannerEntity getBannerByPageName(String pageName) {
        return bannerRepository.findByPageName(pageName);
    }

    @Override
    public void deleteBanner(Long id) {
        if (!bannerRepository.existsById(id)) {
            throw new RuntimeException("Banner not found with id: " + id);
        }
        bannerRepository.deleteById(id);
    }

    @Override
    public List<BannerEntity> searchBannersByHeader(String header) {
        return bannerRepository.findByHeaderContainingIgnoreCase(header);
    }

    @Override
    public List<BannerEntity> getBannersWithImages() {
        return bannerRepository.findBannersWithImages();
    }
}