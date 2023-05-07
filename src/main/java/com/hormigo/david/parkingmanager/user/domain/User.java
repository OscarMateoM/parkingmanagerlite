package com.hormigo.david.parkingmanager.user.domain;

import jakarta.persistence.Column;
import java.util.Set;

import com.hormigo.david.parkingmanager.draw.domain.Draw;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String lastName1;
    private String lastName2;
    private Role role;
    private boolean expired;
    private boolean locked;
    private boolean verified;
    
    @ManyToMany
    @JoinTable(name = "users_included",
    joinColumns = @JoinColumn(name="user_id"),
     inverseJoinColumns = @JoinColumn(name="draw_id"))
    private Set<Draw> includedIn;
    
    /**
     * @param email
     * @param password
     * @param name
     * @param lastName1
     * @param role
     */
    public User(String email, String password, String name, String lastName1, Role role) {
        this(email,password,name,lastName1,"",role);
    }
    /**
     * @param email
     * @param password
     * @param name
     * @param lastName1
     * @param lastName2
     * @param role
     */
    public User(String email, String password, String name, String lastName1, String lastName2, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.role = role;
    }
    /**
     * 
     */
    public User() {
        this("","","","",null);
    }
    public boolean isExpired() {
        return expired;
    }
    /**
     * @param expired
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    public boolean isLocked() {
        return locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    public boolean isVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    

    /**
     * @return identif
     */
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
