package ta.microservices.authentication.api;

import ta.microservices.authentication.api.dto.AdminUserStatus;
import ta.microservices.authentication.api.dto.AuthenticationRequest;
import ta.microservices.authentication.api.dto.AuthenticationResponse;
import ta.microservices.authentication.api.dto.UserStatus;
import ta.microservices.common.service.security.AuthenticationRole;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
public interface IAuthenticationService {
    @GET
    @Path("getAdminUserStatus")
    @RolesAllowed({AuthenticationRole.Admin})
    AdminUserStatus getAdminUserStatus();

    @GET
    @Path("getUserStatus")
    @RolesAllowed({AuthenticationRole.Admin, AuthenticationRole.User})
    UserStatus getUserStatus();

    @POST
    @Path("login")
    @RolesAllowed({AuthenticationRole.Any})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
