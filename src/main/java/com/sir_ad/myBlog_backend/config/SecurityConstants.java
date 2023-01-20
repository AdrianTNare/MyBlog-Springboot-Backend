package com.sir_ad.myBlog_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties("app.property")
public class SecurityConstants {

    private String signUpUrl;

    private String authKey;

    private String headerName;

    private Long  expirationTime;


    public String getSignUpUrl() {
        return signUpUrl;
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
