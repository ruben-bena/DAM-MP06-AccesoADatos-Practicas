package com.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PR114linies {

    public static void main(String[] args) {
        // Definir el camí del fitxer dins del directori "data"
        String camiFitxer = System.getProperty("user.dir") + "/data/numeros.txt";

        // Crida al mètode que genera i escriu els números aleatoris
        generarNumerosAleatoris(camiFitxer);
    }

    // Mètode per generar 10 números aleatoris i escriure'ls al fitxer
    public static void generarNumerosAleatoris(String camiFitxer) {
        try {
            // Generar 10 números aleatorios
            int[] randomNumbers = new int[10];
            for (int i=0; i<randomNumbers.length; i++) {
                int number = (int) (Math.random()*100);
                randomNumbers[i] = number;
                
            }

            // Guardarlos en una lista de Strings
            List<String> randomNumbersLines = new ArrayList<String>();
            for (int number : randomNumbers) {
                randomNumbersLines.add(String.valueOf(number));

            }

            // Escribir en un archivo
            Path filePath = Paths.get(camiFitxer);
            Files.write(filePath, randomNumbersLines, StandardCharsets.UTF_8);
            
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
