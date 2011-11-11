/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package authentication;

import java.util.List;

import javax.naming.NamingException;

import models.User;

import org.apache.commons.lang.UnhandledException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;


/**
 *
 * @author Martin Silva
 * @since Oct 21, 2011
 */
public class LDAPAuthenticator {

    private final LdapTemplate ldapTemplate;


    /**
     * Creates the LDAPAuthenticator.
     *
     */
    public LDAPAuthenticator(final LdapTemplate template) {
        this.ldapTemplate = template;
    }


    public boolean login(final String username, final String password){
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("uid", username));
        boolean authenticate = this.ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
        if (authenticate) {
            List<String> results = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.toString(), new ContextMapper() {

                @Override
                public Object mapFromContext(final Object arg0) {
                    try {
                        return ((org.springframework.ldap.core.DirContextAdapter)arg0).getAttributes().get("mail").get();
                    } catch (NamingException e) {
                        throw new UnhandledException(e);
                    }
                }
            });
            if (results.isEmpty()) {
                return false;
            }
            String mail = results.get(0);
            User user = User.findByEmail(mail);
            if (user == null) {
                user = new User(mail, username);
                if (!user.validateAndCreate()) {
                    throw new IllegalStateException("Cannot save user");
                }
            }
        }
        return authenticate;

    }

}
