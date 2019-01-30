package com.intro;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Main {

    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        HashMap<Character, Integer> countMap = new HashMap<>();
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_NAME), charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (Character single : line.toCharArray()) {
                    // This implementation doesn't eliminate empty characters because they're still characters (and they still have rights :)).
                    if (countMap.containsKey(single)) {
                        countMap.replace(single, countMap.get(single), countMap.get(single) + 1);
                        continue;
                    }
                    countMap.put(single, 1);
                }
            }
            Object[] keySet = countMap.keySet().toArray();
            String print = null;
            for (int i = 0; i < keySet.length; i++) {
                if (keySet[i].equals(' ')) {
                    print = "' '";
                } else {
                    print = keySet[i].toString();
                }
                System.out.println(print + ": " + countMap.get(keySet[i]));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
