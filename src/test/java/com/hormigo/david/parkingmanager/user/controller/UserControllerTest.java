package com.hormigo.david.parkingmanager.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import com.hormigo.david.parkingmanager.core.exceptions.EmailNotUpdatableException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.service.UserService;
import com.hormigo.david.parkingmanager.user.service.UserServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

// Test GET /api/users y GET /api/users/{id}
    @Test
    public void testSingleUserRead() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("dhorram948@g.educaand.es","David","Hormigo","Ramírez",Role.PROFESSOR);
        String json = mapper.writeValueAsString(user);
        when(userService.getUser(2)).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/api/users/2"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
                    
    }

    @Test
    public void testAllUserRead() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        User user = new User("dhorram948@g.educaand.es","David","Hormigo","Ramírez",Role.PROFESSOR);
        ArrayList<User> usuariso = new ArrayList<>();
        usuariso.add(user);
        String json = mapper.writeValueAsString(usuariso);
        json = "{ \"_embedded\": {\"userList\":" + json + "}}";
        when(userService.getAll()).thenReturn(usuariso);
        this.mockMvc.perform(get("/api/users"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
    }

// Test DELETE /api/users
    @Test
    public void testSingleUserDelete() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("dhorram948@g.educaand.es","David","Hormigo","Ramírez",Role.PROFESSOR);
        String json = mapper.writeValueAsString(user);
        when(userService.getUser(2)).thenReturn(Optional.of(user));
        this.mockMvc.perform(delete("/api/users/2"))
                    .andDo(print())
                    .andExpect(status().is(204));
                    
    }

// POST /api/users (test positivo y negativos)
    @Test
    public void testUserisCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("dhorram948@g.educaand.es", "David", "Hormigo", "Ramírez", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenReturn(new User("dhorram948@g.educaand.es","David","Hormigo","Ramírez",Role.PROFESSOR));
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().isCreated());
    }

    @Test
public void testEmailAlreadyExists() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    UserDao dao = new UserDao("dhorram948@g.educaand.es", "David", "Hormigo", "Ramírez", Role.PROFESSOR);
    String json = mapper.writeValueAsString(dao);
    when(this.userService.register(any(UserDao.class))).thenThrow(UserExistsException.class);
    this.mockMvc.perform(post("/api/users")
                .contentType("application/json").content(json))
                .andDo(print())
                .andExpect(status().is(406));
}




@Test
public void testEmailNull() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    UserDao dao = new UserDao(null, "David", "Hormigo", "Ramírez", Role.PROFESSOR);
    String json = mapper.writeValueAsString(dao);

    this.mockMvc.perform(post("/api/users")
            .content(json)
            .contentType("application/json"))
            .andDo(print())
            .andExpect(status().is(422));
}

@Test
public void testNameNull() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    UserDao dao = new UserDao("dhorram948@g.educaand.es", null, "Hormigo", "Ramírez", Role.PROFESSOR);
    String json = mapper.writeValueAsString(dao);

    this.mockMvc.perform(post("/api/users")
            .content(json)
            .contentType("application/json"))
            .andDo(print())
            .andExpect(status().is(422));
}

@Test
public void testLastNameNull() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    UserDao dao = new UserDao("dhorram948@g.educaand.es", "David", null, "Ramírez", Role.PROFESSOR);
    String json = mapper.writeValueAsString(dao);

    this.mockMvc.perform(post("/api/users")
            .content(json)
            .contentType("application/json"))
            .andDo(print())
            .andExpect(status().is(422));
}

// Test PATCH /api/users/{id}
@Test
public void testPatchUser() throws Exception {

    ObjectMapper mapper = new ObjectMapper();

    User user = new User("dhorram948@g.educaand.es", null, "Hormigo", "Ramírez", Role.PROFESSOR);

    Map<String, Object> updatedData = new HashMap<>();
    updatedData.put("name", "David");
    updatedData.put("lastName1", "Hormigos");
    updatedData.put("lastName2", "Ramírez");
    updatedData.put("role", "PROFESSOR");

    user.setName("Oscar");

    String json = mapper.writeValueAsString(user);
    String updatedDataJson = mapper.writeValueAsString(updatedData);

    when(userService.updateUser(2, updatedData)).thenReturn(user);

    this.mockMvc.perform(patch("/api/users/2")
            .content(updatedDataJson)
            .contentType("application/json"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(json));

}



}

