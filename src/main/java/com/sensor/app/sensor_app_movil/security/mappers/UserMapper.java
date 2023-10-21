package com.sensor.app.sensor_app_movil.security.mappers;


import com.sensor.app.sensor_app_movil.security.dto.user.request.LoginUserRequest;
import com.sensor.app.sensor_app_movil.security.dto.user.request.ModifyDataRequest;
import com.sensor.app.sensor_app_movil.security.dto.user.request.NewUserRequest;
import com.sensor.app.sensor_app_movil.security.dto.user.response.UserLoggedInResponse;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.utils.date.StringToLocalDateAndViceVersa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected StringToLocalDateAndViceVersa stdv;

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(source = "email", target = "email"),
            @Mapping(target = "dateOfBirth", expression = "java(stdv.getString(user.getDateOfBirth()))" ),
            @Mapping(source = "country", target = "country"),
    })
    public abstract UserLoggedInResponse userEntityToUserLoggedInResponse(User user);


    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(source = "email", target = "email"),
            @Mapping(target = "dateOfBirth", expression = "java(stdv.getLocalDate(modifyDataRequest.getDateOfBirth()))" ),
            @Mapping(source = "country", target = "country"),
    })
    public abstract User modifyDataRequestToUserEntity(ModifyDataRequest modifyDataRequest);


    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(target = "dateOfBirth", expression = "java(stdv.getLocalDate(newUser.getDateOfBirth()))" ),
            @Mapping(source = "country", target = "country"),
    })
    public abstract User newUserRequestToUserEntity(NewUserRequest newUser);


    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
    })
    public abstract User loginUserRequestToUserEntity(LoginUserRequest loginUser);

}
