package ta.microservices.authentication.api.dto;

import java.util.Date;

public class UserStatus {
    private String username;
    private Long expiresIn;

    public UserStatus() {
    }

    public UserStatus(String username, Date expiration) {
        this();
        this.username = username;
        this.expiresIn = (expiration == null)? null: expiration.getTime() - System.currentTimeMillis();
    }

    public String getUsername() {
        return username;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }
}
