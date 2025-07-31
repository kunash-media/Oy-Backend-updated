package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}