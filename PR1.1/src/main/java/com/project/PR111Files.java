package com.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PR111Files {

    public static void main(String[] args) {

        /*
        Des dins del programa, realitza les següents tasques:
        -Crea una carpeta anomenada myFiles dins del directori data/pr111, la qual es troba a la ruta del projecte. La ruta del directori serà System.getProperty("user.dir") + "/data/pr111/myFiles".
        */

        String camiFitxer = System.getProperty("user.dir") + "/data/pr111";
        gestionarArxius(camiFitxer);
    }

    public static void gestionarArxius(String camiFitxer) {
        // Crea una carpeta anomenada myFiles dins del directori data/pr111, la qual es troba a la ruta del projecte. La ruta del directori serà System.getProperty("user.dir") + "/data/pr111/myFiles".
        try {

            String newDirectoryString = camiFitxer + "/myFiles";
            System.out.println("newDirectoryString=" + newDirectoryString);
            Path newDirectoryPath = Paths.get(newDirectoryString);
            System.out.println("newDirectoryPath=" + newDirectoryPath);
            Files.createDirectory(newDirectoryPath);
            System.out.println("Directorio creado");

        } catch (IOException e) {

            e.printStackTrace();

        }

        // Dins d'aquesta carpeta, crea dos arxius: file1.txt i file2.txt.
        // Renombra l'arxiu file2.txt a renamedFile.txt.
        // Mostra un llistat dels arxius dins de la carpeta myFiles amb el missatge: “Els arxius de la carpeta són:”.
        // Elimina l'arxiu file1.txt.
        // Torna a mostrar un llistat dels arxius dins de la carpeta myFiles amb el missatge: “Els arxius de la carpeta són:”.
        
    }
}
