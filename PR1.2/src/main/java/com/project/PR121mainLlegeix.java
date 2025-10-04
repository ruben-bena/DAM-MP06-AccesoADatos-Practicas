package com.project;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.objectes.PR121hashmap;

public class PR121mainLlegeix {
    private static String filePath = System.getProperty("user.dir") + "/data/PR121HashMapData.ser";

    public static void main(String[] args) {
        try {
            PR121hashmap hashMap = deserialitzarHashMap();
            hashMap.getPersones().forEach((nom, edat) -> System.out.println(nom + ": " + edat + " anys"));
        } catch (IOFitxerExcepcio e) {
            System.err.println("Error al llegir l'arxiu: " + e.getMessage());
        }
    }

    public static PR121hashmap deserialitzarHashMap() throws IOFitxerExcepcio {
        try (FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            PR121hashmap hashMap = (PR121hashmap) ois.readObject();
            return hashMap;

        } catch (Exception e) {
            throw new IOFitxerExcepcio("Error en deserialitzar l'objecte HashMap", e);
        }
    }

    // Getter
    public static String getFilePath() {
        return filePath;
    }

    // Setter
    public static void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }    
}