package com.sensor.app.sensor_app_movil.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sensor.app.sensor_app_movil.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ConfirmationTokenDevicePasswordChange")
public class ConfirmationTokenDevicePasswordChange {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_ConfirmationTokenDevicePasswordChange")
    private Long idConfirmationTokenDevicePasswordChange;

    @Column(nullable = false)
    private String token;

    @Column(name="new_password", length = 150, nullable = false)
    private String newPassword;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "FK_User"
    )
    private User fkUser;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "FK_Device"
    )
    private Device fkDevice;

    public ConfirmationTokenDevicePasswordChange(
            String token,
            LocalDateTime  createdAt,
            User user,
            Device device,
            String newPassword
    ) {
        this.token = token;
        this.createdAt = createdAt;
        this.fkUser = user;
        this.fkDevice = device;
        this.newPassword = newPassword;
    }
}
