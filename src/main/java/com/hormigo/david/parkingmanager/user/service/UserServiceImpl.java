package com.hormigo.david.parkingmanager.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.domain.UserRepository;
@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<User> getAll() {

        return this.repository.findAll();
    }

    public void register(UserDao userDao) throws UserExistsException {
        if (userExists(userDao.getEmail())){
            throw new UserExistsException();
        }
        User user = new User();
        
        BeanUtils.copyProperties(userDao, user);
        this.repository.save(user);
    }

    @Override
    public boolean userExists(String email) {
        return getByEmail(email) != null ? true : false;

    }

    @Override
    public User getByEmail(String email) throws UserDoesNotExistsException {
        return this.repository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
    }

    

}
