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

import java.util.Collections;
import java.util.UUID;

/**
 * {@link ApplicationUser} specialization that represents an anonymous user.
 *
 * All instances of {@link AnonymousUser} share the same {@link #getUserId() user id}; therefore, any two
 * anonymous user objects compare {@link #equals(Object) equal}.
 */
public class AnonymousUser extends ApplicationUser {

    public static final String ANONYMOUS_USER_ID = UUID.nameUUIDFromBytes("Saml2SPAnonymousUser".getBytes()).toString();
    public static final String ANONYMOUS_USER_NAME = "anonymous";

    public AnonymousUser() {
        super(ANONYMOUS_USER_ID, ANONYMOUS_USER_NAME, Collections.singleton(RoleId.ANONYMOUS_ROLE_ID),
              AuthenticationStatus.ANONYMOUS);
    }
}
