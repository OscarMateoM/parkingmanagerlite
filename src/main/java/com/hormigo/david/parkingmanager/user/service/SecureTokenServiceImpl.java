package com.hormigo.david.parkingmanager.user.service;

import java.time.LocalDateTime;

import org.apache.tomcat.util.codec.binary.Base64;
import org.checkerframework.checker.units.qual.m;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.Charset;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

import com.hormigo.david.parkingmanager.user.domain.SecureToken;
import com.hormigo.david.parkingmanager.user.domain.SecureTokenRepository;

@Service
public class SecureTokenServiceImpl implements SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Value("${security.secure.token.validity}")
    private int tokenValidityInSeconds;
    private SecureTokenRepository repository;

    public SecureTokenServiceImpl(SecureTokenRepository repository){
        this.repository = repository;
    }

    @Override
    public SecureToken createSecureToken() {
        //String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        String tokenValue = Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey());
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveSecureToken(secureToken);
        return secureToken;

    }

    @Override
    public void saveSecureToken(SecureToken token) {
        this.repository.save(token);
    }

    @Override
    public SecureToken findByToken(String token) {
        return this.repository.findByToken(token).orElseThrow(RuntimeException::new);
    }

    @Override
    public void removeToken(SecureToken token) {
        this.repository.delete(token);
    }

    @Override
    public void removeTokenByToken(String token) {
        this.repository.removeByToken(token);
    }
    @Override
    public void save(SecureToken token){
        this.repository.save(token);
    }

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

}
