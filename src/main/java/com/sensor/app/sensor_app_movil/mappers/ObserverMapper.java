package com.sensor.app.sensor_app_movil.mappers;

import com.sensor.app.sensor_app_movil.dto.observer.response.ObserverResponse;
import com.sensor.app.sensor_app_movil.entity.Observer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ObserverMapper {


    @Mappings({
            @Mapping(source = "fkUser.name", target = "name"),
            @Mapping(source = "fkUser.lastname", target = "lastname"),
            @Mapping(source = "fkUser.email", target = "email"),
            @Mapping(source = "created", target = "created"),
    })
    ObserverResponse toObserverResponse(Observer observer);

}
