package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.NavSectionDto;
import com.oy.oy_jewels.dto.request.NavSubOptionDto;
import com.oy.oy_jewels.dto.request.NavbarRequestDto;
import com.oy.oy_jewels.dto.response.NavbarResponseDto;
import com.oy.oy_jewels.entity.NavEntity;
import com.oy.oy_jewels.entity.NavSectionEntity;
import com.oy.oy_jewels.entity.NavSubOptionEntity;
import com.oy.oy_jewels.repository.NavRepository;
import com.oy.oy_jewels.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NavServiceImpl implements NavService {

    @Autowired
    private NavRepository navRepository;

    @Override
    public NavbarResponseDto createNavbar(MultipartFile logo, NavbarRequestDto requestDto) {
        try {
            NavEntity navbar = new NavEntity();

            if (logo != null && !logo.isEmpty()) {
                navbar.setLogo(logo.getBytes());
            }

            navbar.setHeaderText(requestDto.getHeaderText());

            List<NavSectionEntity> sections = convertToSectionEntities(requestDto.getSections(), navbar);
            navbar.setSections(sections);

            NavEntity savedNavbar = navRepository.save(navbar);
            return convertToResponseDto(savedNavbar);

        } catch (IOException e) {
            throw new RuntimeException("Error processing navbar data", e);
        }
    }

    @Override
    public NavbarResponseDto updateNavbar(Long id, MultipartFile logo, NavbarRequestDto requestDto) {
        try {
            NavEntity navbar = navRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Navbar not found"));

            if (logo != null && !logo.isEmpty()) {
                navbar.setLogo(logo.getBytes());
            }

            if (requestDto != null) {
                if (requestDto.getHeaderText() != null) {
                    navbar.setHeaderText(requestDto.getHeaderText());
                }

                if (requestDto.getSections() != null) {
                    List<NavSectionEntity> sections = convertToSectionEntities(requestDto.getSections(), navbar);
                    navbar.setSections(sections);
                }
            }

            NavEntity savedNavbar = navRepository.save(navbar);
            return convertToResponseDto(savedNavbar);

        } catch (IOException e) {
            throw new RuntimeException("Error updating navbar data", e);
        }
    }

    @Override
    public NavbarResponseDto getNavbarById(Long id) {
        NavEntity navbar = navRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Navbar not found"));
        return convertToResponseDto(navbar);
    }

    @Override
    public List<NavbarResponseDto> getAllNavbars() {
        List<NavEntity> navbars = navRepository.findAll();
        return navbars.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNavbar(Long id) {
        navRepository.deleteById(id);
    }

    @Override
    public byte[] getNavbarLogo(Long id) {
        NavEntity navbar = navRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Navbar not found"));
        return navbar.getLogo();
    }

    private List<NavSectionEntity> convertToSectionEntities(List<NavSectionDto> sectionDtos, NavEntity navbar) {
        if (sectionDtos == null) {
            return new ArrayList<>();
        }

        List<NavSectionEntity> sections = new ArrayList<>();

        for (NavSectionDto sectionDto : sectionDtos) {
            NavSectionEntity section = new NavSectionEntity();
            section.setName(sectionDto.getName());
            section.setPath(sectionDto.getPath());
            section.setHasSubOptions(sectionDto.getHasSubOptions());
            section.setNavbar(navbar);

            if (sectionDto.getSubOptions() != null && !sectionDto.getSubOptions().isEmpty()) {
                List<NavSubOptionEntity> subOptions = new ArrayList<>();

                for (NavSubOptionDto subOptionDto : sectionDto.getSubOptions()) {
                    NavSubOptionEntity subOption = new NavSubOptionEntity();
                    subOption.setName(subOptionDto.getName());
                    subOption.setPath(subOptionDto.getPath());
                    subOption.setSection(section);
                    subOptions.add(subOption);
                }

                section.setSubOptions(subOptions);
            }

            sections.add(section);
        }

        return sections;
    }

    private NavbarResponseDto convertToResponseDto(NavEntity navbar) {
        NavbarResponseDto responseDto = new NavbarResponseDto();
        responseDto.setId(navbar.getId());
        responseDto.setHeaderText(navbar.getHeaderText());
        responseDto.setHasLogo(navbar.getLogo() != null && navbar.getLogo().length > 0);

        if (navbar.getSections() != null) {
            List<NavSectionDto> sectionDtos = navbar.getSections().stream()
                    .map(this::convertToSectionDto)
                    .collect(Collectors.toList());
            responseDto.setSections(sectionDtos);
        }

        return responseDto;
    }

    private NavSectionDto convertToSectionDto(NavSectionEntity section) {
        NavSectionDto sectionDto = new NavSectionDto();
        sectionDto.setId(section.getId());
        sectionDto.setName(section.getName());
        sectionDto.setPath(section.getPath());
        sectionDto.setHasSubOptions(section.getHasSubOptions());

        if (section.getSubOptions() != null && !section.getSubOptions().isEmpty()) {
            List<NavSubOptionDto> subOptionDtos = section.getSubOptions().stream()
                    .map(this::convertToSubOptionDto)
                    .collect(Collectors.toList());
            sectionDto.setSubOptions(subOptionDtos);
        }

        return sectionDto;
    }

    private NavSubOptionDto convertToSubOptionDto(NavSubOptionEntity subOption) {
        NavSubOptionDto subOptionDto = new NavSubOptionDto();
        subOptionDto.setId(subOption.getId());
        subOptionDto.setName(subOption.getName());
        subOptionDto.setPath(subOption.getPath());
        return subOptionDto;
    }
}