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
@Entity(name = "ConfirmationTokenInvitation")
public class ConfirmationTokenInvitation {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_ConfirmationTokenInvitation")
    private Long idConfirmationTokenEmailChange;
    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime expiredAt;
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

    public ConfirmationTokenInvitation(
            String token,
            LocalDateTime  createdAt,
            LocalDateTime  expiredAt,
            User user,
            Device device
    ) {
        this.token = token;
        this.createdAt = createdAt;
        this.fkUser = user;
        this.expiredAt = expiredAt;
        this.fkDevice = device;
    }


}
