package org.visum.api.akkahttp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileHelper {

    public static List<String> getLines(Path fileName) {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName.toString()))) {
            stream.forEach(line -> lines.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}