package org.visum.api.akkahttp;

import java.util.UUID;

public class FileIndex {
    private UUID uuid;
    private String name;

    public FileIndex() {
    }

    public FileIndex(final UUID uuid, final String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileIndex{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}
