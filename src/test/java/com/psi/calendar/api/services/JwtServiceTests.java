package com.psi.calendar.api.services;

import com.psi.calendar.api.services.impl.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static com.psi.calendar.api.constants.JwtTestConstants.VALID_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class JwtServiceTests {

    private final IJwtService sut = new JwtServiceImpl();

    @Test
    public void itShouldCreateJwt() {
        // Create jwt
        var jwt = this.sut.generateToken(new User(
                "foo",
                "foo",
                new ArrayList<>()
        ));

        // Check if created jwt is not empty
        assertThat(jwt).isNotEmpty();
    }

    @Test
    public void itShouldExtractUsernameFromJwt() {
        var expectedUserName = "foo";
        var actualUsername = this.sut.extractUsername(VALID_TOKEN);

        assertThat(actualUsername).isEqualTo(expectedUserName);
    }

}
