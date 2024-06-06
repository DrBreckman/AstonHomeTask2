package eu.sedov.repository.mapper.impl;

import eu.sedov.model.Review;
import eu.sedov.repository.mapper.ReviewResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewResultSetMapperImpl implements ReviewResultSetMapper {
    private final ReviewEnumMap map = new ReviewEnumMap();
    @Override
    public Review map(ResultSet resultSet) throws SQLException {
        return new Review(
                resultSet.getInt(map.getMap().get(ReviewEnumMap.ReviewResultSetParams.ID)),
                resultSet.getInt(map.getMap().get(ReviewEnumMap.ReviewResultSetParams.MARK)),
                resultSet.getString(map.getMap().get(ReviewEnumMap.ReviewResultSetParams.DESCRIPTION))
        );
    }
}
