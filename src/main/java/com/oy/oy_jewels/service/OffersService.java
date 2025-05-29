package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.OffersEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OffersService {

    OffersEntity createOffers(MultipartFile pageHeaderBackground, String pageTitle, String superDiscountActiveCoupons);

    OffersEntity getOffersById(Long id);

    List<OffersEntity> getAllOffers();

    OffersEntity updateOffers(Long id, MultipartFile pageHeaderBackground, String pageTitle, String superDiscountActiveCoupons);

    void deleteOffers(Long id);
}