package com.excepciones.spring_excepciones.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excepciones.spring_excepciones.service.FileContent;

@RestController
@RequestMapping("/file")
public class FileController {
    private final FileContent contenido;

    public FileController(FileContent contenido) {
        this.contenido = contenido;
    }

    @RequestMapping("/read")
    public ResponseEntity<String> readFile(@RequestParam String filePath) {
        try {
            String elArchivo = contenido.lecturaArchivo(filePath);
            return ResponseEntity.ok(elArchivo);            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de lectura del archivo" + e);
        }
    }

}
