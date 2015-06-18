/*
 * This file is based on a sample provided by Vincenzo De Notaris
 * in the GitHub repository
 * <a href="https://github.com/vdenotaris/spring-boot-security-saml-sample">vdenotaris /
 * spring-boot-security-saml-sample</a>.
 *
 * <strong>Original copyright statement:</strong>
 *
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

package de.chludwig.websec.saml2sp.controller;

import de.chludwig.websec.saml2sp.security.ApplicationUser;
import de.chludwig.websec.saml2sp.stereotypes.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.saml.SAMLDiscovery;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/saml")
public class Saml2SPSSOController {

    private static final Logger LOG = LoggerFactory.getLogger(Saml2SPSSOController.class);

    public static final String SAML_LOGIN_PAGE_PATH = "/saml/login";
    public static final String SAML_GLOBAL_LOGOUT_URL_PATH = "/saml/logout";
    public static final String SAML_LOCAL_LOGOUT_URL_PATH = "/saml/logout?local=true";

    // In a "proper" project, you'd inject this view name
    private static final String NOT_IN_LOGIN_PROCESS_VIEW_NAME = "redirect:/";


    @Autowired
    private MetadataManager metadata;

    @RequestMapping(value = "/idpSelection", method = RequestMethod.GET)
    public ModelAndView idpSelection(HttpServletRequest request, @CurrentUser ApplicationUser currentUser) {
        if (!inLoginProcess(request, currentUser)) {
            return new ModelAndView(NOT_IN_LOGIN_PROCESS_VIEW_NAME);
        }

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("idps", getIDPs());
        modelMap.put("idpDiscoReturnURL", request.getAttribute(SAMLDiscovery.RETURN_URL));
        modelMap.put("idpDiscoReturnParam", request.getAttribute(SAMLDiscovery.RETURN_PARAM));
        return new ModelAndView("idpselection", modelMap);
    }

    private boolean inLoginProcess(HttpServletRequest request, ApplicationUser currentUser) {
        if (!currentUser.isAnonymous()) {
            LOG.warn("Direct accesses to '/saml/idpSelection' route are not allowed");
            return false;
        }
        else if (!isForwarded(request)) {
            LOG.warn("The current user is already logged in.");
            return false;
        }
        return true;
    }

    private Set<String> getIDPs() {
        Set<String> idps = metadata.getIDPEntityNames();
        for (String idp : idps) {
            LOG.info("Configured Identity Provider for SSO: {}", idp);
        }
        return idps;
    }

    /*
     * Checks if an HTTP request is forwarded from servlet.
     */

    private boolean isForwarded(HttpServletRequest request) {
        return request.getAttribute("javax.servlet.forward.request_uri") != null;
    }
}
