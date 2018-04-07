package org.visum.api.akkahttp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FilesRepository {
    private final Map<String, Index> fileIndexes = new HashMap<>();

    public FilesRepository() {
        try {
            String workDir = System.getProperty("user.dir");
            Path testIndexes = Paths.get(workDir.toString(), "src\\test\\resources\\index");
            Files.list(Paths.get(testIndexes.toString()))
                    .filter(Files::isRegularFile)
                    .forEach(file -> fileIndexes.put(file.toFile().getName(), new Index(file)));
        } catch (IOException e) {
            // todo: replace this with more meaningful stuff
            e.printStackTrace();
        }
    }

    public Index get(String keyword) {
        return fileIndexes.get(keyword);
    }

    public Index create(Index fileIndex) {
        System.out.println("file index created");
        fileIndexes.put(fileIndex.getName(), fileIndex);
        return fileIndex;
    }

    public void update(Index index) {
        fileIndexes.put(index.getName(), index);
    }

    public Collection<Index> getAll() {
        return fileIndexes.values();
    }

    public void delete(String indexName) {
        fileIndexes.remove(indexName);
    }

}