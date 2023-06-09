package com.sensor.app.sensor_app_movil.mappers;

import com.sensor.app.sensor_app_movil.dto.informativemessage.request.AddInformativeMessageRequest;
import com.sensor.app.sensor_app_movil.dto.informativemessage.response.InformativeMessageResponse;
import com.sensor.app.sensor_app_movil.entity.InformativeMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface InformativeMessageMapper {


    @Mappings({
            @Mapping(source = "message", target = "message"),
            @Mapping(source = "created", target = "created"),
    })
    InformativeMessageResponse toInformativeMessageResponse(InformativeMessage informativeMessage);



}
