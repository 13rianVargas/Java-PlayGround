package com.interceptor.dos.control_horario.interceptors;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Calendario_Interceptor implements HandlerInterceptor {

    @Value("${config.calendar.open}")
    private int open;

    @Value("${config.calendar.close}")
    private int close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);

        if (hora >= open && hora < close) {
            StringBuilder mensaje = new StringBuilder("Bienvenido!");
            mensaje.append(" El horario de atención es de ");
            mensaje.append(open).append(":00 a ").append(close).append(":00 horas.");
            mensaje.append("Gracias por visitarnos.");
            request.setAttribute("mensaje", mensaje.toString());
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
