package authentication;

public interface Authenticator {

    public boolean login(final String username, final String password);

}
