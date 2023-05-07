package com.hormigo.david.parkingmanager.user.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    public Optional<User> findByEmail(String email);

}
    
