package com.oy.oy_jewels.service.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

@Service
public class NavServiceImpl implements NavService {

    @Autowired
    private NavRepository navRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NavEntity createNavbar(MultipartFile logo, String headerText, String sectionsJson) {
        try {
            NavEntity navbar = new NavEntity();

            if (logo != null && !logo.isEmpty()) {
                navbar.setLogo(logo.getBytes());
            }

            navbar.setHeaderText(headerText);

            List<NavSectionEntity> sections = parseSections(sectionsJson, navbar);
            navbar.setSections(sections);

            return navRepository.save(navbar);

        } catch (IOException e) {
            throw new RuntimeException("Error processing navbar data", e);
        }
    }

    @Override
    public NavEntity updateNavbar(Long id, MultipartFile logo, String headerText, String sectionsJson) {
        try {
            NavEntity navbar = navRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Navbar not found"));

            if (logo != null && !logo.isEmpty()) {
                navbar.setLogo(logo.getBytes());
            }

            if (headerText != null) {
                navbar.setHeaderText(headerText);
            }

            if (sectionsJson != null) {
                List<NavSectionEntity> sections = parseSections(sectionsJson, navbar);
                navbar.setSections(sections);
            }

            return navRepository.save(navbar);

        } catch (IOException e) {
            throw new RuntimeException("Error updating navbar data", e);
        }
    }

    @Override
    public NavEntity getNavbarById(Long id) {
        return navRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Navbar not found"));
    }

    @Override
    public List<NavEntity> getAllNavbars() {
        return navRepository.findAll();
    }

    @Override
    public void deleteNavbar(Long id) {
        navRepository.deleteById(id);
    }

    @Override
    public byte[] getNavbarLogo(Long id) {
        NavEntity navbar = getNavbarById(id);
        return navbar.getLogo();
    }

    private List<NavSectionEntity> parseSections(String sectionsJson, NavEntity navbar) throws IOException {
        List<Map<String, Object>> sectionMaps = objectMapper.readValue(
                sectionsJson, new TypeReference<List<Map<String, Object>>>() {});

        List<NavSectionEntity> sections = new ArrayList<>();

        for (Map<String, Object> sectionMap : sectionMaps) {
            NavSectionEntity section = new NavSectionEntity();
            section.setName((String) sectionMap.get("name"));
            section.setPath((String) sectionMap.get("path"));
            section.setHasSubOptions((Boolean) sectionMap.get("hasSubOptions"));
            section.setNavbar(navbar);

            if (sectionMap.containsKey("subOptions") && sectionMap.get("subOptions") != null) {
                List<Map<String, Object>> subOptionMaps = (List<Map<String, Object>>) sectionMap.get("subOptions");
                List<NavSubOptionEntity> subOptions = new ArrayList<>();

                for (Map<String, Object> subOptionMap : subOptionMaps) {
                    NavSubOptionEntity subOption = new NavSubOptionEntity();
                    subOption.setName((String) subOptionMap.get("name"));
                    subOption.setPath((String) subOptionMap.get("path"));
                    subOption.setSection(section);
                    subOptions.add(subOption);
                }

                section.setSubOptions(subOptions);
            }

            sections.add(section);
        }

        return sections;
    }
}