/*
 * Copyright 2015 Christoph Ludwig
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
