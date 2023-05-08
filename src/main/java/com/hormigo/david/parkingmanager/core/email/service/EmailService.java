package com.hormigo.david.parkingmanager.core.email.service;

import com.hormigo.david.parkingmanager.core.email.AbstractEmailContext;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendMail(final AbstractEmailContext email) throws MessagingException;
}
