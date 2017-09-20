package ta.microservices.authentication.service.interfaces;

public interface IUserDatabase {
    public String getValue();
    public String[] getRolesForUser(String username, String password);
}
