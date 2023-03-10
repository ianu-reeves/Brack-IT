package com.eyanu.tournamentproject.config;

import com.eyanu.tournamentproject.entity.user.User;
import com.eyanu.tournamentproject.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// old method implements AuthenticationSuccessHandler
@Component
public class CustomAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        setDefaultTargetUrl("/");
        setUseReferer(useReferer(request.getHeader("referer")));    // true if user not coming from login
        super.onAuthenticationSuccess(request, response, auth);

        //response.sendRedirect(request.getContextPath() + "/");
    }

    protected Boolean useReferer(String referer) {
        String loginURL = env.getProperty("app.loginURL");
        return loginURL != null && !referer.startsWith(loginURL);
    }
}
