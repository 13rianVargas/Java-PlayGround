package com.interceptores.interceptores_id.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Saludo {

    @RequestMapping("/saludo")
    public String saludar() throws InterruptedException {
        Thread.sleep(5000);
        return "Saludos desde el controlador Saludo";
    }

}
