package eu.sedov.repository.mapper;

import eu.sedov.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserResultSetMapper {
    User map(ResultSet resultSet) throws SQLException;
}
