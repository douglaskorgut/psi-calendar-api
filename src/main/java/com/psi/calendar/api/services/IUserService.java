package com.psi.calendar.api.services;

import com.psi.calendar.api.exceptions.UserNotFoundException;
import com.psi.calendar.api.models.entity.User;

public interface IUserService {
    User findUser(String username) throws UserNotFoundException;
}
