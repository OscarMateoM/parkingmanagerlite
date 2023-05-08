package com.hormigo.david.parkingmanager.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureTokenRepository extends JpaRepository<SecureToken,Long>{
    
    public Optional<SecureToken> findByToken(String token);
    public void removeByToken(String token);
}
