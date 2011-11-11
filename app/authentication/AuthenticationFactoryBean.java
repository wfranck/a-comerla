package authentication;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.ldap.core.LdapTemplate;

public class AuthenticationFactoryBean extends AbstractFactoryBean<Authenticator> {

    @Override
    public Class<?> getObjectType() {
        return Authenticator.class;
    }

    private boolean mock;
    private LdapTemplate template;

    @Override
    protected Authenticator createInstance() throws Exception {
        if (!isMock()) {
            return new LDAPAuthenticator(template);
        } else {
            return new MockAuthenticator();
        }
    }

    public LdapTemplate getTemplate() {
        return template;
    }

    public void setTemplate(final LdapTemplate template) {
        this.template = template;
    }

    public boolean isMock() {
        return mock;
    }

    public void setMock(final boolean mock) {
        this.mock = mock;
    }
}
