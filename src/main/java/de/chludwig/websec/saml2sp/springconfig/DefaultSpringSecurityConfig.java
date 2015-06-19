/*
 * Copyright 2015 Christoph Ludwig
 *
 * This file was adapted from Vincenzo De Notaris's spring-boot-security-saml-sample
 * under https://github.com/vdenotaris/spring-boot-security-saml-sample/tree/master/src.
 *
 * Original copyright notice:
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

package de.chludwig.websec.saml2sp.springconfig;

import de.chludwig.websec.saml2sp.controller.Saml2SpController;
import de.chludwig.websec.saml2sp.security.RoleId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import static de.chludwig.websec.saml2sp.controller.Saml2SPErrorController.ERROR_PAGE_PATH;
import static de.chludwig.websec.saml2sp.controller.Saml2SpController.*;
import static de.chludwig.websec.saml2sp.security.RoleId.ADMIN_ROLE_ID;
import static de.chludwig.websec.saml2sp.security.RoleId.USER_ROLE_ID;

/**
 * TODO; add documentation
 */
@Configuration
@EnableWebMvcSecurity
public class DefaultSpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()  //
                .withUser("user").password("userpw").authorities(USER_ROLE_ID.getId()).and() //
                .withUser("admin").password("adminpw").authorities(USER_ROLE_ID.getId(), ADMIN_ROLE_ID.getId());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // don't do this in production!!!
        http.anonymous().authorities(RoleId.ANONYMOUS_ROLE_ID.getId());
        configureRequestUrlAuthorization(http.authorizeRequests());
        configureLogin(http.formLogin());
        configureLogout(http.logout());
    }

    private void configureRequestUrlAuthorization(
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                    urlAuthorizationRegistry) {
        urlAuthorizationRegistry.antMatchers(PW_LOGIN_PAGE_PATH).permitAll() //
                .antMatchers(START_PAGE_PATH).permitAll() //
                .antMatchers(ERROR_PAGE_PATH).permitAll() //
                .antMatchers("/saml/**").denyAll() //
                .antMatchers(AUTHENTICATED_PAGE_PATH).authenticated() //
                .antMatchers(ANONYMOUS_PAGE_PATH).anonymous() //
                .antMatchers(USER_ROLE_PAGE_PATH).hasAuthority(RoleId.USER_ROLE_ID.getId()) //
                .antMatchers(ADMIN_ROLE_PAGE_PATH).hasAuthority(RoleId.ADMIN_ROLE_ID.getId()) //
                .anyRequest().authenticated();
    }

    private void configureLogin(FormLoginConfigurer<HttpSecurity> loginConfigurer) {
        loginConfigurer.loginPage(Saml2SpController.PW_LOGIN_PAGE_PATH)
                .failureUrl(Saml2SpController.AUTHENTICATION_FAILURE_URL).defaultSuccessUrl(START_PAGE_PATH)
                .permitAll();
    }

    private void configureLogout(LogoutConfigurer<HttpSecurity> logoutConfigurer) {
        logoutConfigurer.logoutUrl(Saml2SpController.PW_LOGOUT_PAGE_PATH).logoutSuccessUrl(START_PAGE_PATH)
                .invalidateHttpSession(true).permitAll();
    }
}
