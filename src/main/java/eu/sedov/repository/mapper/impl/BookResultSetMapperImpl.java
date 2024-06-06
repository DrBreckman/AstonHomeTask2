package eu.sedov.repository.mapper.impl;

import eu.sedov.model.Book;
import eu.sedov.repository.mapper.BookResultSetMapper;
import org.slf4j.Marker;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookResultSetMapperImpl implements BookResultSetMapper {
    private final BookEnumMap map = new BookEnumMap();
    @Override
    public Book map(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getInt(map.getMap().get(BookEnumMap.BookResultSetParams.ID)),
                resultSet.getString(map.getMap().get(BookEnumMap.BookResultSetParams.NAME)),
                resultSet.getString(map.getMap().get(BookEnumMap.BookResultSetParams.AUTHOR))
        );
    }
}
