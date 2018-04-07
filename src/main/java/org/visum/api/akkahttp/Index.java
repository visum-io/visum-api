package org.visum.api.akkahttp;

import java.nio.file.Path;

public class Index {
    private Path fullPath;

    public Index() {
    }

    public Index(final Path fullPath) {
        this.fullPath = fullPath;
    }

    public Path getFullPath() {
        return fullPath;
    }

    public void setFullPath(Path fullPath) {
        this.fullPath = fullPath;
    }

    public String getName() {
        return fullPath.toFile().getName();
    }

    @Override
    public String toString() {
        return "FileIndex{" +
                "fullPath=" + fullPath +
                ", name='" + getName() + '\'' +
                '}';
    }
}