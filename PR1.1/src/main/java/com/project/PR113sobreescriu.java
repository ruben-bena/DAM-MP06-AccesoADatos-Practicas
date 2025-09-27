package com.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PR113sobreescriu {

    public static void main(String[] args) {
        // Definir el camí del fitxer dins del directori "data"
        String camiFitxer = System.getProperty("user.dir") + "/data/frasesMatrix.txt";

        // Crida al mètode que escriu les frases sobreescrivint el fitxer
        escriureFrases(camiFitxer);
    }

    // Mètode que escriu les frases sobreescrivint el fitxer amb UTF-8 i línia en blanc final
    public static void escriureFrases(String camiFitxer) {
        try {
            Path filePath = Paths.get(camiFitxer);

            List<String> lines = List.of(
                "I can only show you the door",
                "You're the one that has to walk through it"
            );

            Files.write(filePath, lines, StandardCharsets.UTF_8);
            
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
