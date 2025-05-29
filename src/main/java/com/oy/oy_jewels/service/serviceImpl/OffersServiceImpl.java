package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.OffersEntity;
import com.oy.oy_jewels.repository.OffersRepository;
import com.oy.oy_jewels.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class OffersServiceImpl implements OffersService {

    @Autowired
    private OffersRepository offersRepository;

    @Override
    public OffersEntity createOffers(MultipartFile pageHeaderBackground, String pageTitle, String superDiscountActiveCoupons) {
        OffersEntity offersEntity = new OffersEntity();

        try {
            if (pageHeaderBackground != null && !pageHeaderBackground.isEmpty()) {
                offersEntity.setPageHeaderBackground(pageHeaderBackground.getBytes());
            }

            offersEntity.setPageTitle(pageTitle);
            offersEntity.setSuperDiscountActiveCoupons(superDiscountActiveCoupons);

        } catch (IOException e) {
            throw new RuntimeException("Error processing image file", e);
        }

        return offersRepository.save(offersEntity);
    }

    @Override
    public OffersEntity getOffersById(Long id) {
        return offersRepository.findById(id).orElse(null);
    }

    @Override
    public List<OffersEntity> getAllOffers() {
        return offersRepository.findAll();
    }

    @Override
    public OffersEntity updateOffers(Long id, MultipartFile pageHeaderBackground, String pageTitle, String superDiscountActiveCoupons) {
        OffersEntity existingOffers = offersRepository.findById(id).orElse(null);

        if (existingOffers == null) {
            return null;
        }

        try {
            if (pageHeaderBackground != null && !pageHeaderBackground.isEmpty()) {
                existingOffers.setPageHeaderBackground(pageHeaderBackground.getBytes());
            }

            if (pageTitle != null) {
                existingOffers.setPageTitle(pageTitle);
            }

            if (superDiscountActiveCoupons != null) {
                existingOffers.setSuperDiscountActiveCoupons(superDiscountActiveCoupons);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error processing image file", e);
        }

        return offersRepository.save(existingOffers);
    }

    @Override
    public void deleteOffers(Long id) {
        offersRepository.deleteById(id);
    }
}
