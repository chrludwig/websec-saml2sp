package de.chludwig.websec.saml2sp.security;

import java.util.Collections;
import java.util.UUID;

/**
 * TODO; add documentation
 */
public class AnonymousUser extends ApplicationUser {

    public static final String ANONYMOUS_USER_ID = UUID.nameUUIDFromBytes("Saml2SPAnonymousUser".getBytes()).toString();
    public static final String ANONYMOUS_USER_NAME = "anonymous";

    public AnonymousUser() {
        super(ANONYMOUS_USER_ID, ANONYMOUS_USER_NAME, Collections.singleton(RoleId.ANONYMOUS_ROLE_ID),
              AuthenticationStatus.ANONYMOUS);
    }
}
