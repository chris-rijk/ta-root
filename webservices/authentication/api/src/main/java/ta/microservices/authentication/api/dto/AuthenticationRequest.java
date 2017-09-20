package ta.microservices.authentication.api.dto;

public class AuthenticationRequest {
    public AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    private String username, password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
