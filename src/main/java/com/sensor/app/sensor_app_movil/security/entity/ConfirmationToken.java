package com.sensor.app.sensor_app_movil.security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ConfirmationToken")
public class ConfirmationToken {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_ConfirmationToken")
    private Long idConfirmationToken;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime  createdAt;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime  expiresAt;

    @Column()
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime  confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "FK_User"
    )
    private User fkUser;

    public ConfirmationToken(String token,
                             LocalDateTime  createdAt,
                             LocalDateTime  expiresAt,
                             User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.fkUser = user;
    }
}
