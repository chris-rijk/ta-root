package ta.microservices.authentication.service;

import ta.microservices.authentication.api.IAuthenticationService;
import ta.microservices.authentication.api.dto.AdminUserStatus;
import ta.microservices.authentication.api.dto.AuthenticationRequest;
import ta.microservices.authentication.api.dto.AuthenticationResponse;
import ta.microservices.authentication.api.dto.UserStatus;
import ta.microservices.authentication.service.interfaces.IUserDatabase;
import ta.microservices.common.service.security.LoginUser;
import ta.microservices.common.service.security.TokenUtil;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;

public class AuthenticationService implements IAuthenticationService {
    @Context
    IUserDatabase ij;
    @Context
    private SecurityContext securityContext;

    private LoginUser checkUser() {
        LoginUser user = (LoginUser) securityContext.getUserPrincipal();
        if (user == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        return user;
    }

    @Override
    public AdminUserStatus getAdminUserStatus() {
        LoginUser user = checkUser();
        return new AdminUserStatus(user.getName(), user.getExpiration(), user.getRoles());
    }

    @Override
    public UserStatus getUserStatus() {
        LoginUser user = checkUser();
        return new UserStatus(user.getName(), user.getExpiration());
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        String[] roles = ij.getRolesForUser(username, password);

        if (roles != null) {
            TokenUtil tu = new TokenUtil(username, roles, 0, new Date(System.currentTimeMillis() + 100000));
            return new AuthenticationResponse(username, tu.getTokenString());
        }
        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }
}
