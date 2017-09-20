package ta.microservices.authentication.api.dto;

import java.util.Date;

public class AdminUserStatus extends UserStatus {
    public AdminUserStatus() {}

    public AdminUserStatus(String username, Date expiration, String[] roles) {
        super(username, expiration);
        this.roles = roles;
    }

    private String[] roles;

    public String[] getRoles() {
        return roles;
    }
}
