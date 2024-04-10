package com.example.springwordgame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    static Admin findByEmail(String email) {
        return null;
    }
}
