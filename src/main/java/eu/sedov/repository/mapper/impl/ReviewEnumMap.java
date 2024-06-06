package eu.sedov.repository.mapper.impl;

import eu.sedov.repository.mapper.EntityEnumMap;

import java.util.EnumMap;
import java.util.Map;

public class ReviewEnumMap implements EntityEnumMap<ReviewEnumMap.ReviewResultSetParams> {
    public enum ReviewResultSetParams {
        ID,
        MARK,
        DESCRIPTION
    }

    @Override
    public Map<ReviewResultSetParams, String> getMap() {
        Map<ReviewResultSetParams, String> map = new EnumMap<>(ReviewResultSetParams.class);
        map.put(ReviewResultSetParams.ID, "id");
        map.put(ReviewResultSetParams.MARK, "mark");
        map.put(ReviewResultSetParams.DESCRIPTION, "description");
        return map;
    }
}
