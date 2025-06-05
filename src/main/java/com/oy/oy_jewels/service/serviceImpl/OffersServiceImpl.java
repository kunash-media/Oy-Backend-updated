package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.request.OffersRequestDto;
import com.oy.oy_jewels.dto.response.OffersResponseDto;
import com.oy.oy_jewels.entity.OffersEntity;
import com.oy.oy_jewels.repository.OffersRepository;
import com.oy.oy_jewels.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffersServiceImpl implements OffersService {

    @Autowired
    private OffersRepository offersRepository;

    @Override
    public OffersResponseDto createOffers(MultipartFile pageHeaderBackground, OffersRequestDto requestDto) {
        OffersEntity offersEntity = new OffersEntity();

        try {
            if (pageHeaderBackground != null && !pageHeaderBackground.isEmpty()) {
                offersEntity.setPageHeaderBackground(pageHeaderBackground.getBytes());
            }

            offersEntity.setPageTitle(requestDto.getPageTitle());
            offersEntity.setSuperDiscountActiveCoupons(requestDto.getSuperDiscountActiveCoupons());

        } catch (IOException e) {
            throw new RuntimeException("Error processing image file", e);
        }

        OffersEntity savedEntity = offersRepository.save(offersEntity);
        return convertToResponseDto(savedEntity);
    }

    @Override
    public OffersResponseDto getOffersById(Long id) {
        OffersEntity offers = offersRepository.findById(id).orElse(null);
        if (offers == null) {
            return null;
        }
        return convertToResponseDto(offers);
    }

    @Override
    public List<OffersResponseDto> getAllOffers() {
        List<OffersEntity> offers = offersRepository.findAll();
        return offers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public OffersResponseDto updateOffers(Long id, MultipartFile pageHeaderBackground, OffersRequestDto requestDto) {
        OffersEntity existingOffers = offersRepository.findById(id).orElse(null);

        if (existingOffers == null) {
            return null;
        }

        try {
            if (pageHeaderBackground != null && !pageHeaderBackground.isEmpty()) {
                existingOffers.setPageHeaderBackground(pageHeaderBackground.getBytes());
            }

            if (requestDto != null) {
                if (requestDto.getPageTitle() != null) {
                    existingOffers.setPageTitle(requestDto.getPageTitle());
                }

                if (requestDto.getSuperDiscountActiveCoupons() != null) {
                    existingOffers.setSuperDiscountActiveCoupons(requestDto.getSuperDiscountActiveCoupons());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error processing image file", e);
        }

        OffersEntity savedEntity = offersRepository.save(existingOffers);
        return convertToResponseDto(savedEntity);
    }

    @Override
    public void deleteOffers(Long id) {
        offersRepository.deleteById(id);
    }

    @Override
    public byte[] getPageHeaderBackground(Long id) {
        OffersEntity offers = offersRepository.findById(id).orElse(null);
        if (offers == null) {
            return null;
        }
        return offers.getPageHeaderBackground();
    }

    private OffersResponseDto convertToResponseDto(OffersEntity entity) {
        OffersResponseDto responseDto = new OffersResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setPageTitle(entity.getPageTitle());
        responseDto.setSuperDiscountActiveCoupons(entity.getSuperDiscountActiveCoupons());
        responseDto.setHasPageHeaderBackground(entity.getPageHeaderBackground() != null && entity.getPageHeaderBackground().length > 0);
        return responseDto;
    }
}