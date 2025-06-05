package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.NavbarRequestDto;
import com.oy.oy_jewels.dto.response.NavbarResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NavService {

    NavbarResponseDto createNavbar(MultipartFile logo, NavbarRequestDto requestDto);

    NavbarResponseDto updateNavbar(Long id, MultipartFile logo, NavbarRequestDto requestDto);

    NavbarResponseDto getNavbarById(Long id);

    List<NavbarResponseDto> getAllNavbars();

    void deleteNavbar(Long id);

    byte[] getNavbarLogo(Long id);
}
