/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package authentication;

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
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("cn", username));

        return this.ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
    }

}
