package com.sensor.app.sensor_app_movil.entity;


import com.sensor.app.sensor_app_movil.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Observer")
@Getter
@Setter
@NoArgsConstructor
/*Invitado*/
public class Observer {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_Observer")
    private Long idObserver;

    @ManyToOne
    @JoinColumn(name="FK_User")
    private User fkUser;

    @ManyToOne
    @JoinColumn(name="FK_Device")
    private Device fkDevice;

    @Column(name = "create_date",insertable = false,  updatable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    private LocalDateTime created;

    @Column(name = "update_date", insertable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    private LocalDateTime updated;



}
