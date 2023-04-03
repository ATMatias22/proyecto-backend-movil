package com.sensor.app.sensor_app_movil.security.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User implements Cloneable {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="ID_User")
    private Long idUser;

    @Column(name="name", length = 60, nullable = false)
    private String name;

    @Column(name="lastname", length = 65, nullable = false)
    private String lastname;

    @Column(name="email", length = 150, nullable = false)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
    private Calendar dateOfBirth;

    @Column(name="nationality", length = 60, nullable = false)
    private String nationality;

    @Column(name="password", length = 75, nullable = false)
    private String password;

    @Column(name = "create_date",insertable = false,  updatable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar created;

    @Column(name = "update_date", insertable = false, nullable = false, columnDefinition="DATETIME default NOW()")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private Calendar updated;

    private Boolean enabled = false;


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
