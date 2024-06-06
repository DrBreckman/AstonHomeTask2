package eu.sedov.repository.mapper.impl;

import eu.sedov.model.User;
import eu.sedov.repository.mapper.UserResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapperImpl implements UserResultSetMapper {
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("address"));
    }
}
