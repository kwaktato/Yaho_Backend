//package com.example.yaho.utils;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Optional;
//
//public class SecurityUtil {
//
//    // 현재 사용중인 유저의 nickname 가져오기
//    public static String getCurrentUserName() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.getName() == null) {
//            return null;
//        }
//        System.out.println("Authentication: " + authentication.getName());
//        return authentication.getName();
//    }
//
//    public static Optional<Object> getCurrentUserLogin() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.getName() == null) {
//            return Optional.empty();
//        }
//        System.out.println("Authentication: " + authentication.getName());
//        return Optional.ofNullable(authentication.getPrincipal());
//    }
//}