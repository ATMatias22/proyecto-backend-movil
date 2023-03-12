package com.sensor.app.sensor_app_movil.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sensor.app.sensor_app_movil.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Entity
@Table(name = "Device")
@Getter
@Setter
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_Device")
    private Long idDevice;

    @Column(name="device_code", length = 90, unique = true, nullable = false)
    private String deviceCode;

    @Column(name="name", length = 90, nullable = false)
    private String name;


    @Column(name="password", length = 75, nullable = false)
    private String password;

    @Column(name="is_on", nullable = false, columnDefinition ="TINYINT(1) DEFAULT 0")
    private boolean isOn;

    @Column(name = "create_date",insertable = false,  updatable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar created;

    @Column(name = "update_date", insertable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar updated;

    @Column(name = "FK_User")
    private Integer userId;

    @Column(name = "FK_WiFi")
    private Integer wifiId;

    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_User", insertable = false )
    @JsonIgnore
    private User fkUser;

    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_WiFi", insertable = false )
    @JsonIgnore
    private WiFi fkWiFi;

}
