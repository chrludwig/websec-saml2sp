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

import java.util.HashMap;
import java.util.Map;

/**
 * TODO; add documentation
 */
public enum RoleId {
    ANONYMOUS_ROLE_ID("ANONYMOUS"),
    USER_ROLE_ID("USER"),
    EDITOR_ROLE_ID("EDITOR"),
    ADMIN_ROLE_ID("ADMIN");

    private final String identifier;

    private static final Map<String, RoleId> ID_VALUE_MAP = new HashMap<>();

    static {
        for (RoleId value : RoleId.values()) {
            ID_VALUE_MAP.put(value.getId(), value);
        }
    }

    public static RoleId valueById(String identifier) {
        return ID_VALUE_MAP.get(identifier);
    }

    RoleId(String identifier) {
        this.identifier = identifier;
    }

    public String getId() {
        return identifier;
    }

    public boolean equalsId(String otherId) {
        return identifier.equals(otherId);
    }

    @Override
    public String toString() {
        return "RoleId{'" + identifier + "'}";
    }
}
