package ta.microservices.common.service.security;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class TokenUtilTest {
    private TokenUtil CreateToken() {
        Calendar c = GregorianCalendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        c.set(2020, 1, 1);
        return new TokenUtil("username", new String[] { "ADMIN"}, 1, c.getTime());
    }

    @Test
    public void ValidTokenCreated() {
        TokenUtil tu = CreateToken();
        assertEquals(207, tu.getTokenString().length());
        assertNull(tu.getName());
        assertArrayEquals(new String[]{}, tu.getRoles());
        assertEquals(-1, tu.getVersion());
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidUsername() {
        new TokenUtil(null, null, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidRoles() {
        new TokenUtil("username", null, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidDate() {
        new TokenUtil("username", new String[0], 0, null);
    }

    @Test(expected = MalformedJwtException.class)
    public void InvalidString() {
        new TokenUtil("xyz");
    }
}
