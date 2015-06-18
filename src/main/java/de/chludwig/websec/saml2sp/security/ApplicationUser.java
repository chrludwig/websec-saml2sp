package de.chludwig.websec.saml2sp.security;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static de.chludwig.websec.saml2sp.security.AuthenticationStatus.ANONYMOUS;
import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * TODO; add documentation
 */
public abstract class ApplicationUser {

    private final String userId;
    private final String userName;
    private final Set<RoleId> roles;
    private final AuthenticationStatus authenticationStatus;

    protected ApplicationUser(String userId, String userName, Set<RoleId> roleIds,
                              AuthenticationStatus authenticationStatus) {
        this.userId = defaultString(userId);
        this.userName = defaultString(userName);
        this.roles = (roleIds == null) ? Collections.<RoleId>emptySet() : Collections.unmodifiableSet(roleIds);
        this.authenticationStatus = (authenticationStatus == null) ? ANONYMOUS : authenticationStatus;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Set<RoleId> getRoles() {
        return roles;
    }

    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }

    public boolean isAnonymous() {
        return AuthenticationStatus.ANONYMOUS.equals(authenticationStatus);
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", roles={" + StringUtils.join(roles, ", ") + '}' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        ApplicationUser that = (ApplicationUser) o;
        return Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
