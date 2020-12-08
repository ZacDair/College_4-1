package com.warner_dair.dao;

import com.warner_dair.entities.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<CustomUser, String> {
    boolean existsByUserEmail(String userEmail);
}
