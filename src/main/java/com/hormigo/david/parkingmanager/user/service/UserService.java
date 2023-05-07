package com.hormigo.david.parkingmanager.user.service;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;

public interface UserService {
    public Iterable<User> getAll();
    public void register(UserDao userDao) throws UserExistsException;
    public boolean userExists(String email);
    public User getByEmail(String email) throws UserDoesNotExistsException;
}
