package ta.microservices.authentication.service;

import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ta.microservices.authentication.api.IAuthenticationService;
import ta.microservices.authentication.api.dto.AdminUserStatus;
import ta.microservices.authentication.api.dto.AuthenticationRequest;
import ta.microservices.authentication.api.dto.AuthenticationResponse;
import ta.microservices.authentication.api.dto.UserStatus;
import ta.microservices.common.service.security.AuthenticationRole;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AuthenticationServiceTest extends TestBase {
    private IAuthenticationService getProxy(AuthenticationResponse auth) {
        MultivaluedHashMap<String,Object> headers = new MultivaluedHashMap<>();
        if (auth != null) {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+auth.getTokenString());
        }
        return WebResourceFactory.newResource(IAuthenticationService.class, target(), false, headers, Collections.emptyList(), new Form());
    }

    private AuthenticationResponse getAuthentication(String role, String username, String password) throws InterruptedException, ExecutionException {
        Mockito.doReturn(new String[] { role}).when(injectionMock).getRolesForUser(Mockito.any(), Mockito.any());

        AuthenticationRequest request = new AuthenticationRequest(username, password);
        return getProxy(null).login(request);
    }

    @Test
    public void getInvalidAuthentication() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("john.admin", "foobar");
        try {
            getProxy(null).login(request);
            Assert.fail();
        } catch (NotAuthorizedException ex) {
            assertEquals("HTTP 401 Unauthorized", ex.getMessage());
        }

        Mockito.verify(injectionMock, Mockito.times(1)).getRolesForUser("john.admin", "foobar");
    }

    @Test
    public void getAdminAuthentication() throws Exception {
        AuthenticationResponse response = getAuthentication(AuthenticationRole.Admin, "john.admin", "foobar");
        assertNotNull(response);

        Mockito.verify(injectionMock, Mockito.times(1)).getRolesForUser("john.admin", "foobar");
    }

    @Test
    public void getAuthenticationWithExistingInvalidAuthentication() throws Exception {
        Mockito.doReturn(new String[] { AuthenticationRole.Admin}).when(injectionMock).getRolesForUser(Mockito.any(), Mockito.any());
        AuthenticationRequest request = new AuthenticationRequest("john.admin", "foobar");
        AuthenticationResponse response = getProxy(new AuthenticationResponse()).login(request);
        assertNotNull(response);

        Mockito.verify(injectionMock, Mockito.times(1)).getRolesForUser("john.admin", "foobar");
    }

    @Test(expected = ForbiddenException.class)
    public void AnyUserWithoutAuthentication() throws Exception {
        getProxy(null).getUserStatus();
    }

    @Test(expected = ForbiddenException.class)
    public void AnyUserWithFakeAuthentication() throws Exception {
        AuthenticationResponse authResponse = new AuthenticationResponse("username", "token");
        getProxy(authResponse).getUserStatus();
    }

    @Test
    public void AnyUserWithNormalAuthentication() throws Exception {
        AuthenticationResponse authResponse = getAuthentication(AuthenticationRole.User, "john.doe", "wibble");
        UserStatus response = getProxy(authResponse).getUserStatus();
        assertNotNull(response);
        assertEquals("john.doe", response.getUsername());
        assertNotNull(response.getExpiresIn());
        Assert.assertTrue("expiration", response.getExpiresIn() > 1);
    }

    @Test
    public void AnyUserWithAdminAuthentication() throws Exception {
        AuthenticationResponse authResponse = getAuthentication(AuthenticationRole.Admin, "john.admin", "foobar");
        UserStatus response = getProxy(authResponse).getUserStatus();
        assertNotNull(response);
        assertEquals("john.admin", response.getUsername());
    }

    @Test(expected = ForbiddenException.class)
    public void AdminOnlyWithoutAuthentication() throws Exception {
        getProxy(null).getAdminUserStatus();
    }

    @Test(expected = ForbiddenException.class)
    public void AdminOnlyWithNormalAuthentication() throws Exception {
        AuthenticationResponse authResponse = getAuthentication(AuthenticationRole.User, "john.doe", "wibble");
        getProxy(authResponse).getAdminUserStatus();
    }

    @Test
    public void AdminOnlyWithAdminAuthentication() throws Exception {
        AuthenticationResponse authResponse = getAuthentication(AuthenticationRole.Admin, "john.admin", "wibble");
        AdminUserStatus response = getProxy(authResponse).getAdminUserStatus();
        assertNotNull(response);
        assertEquals("john.admin", response.getUsername());
        assertNotNull(response.getExpiresIn());
        Assert.assertTrue("expiration", response.getExpiresIn() > 1);
        assertNotNull(response.getRoles());
        assertArrayEquals(new String[] {AuthenticationRole.Admin }, response.getRoles());
    }
}