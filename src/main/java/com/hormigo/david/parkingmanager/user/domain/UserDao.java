package com.hormigo.david.parkingmanager.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class UserDao {
    @Email(message = "El formato del corrreo no es aducado")
    @NotBlank(message = "El correo noo puede ser vacio")
    private String email;
    @NotBlank(message="La contraseña no puede ser vacia")
    private String password;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El primer apellido es obligatorio")
    private String lastName1;
    
    private String lastName2;
    private Role role;
    public UserDao(){
        this("","","","","",null);
    }
    public UserDao(String email, String password,String name, String lastName1, String lastName2, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.role = role;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName1() {
        return lastName1;
    }
    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }
    public String getLastName2() {
        return lastName2;
    }
    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
