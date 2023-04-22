package com.sensor.app.sensor_app_movil.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Entity
@Table(name = "InformativeMessage")
@Getter
@Setter
@NoArgsConstructor
public class InformativeMessage {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_InformativeMessage")
    private Long idInformativeMessage;

    @Column(name="message",columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "create_date",insertable = false,  updatable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar created;

    @Column(name = "update_date", insertable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar updated;

    @ManyToOne
    @JoinColumn(name = "FK_Device", nullable = false)
    @JsonIgnore
    private Device fkDevice;



}
