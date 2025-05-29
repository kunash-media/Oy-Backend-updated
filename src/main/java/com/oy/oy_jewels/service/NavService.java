package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.NavEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NavService {

    NavEntity createNavbar(MultipartFile logo, String headerText, String sectionsJson);

    NavEntity updateNavbar(Long id, MultipartFile logo, String headerText, String sectionsJson);

    NavEntity getNavbarById(Long id);

    List<NavEntity> getAllNavbars();

    void deleteNavbar(Long id);

    byte[] getNavbarLogo(Long id);
}