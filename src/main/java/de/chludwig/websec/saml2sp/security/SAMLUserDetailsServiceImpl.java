/*
 * Copyright 2014 Vincenzo De Notaris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package de.chludwig.websec.saml2sp.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * service that converts the credentials returned by an IdP after a successful SAML 2.0 Web SSO into
 * a {@link SamlUserDetails} object.
 */
@Service("samlUserDetailsService")
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);

    @Override
    public Object loadUserBySAML(SAMLCredential credential) {
        // In a real scenario, this implementation has to locate user in a arbitrary
        // dataStore based on information present in the SAMLCredential and
        // returns such a date in a form of application specific UserDetails object.

        String userID = credential.getNameID().getValue();

        LOG.info(userID + " is logged in");
        List<GrantedAuthority> authorities = getGrantedAuthorities(credential);

        return new SamlUserDetails(userID, authorities, credential);
    }

    private List<GrantedAuthority> getGrantedAuthorities(SAMLCredential credential) {
        String rolesClaim = credential.getAttributeAsString("http://wso2.org/claims/role");
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.isNotBlank(rolesClaim)) {
            String[] splitRolesClaim = StringUtils.split(rolesClaim, ",");
            for (String roleClaim : splitRolesClaim) {
                RoleId roleId = RoleId.valueById(roleClaim);
                if (roleId != null) {
                    authorities.add(new SimpleGrantedAuthority(roleId.getId()));
                }
            }
        }

        // fallback in case the IdP did not provide any role claims
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(RoleId.USER_ROLE_ID.getId()));
        }

        return Collections.unmodifiableList(authorities);
    }
}
