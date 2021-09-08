package com.psi.calendar.api.services.impl;

import com.psi.calendar.api.exceptions.UserNotFoundException;
import com.psi.calendar.api.models.entity.User;
import com.psi.calendar.api.repository.UserRepository;
import com.psi.calendar.api.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String username) throws UserNotFoundException {
        try {
            // Find user on database
            var user = this.userRepository.findUserByUsername(username);

            // If no user found, throw
            if (user == null) throw new Exception();

            return user;
        } catch (Exception e){
            throw new UserNotFoundException("Couldn't find user with given username: " + username);
        }
    }
}