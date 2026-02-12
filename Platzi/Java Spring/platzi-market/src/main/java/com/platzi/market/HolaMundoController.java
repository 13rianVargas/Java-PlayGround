package com.platzi.market;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoController {

    @RequestMapping("/saludos")
    public String saludos(){
        return "No pares de aprender Bri";
    }

}
