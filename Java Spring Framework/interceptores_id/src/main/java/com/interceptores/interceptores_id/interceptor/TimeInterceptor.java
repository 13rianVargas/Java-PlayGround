package com.interceptores.interceptores_id.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("null")
public class TimeInterceptor implements HandlerInterceptor {
    private static final String START_TIME = "start";

    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        return true;
    }

    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {
        // Calcular el tienpo trancurrido
        long startTime = (Long) request.getAttribute(START_TIME);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Tiempo de ejecución: " + endTime + " ms para URL: " + request.getRequestURI());
    }
}
