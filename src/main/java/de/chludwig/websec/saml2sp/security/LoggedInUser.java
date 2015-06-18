package de.chludwig.websec.saml2sp.security;

import java.util.Set;

/**
 * TODO; add documentation
 */
public class LoggedInUser extends ApplicationUser {

    public LoggedInUser(String userId, String userName, Set<RoleId> roles, AuthenticationStatus authenticationStatus) {
        super(userId, userName, roles, authenticationStatus);
        if (AuthenticationStatus.ANONYMOUS.equals(getAuthenticationStatus())) {
            throw new IllegalArgumentException("logged in users cannot be anonymous!");
        }
    }
}
