package com.sensor.app.sensor_app_movil.security.repository;

import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);


    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    Integer enableUser(String email);

}
