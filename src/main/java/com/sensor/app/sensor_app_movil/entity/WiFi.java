package com.sensor.app.sensor_app_movil.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "WiFi")
@Getter
@Setter
@NoArgsConstructor
public class WiFi {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_WiFi")
    private Long idWiFi;

    @Column(name="SSID", length = 100, nullable = false)
    private String deviceCode;

    @Column(name="password", length = 100, nullable = false)
    private String password;

    @Column(name = "create_date",insertable = false,  updatable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    private LocalDateTime created;

    @Column(name = "update_date", insertable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    private LocalDateTime updated;

    @Column(name="state")
    private Boolean state;

}
