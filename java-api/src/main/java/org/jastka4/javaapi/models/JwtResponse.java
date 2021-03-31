package org.jastka4.javaapi.models;


import java.io.Serializable;

public class JwtResponse implements Serializable {

    private final String jwtToken;

    public JwtResponse(final String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}
