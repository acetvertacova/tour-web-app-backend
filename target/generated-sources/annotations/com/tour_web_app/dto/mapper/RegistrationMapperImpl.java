package com.tour_web_app.dto.mapper;

import com.tour_web_app.dto.request.RegistrationRequest;
import com.tour_web_app.dto.response.RegistrationResponse;
import com.tour_web_app.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T20:22:52+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class RegistrationMapperImpl implements RegistrationMapper {

    @Override
    public RegistrationResponse toRegistrationResponse(User user) {
        if ( user == null ) {
            return null;
        }

        String username = null;
        String email = null;

        username = user.getUsername();
        email = user.getEmail();

        RegistrationResponse registrationResponse = new RegistrationResponse( username, email );

        return registrationResponse;
    }

    @Override
    public User toUserEntity(RegistrationRequest registrationRequest) {
        if ( registrationRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( registrationRequest.username() );
        user.email( registrationRequest.email() );
        user.password( registrationRequest.password() );

        return user.build();
    }
}
