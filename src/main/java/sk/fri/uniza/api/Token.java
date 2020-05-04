package sk.fri.uniza.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("token")
    private String token;

    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }
}
