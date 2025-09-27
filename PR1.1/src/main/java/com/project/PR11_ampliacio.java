package com.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

public class PR11_ampliacio {

    public static void main(String[] args) {
        String camiFitxer = System.getProperty("user.dir") + "/data/emojis.txt";
        writeAllEmojisIntoFile(camiFitxer);

    }

    public static void writeAllEmojisIntoFile(String fileRoute) {
        try {
            // Preparar List de emojis
            Collection<Emoji> emojiCollection = EmojiManager.getAll();
            List<String> emojisList = new ArrayList<String>();
            for (Emoji emoji : emojiCollection) {
                String emojiString = emoji.getUnicode();
                emojisList.add(emojiString);
                
            }

            // Escribir los emojis en el fichero final
            Path filePath = Paths.get(fileRoute);
            Files.write(filePath, emojisList, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
