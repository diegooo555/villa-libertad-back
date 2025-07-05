package com.example.demo.handlers;

import com.example.demo.entities.Role;
import com.example.demo.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtil;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {


    private final UserService userService;
    private final RoleService roleService;

    public CustomOAuth2SuccessHandler(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = oauthToken.getPrincipal();
        String givenName = user.getAttribute("given_name");
        String picture = user.getAttribute("picture");
        String email = user.getAttribute("email");

        User userObj = userService.findUserByEmail(email);

        Set<Role> roles = (userObj != null) ? userObj.getRoles()
                : Set.of(roleService.findRoleByName("ROLE_VISITOR"));


        String accessToken = JwtUtil.generateAccessToken(givenName, picture, email, roles);
        String refreshToken = JwtUtil.generateRefreshToken(email, givenName, picture);

        ResponseCookie cookie = ResponseCookie.from("smartorlsasvcderyocxakfgh", refreshToken)
                .httpOnly(true)
                .secure(true) // true solo en producción con HTTPS
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("None") // Esto es importante si usas distintos orígenes
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        String redirectUrl = frontendUrl + "/oauth2-redirect?token=" + accessToken + "&isNewUser=" + (userObj == null);
        response.sendRedirect(redirectUrl);
    }
}