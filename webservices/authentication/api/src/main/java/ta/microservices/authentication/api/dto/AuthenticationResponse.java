package ta.microservices.authentication.api.dto;

public class AuthenticationResponse {
    public AuthenticationResponse() {}

    public AuthenticationResponse(String username, String tokenString) {
        this();
        this.username = username;
        this.tokenString = tokenString;
    }

    private String username;
    private String tokenString;

    public String getUsername() {
        return username;
    }

    public String getTokenString() {
        return tokenString;
    }
}
