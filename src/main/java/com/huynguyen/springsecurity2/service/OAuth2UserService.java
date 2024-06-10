//package com.huynguyen.springsecurity2.service;
//
//import com.huynguyen.springsecurity2.entity.UserCredentials;
//import org.springframework.security.core.Authentication;
//
//public class OAuth2UserService {
//    public UserCredentials getUserCredentials(Authentication authentication) {
//        UserCredentials userCredentials = new UserCredentials();
//        if (authentication instanceof OAuth2AuthenticationToken) {
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//            userCredentials.setEmail(oauthToken.getPrincipal().getAttribute("email"));
//            userCredentials.setPassword(""); // Không thể trả về mật khẩu từ OAuth 2.0 provider
//        }
//        return userCredentials;
//    }
//
//}
