package com.hormigo.david.parkingmanager.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hormigo.david.parkingmanager.core.email.AccountVerificationEmailContext;
import com.hormigo.david.parkingmanager.core.email.service.EmailService;
import com.hormigo.david.parkingmanager.core.email.AccountVerificationEmailContext;
import com.hormigo.david.parkingmanager.core.email.service.EmailService;
import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.SecureToken;
import com.hormigo.david.parkingmanager.user.domain.SecureTokenRepository;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.domain.UserRepository;

import jakarta.mail.MessagingException;
@Service
public class UserServiceImpl implements UserService {
    // TODO: Marcar baseUrl
    private String baseUrl = "";
    @Autowired
    private EmailService emailService;
    private PasswordEncoder encoder;
    private UserRepository userRepository;
    private SecureTokenService secureTokenService;

    public UserServiceImpl(SecureTokenService tokenService, UserRepository userRepository,PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.secureTokenService = tokenService;
        this.encoder = encoder;
    }

    @Override
    public Iterable<User> getAll() {

        return this.userRepository.findAll();
    }

    public void register(UserDao userDao) throws UserExistsException {
        if (userExists(userDao.getEmail())){
            throw new UserExistsException();
        }
        User user = new User();
        
        BeanUtils.copyProperties(userDao, user);
        user.setPassword(encoder.encode(userDao.getPassword()));

        this.userRepository.save(user);
    }

    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenService.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseUrl, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean userExists(String email) {
        return this.userRepository.findByEmail(email).orElse(null) != null ? true : false;

    }

    @Override
    public User getByEmail(String email) throws UserDoesNotExistsException {
        return this.userRepository.findByEmail(email).orElseThrow(UserDoesNotExistsException::new);
    }

    

}
