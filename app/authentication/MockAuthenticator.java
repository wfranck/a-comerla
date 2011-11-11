package authentication;

public class MockAuthenticator implements Authenticator {

    @Override
    public boolean login(final String username, final String password) {
        return true;
    }

}
