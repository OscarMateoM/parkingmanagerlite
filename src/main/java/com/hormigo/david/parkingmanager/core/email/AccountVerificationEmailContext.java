package com.hormigo.david.parkingmanager.core.email;

import org.springframework.web.util.UriComponentsBuilder;

import com.hormigo.david.parkingmanager.user.domain.User;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;
    @Override
    public <T> void init(T context) {
        User user = (User) context; // we pass the customer informati
        put("name", user.getName());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("no-reply@loscamaleones.com");
        setTo(user.getEmail());
    }
    
    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
