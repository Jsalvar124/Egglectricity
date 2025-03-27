package com.egg.egglectricity.repositories;

import com.egg.egglectricity.entities.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImplRepository extends JpaRepository<UserImpl, String> {
    UserImpl findByEmail(String email);
}
