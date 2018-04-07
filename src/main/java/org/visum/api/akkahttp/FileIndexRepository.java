package org.visum.api.akkahttp;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FileIndexRepository {
    // todo: replace this with S3Implementation
    private final ConcurrentHashMap<UUID, FileIndex> fileIndexes = new ConcurrentHashMap<>();

    public static final String DUMMY_UUID = "d0926864-e5e7-4bca-8067-d05eb7c725e9";

    {
        fileIndexes.put(UUID.fromString(DUMMY_UUID),
                new FileIndex(UUID.fromString(DUMMY_UUID), "visum"));
    }

    public FileIndex get(UUID uuid) {
        return fileIndexes.get(uuid);
    }

    public FileIndex create(FileIndex fileIndex) {
        System.out.println("file index created");
        UUID uuid = UUID.randomUUID();
        FileIndex groupWithId = new FileIndex(uuid, fileIndex.getName());
        fileIndexes.put(uuid, groupWithId);
        return groupWithId;
    }

    public void update(FileIndex group) {
        fileIndexes.put(group.getUuid(), group);
    }

    public Collection<FileIndex> getAll() {
        return fileIndexes.values();
    }

    public void delete(UUID uuid) {
        fileIndexes.remove(uuid);
    }
}