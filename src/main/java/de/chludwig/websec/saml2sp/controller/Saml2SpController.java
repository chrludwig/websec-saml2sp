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

package de.chludwig.websec.saml2sp.controller;

import de.chludwig.websec.saml2sp.security.ApplicationUser;
import de.chludwig.websec.saml2sp.security.RoleId;
import de.chludwig.websec.saml2sp.stereotypes.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * MVC controller that implements the (rudimentary) business logic for saml2sp's "regular" pages.
 */
@Controller
public class Saml2SpController {


    public static final String START_PAGE_PATH = "/";
    public static final String ANONYMOUS_PAGE_PATH = "/anonymous";
    public static final String PW_LOGIN_PAGE_PATH = "/pwlogin";
    public static final String PW_LOGOUT_PAGE_PATH = "/pwlogout";
    public static final String LOGIN_ERROR_PARAM = "error";
    public static final String AUTHENTICATION_FAILURE_URL = PW_LOGIN_PAGE_PATH + "?" + LOGIN_ERROR_PARAM;
    public static final String AUTHENTICATED_PAGE_PATH = "/authenticated";
    public static final String USER_ROLE_PAGE_PATH = "/role/user";
    public static final String ADMIN_ROLE_PAGE_PATH = "/role/admin";


    @RequestMapping(value = START_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView startPage(@CurrentUser ApplicationUser currentUser) {
        if (currentUser.isAnonymous()) {
            return createModelAndView("anonymousStartPage", currentUser);
        }
        else {
            return createModelAndView("loggedInStartPage", currentUser);
        }
    }

    @RequestMapping(value = ANONYMOUS_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView anonymousPage(@CurrentUser ApplicationUser currentUser) {
        return createModelAndView("anonymousUserPage", currentUser);
    }

    @RequestMapping(value = AUTHENTICATED_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView authenticatedPage(@CurrentUser ApplicationUser currentUser) {
        return createModelAndView("authenticatedUserPage", currentUser);
    }

    @RequestMapping(value = USER_ROLE_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView userRolePage(@CurrentUser ApplicationUser currentUser) {
        ModelAndView modelAndView = createModelAndView("roleRestrictionPage", currentUser);
        modelAndView.addObject("requiredRoleId", RoleId.USER_ROLE_ID);
        return modelAndView;
    }

    @RequestMapping(value = ADMIN_ROLE_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView adminRolePage(@CurrentUser ApplicationUser currentUser) {
        ModelAndView modelAndView = createModelAndView("roleRestrictionPage", currentUser);
        modelAndView.addObject("requiredRoleId", RoleId.ADMIN_ROLE_ID);
        return modelAndView;
    }

    @RequestMapping(value = PW_LOGIN_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = LOGIN_ERROR_PARAM, defaultValue = "false") boolean errorArg,
                                  @CurrentUser ApplicationUser currentUser) {
        if (currentUser.isAnonymous()) {
            ModelAndView modelAndView = createModelAndView("loginForm", currentUser);
            modelAndView.addObject("errorArg", errorArg);
            return modelAndView;
        }
        else {
            return createModelAndView("alreadyLoggedInPage", currentUser);
        }
    }

    public static ModelAndView createModelAndView(String viewName, ApplicationUser currentUser) {
        ModelAndView modelAndView = createModelAndView(viewName);
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

    public static ModelAndView createModelAndView(String viewName) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        addUrlModelAttributes(modelAndView);
        modelAndView.addObject("time", new Date());
        return modelAndView;
    }

    public static void addUrlModelAttributes(ModelAndView modelAndView) {
        modelAndView.addObject("startPageUrl", START_PAGE_PATH);
        modelAndView.addObject("loginUrl", PW_LOGIN_PAGE_PATH);
        modelAndView.addObject("logoutUrl", PW_LOGOUT_PAGE_PATH);
        modelAndView.addObject("anonymousPageUrl", ANONYMOUS_PAGE_PATH);
        modelAndView.addObject("authenticatedPageUrl", AUTHENTICATED_PAGE_PATH);
        modelAndView.addObject("userRolePageUrl", USER_ROLE_PAGE_PATH);
        modelAndView.addObject("adminRolePageUrl", ADMIN_ROLE_PAGE_PATH);
    }
}
