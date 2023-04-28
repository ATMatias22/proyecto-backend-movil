package com.sensor.app.sensor_app_movil.security.mappers;


import com.sensor.app.sensor_app_movil.security.dto.userDTO.LoginUser;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.ModifyDataRequest;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.NewUser;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.UserLoggedInResponse;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.utils.date.StringToLocalDateAndViceVersa;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserMapper {

    @Autowired
    protected StringToLocalDateAndViceVersa stdv;

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(source = "email", target = "email"),
            @Mapping(target = "dateOfBirth", expression = "java(stdv.getString(user.getDateOfBirth()))" ),
            @Mapping(source = "nationality", target = "nationality"),
    })
    public abstract UserLoggedInResponse userEntityToUserLoggedInResponse(User user);


    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(source = "email", target = "email"),
            @Mapping(target = "dateOfBirth", expression = "java(stdv.getLocalDate(modifyDataRequest.getDateOfBirth()))" ),
            @Mapping(source = "nationality", target = "nationality"),
    })
    public abstract User modifyDataRequestToUserEntity(ModifyDataRequest modifyDataRequest);


    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(target = "dateOfBirth", expression = "java(stdv.getLocalDate(newUser.getDateOfBirth()))" ),
            @Mapping(source = "nationality", target = "nationality"),
    })
    public abstract User newUserRequestToUserEntity(NewUser newUser);


    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
    })
    public abstract User loginUserRequestToUserEntity(LoginUser loginUser);

}
