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

import java.util.Set;

/**
 * Application-specific representation of a logged in user.
 *
 * In particular, the {@link #getAuthenticationStatus() authentication status} of a
 * {@link LoggedInUser logged in user} will never be {@link AuthenticationStatus#ANONYMOUS ANONYMOUS}.
 */
public class LoggedInUser extends ApplicationUser {

    public LoggedInUser(String userId, String userName, Set<RoleId> roles, AuthenticationStatus authenticationStatus) {
        super(userId, userName, roles, authenticationStatus);
        if (AuthenticationStatus.ANONYMOUS.equals(getAuthenticationStatus())) {
            throw new IllegalArgumentException("logged in users cannot be anonymous!");
        }
    }
}
