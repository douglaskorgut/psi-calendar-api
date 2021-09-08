package com.psi.calendar.api.services;

import com.psi.calendar.api.exceptions.UserNotFoundException;
import com.psi.calendar.api.repository.UserRepository;
import com.psi.calendar.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl sut;

    @Test
    public void itShouldThrowUserNoFoundException_whenNoUserIsFoundWithPassedIsUsername() {
        Mockito.when(this.userRepository.findUserByUsername(any())).thenReturn(null);
        var username = "invalid_user";
        var thrown = assertThrows(
                UserNotFoundException.class,
                () -> this.sut.findUser(username)
        );

        var expected = new UserNotFoundException("Couldn't find user with given username: " +  username);

        assertThat(thrown.getMessage()).isEqualTo(expected.getMessage());
    }
}
