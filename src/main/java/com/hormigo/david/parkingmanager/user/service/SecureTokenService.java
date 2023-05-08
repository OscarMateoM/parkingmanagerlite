package com.hormigo.david.parkingmanager.user.service;

import com.hormigo.david.parkingmanager.user.domain.SecureToken;

public interface SecureTokenService {
    SecureToken createSecureToken();
    void saveSecureToken(final SecureToken token);
    void save(final SecureToken token);
    SecureToken findByToken(final String token);
    void removeToken(final SecureToken token);
    void removeTokenByToken(final String token);
}
