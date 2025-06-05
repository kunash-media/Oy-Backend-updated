package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.OffersRequestDto;
import com.oy.oy_jewels.dto.response.OffersResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OffersService {

    OffersResponseDto createOffers(MultipartFile pageHeaderBackground, OffersRequestDto requestDto);

    OffersResponseDto getOffersById(Long id);

    List<OffersResponseDto> getAllOffers();

    OffersResponseDto updateOffers(Long id, MultipartFile pageHeaderBackground, OffersRequestDto requestDto);

    void deleteOffers(Long id);

    byte[] getPageHeaderBackground(Long id);
}