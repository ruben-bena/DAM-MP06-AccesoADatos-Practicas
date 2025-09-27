package com.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PR111Files {

    public static void main(String[] args) {

        String camiFitxer = System.getProperty("user.dir") + "/data/pr111";
        gestionarArxius(camiFitxer);
    }

    public static void gestionarArxius(String camiFitxer) {

        try {
    
            // Crea una carpeta anomenada myFiles dins del directori data/pr111, la qual es troba a la ruta del projecte. La ruta del directori serà System.getProperty("user.dir") + "/data/pr111/myFiles".
            String newDirectoryString = camiFitxer + "/myFiles";
            Path newDirectoryPath = Paths.get(newDirectoryString);
            Files.createDirectories(newDirectoryPath);

            // Dins d'aquesta carpeta, crea dos arxius: file1.txt i file2.txt.
            String newFileString = newDirectoryString + "/file1.txt";
            Path newFilePath = Paths.get(newFileString);
            Files.createFile(newFilePath);

            newFileString = newDirectoryString + "/file2.txt";
            newFilePath = Paths.get(newFileString);
            Files.createFile(newFilePath);

            // Renombra l'arxiu file2.txt a renamedFile.txt.
            Path oldFilePath = newFilePath;
            newFileString = newDirectoryString + "/renamedFile.txt";
            newFilePath = Paths.get(newFileString);
            Files.move(oldFilePath, newFilePath);

            // Mostra un llistat dels arxius dins de la carpeta myFiles amb el missatge: “Els arxius de la carpeta són:”.
            Stream<Path> filesInDirectory = Files.list(newDirectoryPath);
            System.out.println("Els arxius de la carpeta són:");
            filesInDirectory.forEach(path -> System.out.println(path.getFileName()));

            // Elimina l'arxiu file1.txt.
            newFileString = newDirectoryString + "/file1.txt";
            newFilePath = Paths.get(newFileString);
            Files.delete(newFilePath);

            // Torna a mostrar un llistat dels arxius dins de la carpeta myFiles amb el missatge: “Els arxius de la carpeta són:”.
            filesInDirectory = Files.list(newDirectoryPath);
            System.out.println("Els arxius de la carpeta són:");
            filesInDirectory.forEach(path -> System.out.println(path.getFileName()));

        } catch (IOException e) {
            e.printStackTrace();

        }

        
        
    }
}
