package com.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PR110ReadFile {

    public static void main(String[] args) {
        String gestioTasquesPath = System.getProperty("user.dir") + "/data/GestioTasques.java";
        llegirIMostrarFitxer(gestioTasquesPath);

    }

    // Funció que llegeix el fitxer i mostra les línies amb numeració
    public static void llegirIMostrarFitxer(String camiFitxer) {
        try {
            
            List<String> lines = Files.readAllLines(Paths.get(camiFitxer), StandardCharsets.UTF_8);
            for (int i=0; i<lines.size(); i++) {
                String currentLine = (i+1) + ": " + lines.get(i);
                System.out.println(currentLine);
            }

        } catch (IOException e) {

            System.out.println("Error trying to read the file: " + camiFitxer);
        }
    }
}
