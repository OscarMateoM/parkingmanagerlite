package com.hormigo.david.parkingmanager.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.domain.UserRepository;
@Service
public class UserServiceImpl implements UserService {

    
    private PasswordEncoder encoder;
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository,PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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
        user.setPassword(encoder.encode(userDao.getPassword()));

        this.repository.save(user);
    }

    @Override
    public boolean userExists(String email) {
        return this.repository.findByEmail(email).orElse(null) != null ? true : false;

    }

    @Override
    public User getByEmail(String email) throws UserDoesNotExistsException {
        return this.repository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
    }

    

}
