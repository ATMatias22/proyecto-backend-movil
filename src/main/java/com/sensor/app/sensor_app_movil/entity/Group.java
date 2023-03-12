package com.sensor.app.sensor_app_movil.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sensor.app.sensor_app_movil.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Entity
@Table(name = "Group")
@Getter
@Setter
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_Group")
    private Long idGroup;

    @Column(name = "FK_User")
    private Long userId;

    @ManyToOne
    @JoinColumn(name="FK_User", insertable = false, updatable = false)
    private User user;

    @Column(name = "FK_Device")
    private Long deviceId;

    @ManyToOne
    @JoinColumn(name="FK_Device", insertable = false, updatable = false)
    private Device device;

    @Column(name = "create_date",insertable = false,  updatable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar created;

    @Column(name = "update_date", insertable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar updated;



}
