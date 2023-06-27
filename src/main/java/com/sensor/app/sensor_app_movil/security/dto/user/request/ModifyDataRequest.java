package com.sensor.app.sensor_app_movil.security.dto.user.request;

import com.sensor.app.sensor_app_movil.utils.date.validdate.ValidDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDataRequest {

    @Size(max = 50, message = "El nombre debe tener como maximo 50 caracteres")
    @NotBlank(message = "El nombre no puede ser nulo ni vacio")
    private String name;

    @Size(max = 50, message = "El apellido debe tener como maximo 50 caracteres")
    @NotBlank(message = "El apellido no puede ser nulo ni vacio")
    private String lastname;

    @NotBlank(message = "El email no puede ser nulo ni vacio")
    private String email;

    @NotBlank(message = "La nacionalidad no puede ser nulo ni vacio")
    private String nationality;

    @ValidDate(message =  "Fecha de nacimiento mal colocada")
    private String dateOfBirth;


}
