package org.visum.api.akkahttp;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class FilesRepositoryTest {

    @Ignore
    @Test
    public void testCrudOperations() throws Exception {
        FilesRepository filesRepository = new FilesRepository();
        IndexService indexService = new IndexService(filesRepository);
        Iterable<String> results = indexService.getIntersectedResults("c#" , "finland");

        Assert.assertTrue(results.iterator().hasNext());

        results = indexService.getIntersectedResults("c#" , "canada");
        Assert.assertFalse(results.iterator().hasNext());

        results = indexService.getIntersectedResults("c#" , "fsdgsdfgsd");
        Assert.assertFalse(results.iterator().hasNext());

        results = indexService.getIntersectedResults(null);
        Assert.assertFalse(results.iterator().hasNext());
    }
}
