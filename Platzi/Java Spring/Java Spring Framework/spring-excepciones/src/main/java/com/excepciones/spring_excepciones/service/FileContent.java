package com.excepciones.spring_excepciones.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class FileContent {
    public String lecturaArchivo(String filePath) {
        try {
            Path ruta = Paths.get(filePath);
            return Files.readString(ruta);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }
    }
}
