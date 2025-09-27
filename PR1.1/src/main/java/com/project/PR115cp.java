package com.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PR115cp {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error: Has d'indicar dues rutes d'arxiu.");
            System.out.println("Ús: PR115cp <origen> <destinació>");
            return;
        }

        // Ruta de l'arxiu origen
        String rutaOrigen = args[0];
        // Ruta de l'arxiu destinació
        String rutaDesti = args[1];

        // Crida al mètode per copiar l'arxiu
        copiarArxiu(rutaOrigen, rutaDesti);
    }

    // Mètode per copiar un arxiu de text de l'origen al destí
    public static void copiarArxiu(String rutaOrigen, String rutaDesti) {
        try {
            Path initialFilePath = Paths.get(rutaOrigen);
            Path finalFilePath = Paths.get(rutaDesti);

            // Comprobar si el archivo destino ya existe
            boolean finalFileAlreadyExists = Files.isRegularFile(finalFilePath);
            if (finalFileAlreadyExists) {
                String fileAlreadyExistsString = "File " + rutaDesti + " already exists and will be overwritten.";
                System.out.println(fileAlreadyExistsString);

            }

            // Leer archivo origen y escribir en archivo destino
            List<String> lines = Files.readAllLines(initialFilePath, StandardCharsets.UTF_8);
            Files.write(finalFilePath, lines, StandardCharsets.UTF_8);

            String successOperationString = "File " + rutaOrigen + " correctly copied into file " + rutaDesti + ".";
            System.out.println(successOperationString);

        } catch (IOException e) {
            System.out.println("An error occurred during the operation.");

        }
    }
}
