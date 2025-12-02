package com.tour_web_app.dto.mapper;

import com.tour_web_app.dto.request.RegistrationRequest;
import com.tour_web_app.dto.response.RegistrationResponse;
import com.tour_web_app.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    RegistrationResponse toRegistrationResponse(User user);
    User toUserEntity(RegistrationRequest registrationRequest);
}
