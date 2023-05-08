package com.hormigo.david.parkingmanager.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hormigo.david.parkingmanager.core.email.service.EmailService;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.domain.UserRepository;
@SpringBootTest
public class UserServiceImplTest {
    @MockBean
    private EmailService mockedEmailService;
    @MockBean
    private UserRepository mockedRepository;
    @Autowired
    private UserService service;
    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAll() {
        final List<User> expected = new ArrayList<>();
        expected.add(new User("david@correo", "David", "Hormigo", "Ramirez", Role.PROFESSOR));
        //final UserRepository mockedRepository = mock(UserRepository.class);
        when(mockedRepository.findAll()).thenReturn(expected);
        //final UserService service = new UserServiceImpl(mockedRepository,encoder);
        final List<User> actual = (List<User>) service.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void testUserDoesNotExists() {
        // Arrange
        //UserRepository mockRepository = mock(UserRepository.class);
        UserDao userDao = new UserDao("david@correo", "1234","David", "Hormigo", "Ramírez", Role.PROFESSOR);
        when(mockedRepository.findByEmail("david@correo")).thenReturn(Optional.empty());
        //UserService service = new UserServiceImpl(mockRepository,encoder);

        // Act
        try {
           this.service.register(userDao);
        } catch (UserExistsException exception) {
            fail();
        }
        verify(mockedRepository).save(any(User.class));

    }

    @Test
    void testUserAlreadyExists() {
        // Arrange
        //UserRepository mockRepository = mock(UserRepository.class);
        UserDao userDao = new UserDao("david@correo", "1234", "David", "Hormigo", "Ramírez", Role.PROFESSOR);
        when(mockedRepository.findByEmail("david@correo"))
                .thenReturn( Optional.of(new User("david@correo", "1234","David", "Hormigo", "Ramírez", Role.PROFESSOR)));

        //UserService service = new UserServiceImpl(mockRepository,encoder);
        // Act y assert
        assertThrows(UserExistsException.class,
                () -> {
                    service.register(userDao);
                });

    }

    @Test
    void userDoesNotExists(){
      // Arrange
      //UserRepository mockRepository = mock(UserRepository.class);
      //UserDao userDao = new UserDao("david@correo", "David", "Hormigo", "Ramírez", Role.PROFESSOR);
      when(mockedRepository.findByEmail("david@correo")).thenReturn(Optional.empty());

      //UserService service = new UserServiceImpl(mockRepository,encoder);

      assertFalse(service.userExists("david@correo"));
    }

     @Test
     void userExists() {
        // Arrange
        //UserRepository mockRepository = mock(UserRepository.class);
        //UserDao userDao = new UserDao("david@correo", "David", "Hormigo", "Ramírez", Role.PROFESSOR);
        when(mockedRepository.findByEmail("david@correo"))
                .thenReturn(Optional.of(new User("david@correo", "1234","David", "Hormigo", "Ramírez", Role.PROFESSOR)));
        
        //UserService service = new UserServiceImpl(mockRepository,encoder);
        assertTrue(service.userExists("david@correo"));
     }

}
