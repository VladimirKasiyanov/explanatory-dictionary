package com.kasiyanov.dao;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DictionaryDaoTest {

    @Test
    public void checkGetAllByJavaMerge() {
        Map<String, Integer> actualMap = DictionaryDao.getINSTANCE().getAllByJavaMerge();
        Map<String, Integer> expectedMap = createExpectedMap();

        assertNotNull(actualMap);
        assertEquals(actualMap, expectedMap);
    }

    @Test
    public void checkGetAllBySqlMerge() {
        Map<String, Integer> actualMap = DictionaryDao.getINSTANCE().getAllBySqlMerge();
        Map<String, Integer> expectedMap = createExpectedMap();

        assertNotNull(actualMap);
        assertEquals(actualMap, expectedMap);
    }

    private Map<String, Integer> createExpectedMap() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("человек", 4);
        expectedMap.put("самолет", 3);
        expectedMap.put("абажур", 1);
        expectedMap.put("кинотеатр", 2);
        expectedMap.put("музыка", 16);
        return expectedMap;
    }
}