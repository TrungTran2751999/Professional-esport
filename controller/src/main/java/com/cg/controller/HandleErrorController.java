//package com.cg.controller;
//
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class HandleErrorController implements ErrorController {
//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request) {
//        int httpErrorCode = getErrorCode(request);
//
//        switch (httpErrorCode) {
//            case 400: {
//                return "error/400";
//            }
//            case 401: {
//                return "error/401";
//            }
//            case 404: {
//                return "error/404";
//            }
//            case 405: {
//                return "error/405";
//            }
//            case 409: {
//                return "error/409";
//            }
//            case 500: {
//                return "error/500";
//            }
//        }
//        return "/cp/index";
//    }
//
//    private int getErrorCode(HttpServletRequest httpRequest) {
//        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
//    }
//
//    @RequestMapping("/403")
//    public String accessDenied() {
//        return "error/403";
//    }
//
//}
