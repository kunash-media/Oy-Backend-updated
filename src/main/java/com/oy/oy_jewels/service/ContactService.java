package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.ContactRequestDTO;
import com.oy.oy_jewels.dto.response.ContactResponseDTO;

import java.util.List;

public interface ContactService {
    ContactResponseDTO saveContact(ContactRequestDTO contactRequestDTO);
    List<ContactResponseDTO> getAllContacts();

    ContactResponseDTO getContactById(Long formId);
    void deleteContact(Long formId);
}
