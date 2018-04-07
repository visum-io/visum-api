package org.visum.api.akkahttp;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class IndexService {
    private final FilesRepository filesRepository;

    @Inject
    public IndexService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public Iterable<String> getIntersectedResults(String... keywords) {
        if (keywords == null || Arrays.stream(keywords).anyMatch(keyword -> keyword == null)) {
            return new ArrayList<>();
        }

        List<Index> indexes = new ArrayList<>();
        Arrays.stream(keywords).forEach(keyword -> indexes.add(filesRepository.get(keyword)));

        return getIntersection(indexes);
    }

    private Iterable<String> getIntersection(List<Index> indexes) {
        List<String> lastList = null;

        for (Index index : indexes) {
            List<String> users;
            if (index == null) {
                return new ArrayList<>();
            } else {
                users = FileHelper.getLines(index.getFullPath());
            }

            if (lastList == null) {
                lastList = users;
            } else {
                lastList = users.stream()
                        .filter(lastList::contains)
                        .collect(Collectors.toList());
            }
        }

        return lastList;
    }
}
