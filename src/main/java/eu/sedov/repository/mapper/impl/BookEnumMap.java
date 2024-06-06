package eu.sedov.repository.mapper.impl;

import eu.sedov.repository.mapper.EntityEnumMap;

import java.util.EnumMap;
import java.util.Map;

public class BookEnumMap implements EntityEnumMap<BookEnumMap.BookResultSetParams> {
    public enum BookResultSetParams {
        ID,
        NAME,
        AUTHOR
    }

    @Override
    public Map<BookResultSetParams, String> getMap() {
        Map<BookResultSetParams, String> map = new EnumMap<>(BookResultSetParams.class);
        map.put(BookResultSetParams.ID, "id");
        map.put(BookResultSetParams.NAME, "name");
        map.put(BookResultSetParams.AUTHOR, "author");
        return map;
    }



}
