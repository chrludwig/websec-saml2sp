package de.chludwig.websec.saml2sp.controller;

import de.chludwig.websec.saml2sp.security.ApplicationUser;
import de.chludwig.websec.saml2sp.stereotypes.CurrentUser;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO; add documentation
 */
@Controller
public class Saml2SPErrorController implements ErrorController {

    public static final String ERROR_PAGE_PATH = "/error";

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PAGE_PATH;
    }

    @RequestMapping(ERROR_PAGE_PATH)
    public ModelAndView errorPage(HttpServletRequest request, HttpServletResponse response,
                                  @CurrentUser ApplicationUser currentUser) {

        ModelAndView modelAndView = Saml2SpController.createModelAndView("error", currentUser);
        modelAndView.addObject("forwardedFrom", request.getAttribute("javax.servlet.forward.request_uri"));
        modelAndView.addObject("statusCode", response.getStatus());
        return modelAndView;
    }
}
