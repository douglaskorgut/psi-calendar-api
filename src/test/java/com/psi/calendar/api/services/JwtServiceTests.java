package com.psi.calendar.api.services;

import com.psi.calendar.api.services.impl.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class JwtServiceTests {

    private final IJwtService sut = new JwtServiceImpl();

    @Test
    public void itShouldCreateJwt(){
        var jwt = this.sut.generateToken(new User(
                "foo",
                "foo",
                new ArrayList<>()
        ));

        assertThat(jwt).isNotEmpty();
    }

}
