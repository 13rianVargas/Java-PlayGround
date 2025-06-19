package com.interceptor.dos.control_horario.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/indice")
    public ResponseEntity<?> indice() {// el ? indica que el método puede devolver cualquier tipo de objeto
        Map<String, Object> datos = new HashMap<>();
        datos.put("titulo", "Sistema de Control de Acceso");
        datos.put("hora", new Date());
        return ResponseEntity.ok(datos);
    }
}
