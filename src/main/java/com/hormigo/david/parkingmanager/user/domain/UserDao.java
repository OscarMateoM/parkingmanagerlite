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
    private boolean expired;
    private boolean locked;
    /**
     * 
     */
    public UserDao(){
        this("","","","","",null);
    }
    /**
     * @param email
     * @param password
     * @param name
     * @param lastName1
     * @param lastName2
     * @param role
     */
    public UserDao(String email, String password,String name, String lastName1, String lastName2, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.role = role;
    }
    /**
     * @return
     */
    public boolean isExpired() {
        return expired;
    }
    /**
     * @param expired
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    /**
     * @return
     */
    public boolean isLocked() {
        return locked;
    }
    /**
     * @param locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    /**
     * @return
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return
     */
    public String getLastName1() {
        return lastName1;
    }
    /**
     * @param lastName1
     */
    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }
    /**
     * @return
     */
    public String getLastName2() {
        return lastName2;
    }
    /**
     * @param lastName2
     */
    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }
    /**
     * @return
     */
    public Role getRole() {
        return role;
    }
    /**
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
