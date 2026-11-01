package com.kikop.mycontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController
@Slf4j
public class CookieController {

    // http://localhost:8080/myjsessionid/test/cookie
    // http://localhost:8080/myjsessionid/test/check
    @RequestMapping("/test/cookie")
    public String cookie(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {

        log.info("cookie...");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies).forEach((cookie) -> log.info(cookie.getName() + " : " + cookie.getValue()));
        } else {
            // 这里设置cookie后,客户端每次请求时,都会带上
            //response.addCookie(new Cookie("myname","kikop"));
        }
        return "index";
    }

    @RequestMapping("/test/check")
    public String check(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        log.info("check...");
        if (cookies != null) {
            Arrays.stream(cookies).forEach((cookie) -> log.info(cookie.getName() + " : " + cookie.getValue()));
        }
        // 当我们的create设置为true的时候，当session被new出来之后，会在当前的Resposne中自动添加JESSIONID的这个cookie。
        // ceate:取之前的session,默认true,没有则自动创建
        HttpSession session = request.getSession();
        session.invalidate();
        return "index";
    }
}
