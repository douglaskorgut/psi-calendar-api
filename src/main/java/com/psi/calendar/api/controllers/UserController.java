package com.psi.calendar.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psi.calendar.api.exceptions.UserNotFoundException;
import com.psi.calendar.api.models.dto.user.UserDTO;
import com.psi.calendar.api.models.entity.Role;
import com.psi.calendar.api.services.IUserService;
import com.psi.calendar.api.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class UserController {

    private final IUserService userService;
    private final ObjectMapper mapper = new ObjectMapper();

    public UserController(final UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<String> findUser(@RequestParam String username) throws UserNotFoundException, JsonProcessingException {
        var user = this.userService.findUser(username);
        var userDTO = UserDTO.builder()
                .username(user.getUsername())
                .enabled(user.getEnabled())
                .roles(
                        user
                                .getRoles()
                                .stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.toList())
                ).build();

        return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(userDTO));
    }
}
